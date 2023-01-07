package Uno.Network.Server;

import Uno.Engine.Player.Player;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class Server extends Thread {
    private static final int MAX_CLIENTS = 10;
    private final int serverPort;
    private final ServerSocketChannel serverSocket;
    private final Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(128 * 1024);


    private Set<SelectionKey> currentlyConnectedClients() {
        return selector.keys().stream().filter(k -> k.isValid() && k.channel() instanceof SocketChannel).collect(Collectors.toSet());
    }
    public long currentlyConnectedClientsCount() {
        return currentlyConnectedClients().size();
    }

    public void handleDisconnect(SelectionKey key) throws IOException {
        System.out.println("client " + key.attachment() + " disconnected");

        key.channel().close();

//        selector.
    }

    public Server(int serverPort) throws IOException {
        this.serverPort = serverPort;

        this.selector = Selector.open();
        this.serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(serverPort));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);


    }


    @Override
    public void run() {
        while(serverSocket.isOpen()) {

            try {
                selector.select();
            } catch (IOException e) {
                System.out.println("could not select keys " + e.getMessage());
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();

            while(iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                if(key.isAcceptable()) {
                    handleAccept(key);
                }
                if(key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void read(SelectionKey key) {

        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();
        PromiscousByteArrayOutputStream byteArrayOutputStream = new PromiscousByteArrayOutputStream();

        try {
            int read;
            while((read = client.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer.array(), 0, read);
                System.out.println(read);
            }
            if(read < 0) {
                this.handleDisconnect(key);
                return;
            }

        } catch (IOException e) {
            System.out.println("error reading from client " + e.getMessage());
            return;
        }

        try(
                ByteArrayInputStream bis = new ByteArrayInputStream(byteArrayOutputStream.getBuf(), 0, byteArrayOutputStream.getCount());
                ObjectInputStream ois = new ObjectInputStream(bis);

        ) {
            Player player = (Player)ois.readObject();
            System.out.println(Arrays.toString(player.getHand().toArray()));


        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("failed parsing received data, " + ex.getMessage());
        }

    }

    private void handleAccept(SelectionKey key)  {
        String address;
        try
        {

            SocketChannel client = ((ServerSocketChannel)key.channel()).accept();
            address = client.getRemoteAddress().toString();

            System.out.println("client connecting from "  + address);

            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ, address);
        } catch (IOException e) {
            System.out.println("client failed to connect");
            return;
        }
        System.out.println("accepted connection from " + address);
        System.out.println("current clients count: " + currentlyConnectedClientsCount());

    }

    public void broadcast(byte[] message) {
        ByteBuffer buf = ByteBuffer.wrap(message);
        var clients = this.currentlyConnectedClients();
        System.out.println("broadcasting message to " + clients.size() + " clients");
        for(SelectionKey key: clients) {
            try {
                ((SocketChannel)key.channel()).write(buf);
            } catch (IOException e) {
                System.out.println("failed to send data to " + key.attachment());
            }
            buf.rewind();
        }
    }



}



