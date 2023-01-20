package Uno.Network.Client;

import Uno.Network.Server.ClientRequest.ClientRequest;
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
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client extends Thread {
    private final Selector selector;
    private final SocketChannel socketChannel;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(128*1024);
    private ArrayList<ClientEvent> eventListeners = new ArrayList<>();


    public Client(String host, int port) throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        socketChannel.configureBlocking(false);
        System.out.println("connecting to " + socketChannel.getRemoteAddress());

        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);

    }

    private void handleDisconnect()  {
        try {
            socketChannel.close();
        } catch (IOException e) {
            System.out.println("could not close client socket - " + e.getMessage());
        }
        notifyDisconnect();
    }
    public void addEventListener(ClientEvent listener) {
        eventListeners.add(listener);
    }

    public boolean removeEventListener(ClientEvent listener) {
        return eventListeners.remove(listener);
    }

    @Override
    public void run() {
        while(socketChannel.isOpen()) {
            try {
                selector.select();
            }
            catch (IOException ex) {
                System.out.println("could not select keys " + ex.getMessage());
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isReadable()) {
                    handleRead(key);
                }
            }
        }
    }

    public void handleRead(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        byteBuffer.clear();
        PromiscousByteArrayOutputStream byteArrayOutputStream = new PromiscousByteArrayOutputStream();

        try {
            int read;
            while((read = client.read(byteBuffer)) > 0) {
                byteArrayOutputStream.write(byteBuffer.array(), 0, read);

            }
            if(read < 0) {
                this.handleDisconnect();
                return;
            }
        }
        catch (IOException ex) {
            this.handleDisconnect();
            return;
        }

        try (
                ByteArrayInputStream bis = new ByteArrayInputStream(byteArrayOutputStream.getBuf(), 0, byteArrayOutputStream.getCount());
                ObjectInputStream ois = new ObjectInputStream(bis)
        ) {
            Message message = (Message)ois.readObject();
            notifyMessage(message);


        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("failed parsing received data, " + ex.getMessage());
            notifyReadError(ex.getMessage());

        }

    }


    public void writeRequest(ClientRequest request) throws IOException {
        PromiscousByteArrayOutputStream pbos = new PromiscousByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(pbos);
        oos.writeObject(request);
        socketChannel.write(ByteBuffer.wrap(pbos.getBuf()));
    }

    private void notifyDisconnect() {
        eventListeners.forEach(ClientEvent::onDisconnect);
    }
    private void notifyMessage(Message message) {
        eventListeners.forEach(clientEvent -> clientEvent.onMessage(message));
    }
    private void notifyReadError(String message) {
        eventListeners.forEach(clientEvent -> clientEvent.onReadError(message));
    }
}
