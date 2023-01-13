package Uno.Network.Server;

import Uno.Network.Server.Message.Message;
import Uno.Network.Utilities.PromiscousByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class Server extends Thread {
    private final int serverPort;
    private final ServerSocketChannel serverSocket;
    private final Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(128 * 1024);

    private ArrayList<ServerEvent> eventListeners = new ArrayList<>();

    private Set<SelectionKey> currentlyConnectedClients() {
        return selector.keys().stream().filter(k -> k.isValid() && k.channel() instanceof SocketChannel).collect(Collectors.toSet());
    }

    public long currentlyConnectedClientsCount() {
        return currentlyConnectedClients().size();
    }

    public void handleDisconnect(SelectionKey key) throws IOException {
        System.out.println("client " + key.attachment() + " disconnected");

        key.channel().close();
        this.notifyClientDisconnected(key);

    }

    public Server(int serverPort) throws IOException {
        this.serverPort = serverPort;

        this.selector = Selector.open();
        this.serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(serverPort));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);


    }

    public void close() throws IOException {
        for (var selector : selector.keys()) {
            selector.channel().close();
        }
        serverSocket.close();
        selector.close();
    }

    public void addEventListener(ServerEvent listener) {
        eventListeners.add(listener);
    }

    public boolean removeEventListener(ServerEvent listener) {
        return eventListeners.remove(listener);
    }


    @Override
    public void run() {
        while (serverSocket.isOpen()) {

            try {
                selector.select();
            } catch (IOException e) {
                System.out.println("could not select keys " + e.getMessage());
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                if (key.isAcceptable()) {
                    handleAccept(key);
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
            }
        }
    }

    private void handleRead(SelectionKey key) {

        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();
        PromiscousByteArrayOutputStream byteArrayOutputStream = new PromiscousByteArrayOutputStream();

        try {
            int read;
            while ((read = client.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer.array(), 0, read);
                System.out.println(read);
            }
            if (read < 0) {
                this.handleDisconnect(key);
                return;
            }

        } catch (IOException e) {
            System.out.println("error reading from client " + e.getMessage());
            return;
        }

        try (
                ByteArrayInputStream bis = new ByteArrayInputStream(byteArrayOutputStream.getBuf(), 0, byteArrayOutputStream.getCount());
                ObjectInputStream ois = new ObjectInputStream(bis);

        ) {
            Message message = (Message) ois.readObject();
            this.notifyClientRead(key, message);


        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("failed parsing received data, " + ex.getMessage());
        }

    }

    private void handleAccept(SelectionKey key) {
        String address;
        try {
            SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
            address = client.getRemoteAddress().toString();

            System.out.println("client connecting from " + address);

            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ, address);
        } catch (IOException e) {
            System.out.println("client failed to connect");
            return;
        }
        this.notifyClientConnected(key);
        System.out.println("accepted connection from " + address);
        System.out.println("current clients count: " + currentlyConnectedClientsCount());

    }


    private ByteBuffer wrapMessage(Message message) {
        try {
            PromiscousByteArrayOutputStream pbos = new PromiscousByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(pbos);
            oos.writeObject(message);
            return ByteBuffer.wrap(pbos.getBuf());
        } catch (IOException ex) {
            // should never happen
            throw new IllegalStateException(ex);
        }

    }

    public void broadcast(Message message) {
        ByteBuffer buf = wrapMessage(message);
        var clients = this.currentlyConnectedClients();

        if (clients.size() != 0) {
            System.out.println("broadcasting message to " + clients.size() + " clients");
        }

        for (SelectionKey key : clients) {
            try {
                ((SocketChannel) key.channel()).write(buf);
            } catch (IOException e) {
                System.out.println("failed to send data to " + key.attachment());
            }
            buf.rewind();
        }
    }


    public void sendMessage(Message message, SelectionKey client) throws IOException {
        ((SocketChannel) client.channel()).write(wrapMessage(message));
    }

    private void notifyClientConnected(SelectionKey key) {
        eventListeners.forEach(listener -> listener.onClientConnected(key));
    }

    private void notifyClientDisconnected(SelectionKey key) {
        eventListeners.forEach(listener -> listener.onClientDisconnected(key));
    }

    private void notifyClientRead(SelectionKey key, Message message) {
        eventListeners.forEach(listener -> listener.onClientRead(key, message));
    }

}



