package Uno.Network.Client;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Message.Message;
import Uno.Network.Utilities.PosByteArrayInputStream;
import Uno.Network.Utilities.PromiscousByteArrayOutputStream;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
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
        socketChannel.socket().setReceiveBufferSize(1024*1024);

    }

    public void disconnect() throws IOException {
        handleDisconnect();
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
                if(key.isConnectable()) {
                    handleConnect(key);
                }
            }
        }
    }
    public void handleConnect(SelectionKey key) {
        System.out.println("handleConnect");
        SocketChannel client = (SocketChannel) key.channel();
        try {
            client.socket().setReceiveBufferSize(1024*1024);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleRead(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        PromiscousByteArrayOutputStream byteArrayOutputStream = new PromiscousByteArrayOutputStream();

        try {
            int read;
            while((read = client.read(byteBuffer)) > 0) {
                byteBuffer.clear();
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
        int offset = 0;
        while(offset < byteArrayOutputStream.getCount()) {

            try(
                    PosByteArrayInputStream bis = new PosByteArrayInputStream(byteArrayOutputStream.getBuf(), offset, byteArrayOutputStream.getCount() - offset);
            ) {
                if(!bis.seekHeader()) {
                    break;
                }
                ObjectInputStream ois = new ObjectInputStream(bis);

                Message message = (Message)ois.readObject();
                offset = bis.getPos();

//                System.out.println("Received message, type: " +message.getMessageType());
                notifyMessage(message);

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("offset: "+ offset + " length: " + (byteArrayOutputStream.getCount() - offset));
                System.out.println(ex.getClass().getSimpleName() + " failed parsing received data, " + ex.getMessage());
                notifyReadError(ex.getMessage());
                break;

            }

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
