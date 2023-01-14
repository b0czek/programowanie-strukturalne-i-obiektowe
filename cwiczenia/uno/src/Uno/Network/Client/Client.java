package Uno.Network.Client;

import Uno.Engine.Deck.Deck;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.PlayerController;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.ClientRequest.RequestType;
import Uno.Network.Server.Message.Response;
import Uno.Network.Utilities.PromiscousByteArrayOutputStream;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private static Selector selector;
    private static SocketChannel s;
    private static ByteBuffer buffer = ByteBuffer.allocate(64 * 1024);

    public static void main(String[] args) throws IOException {
        s = SocketChannel.open(new InetSocketAddress("localhost", 42069));
        s.configureBlocking(false);
        System.out.println("connecting to " + s.getRemoteAddress());

        selector = Selector.open();
        s.register(selector, SelectionKey.OP_READ);



        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            String name =scanner.nextLine();
            try {
                write(new ClientRequest(RequestType.JOIN, name));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                write(new ClientRequest(RequestType.CHAT_HISTORY, null));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            while(s.isOpen()) {
                String message = scanner.nextLine();
                try {
                    write(new ClientRequest(RequestType.CHAT_MESSAGE, message));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }).start();

        while (s.isOpen()) {
            System.out.println("selecting");
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
                if (key.isReadable()) {
                    System.out.println("readable");

                    read(key);
                }
            }
        }
    }

    private static void write(ClientRequest request) throws IOException {
        PromiscousByteArrayOutputStream pbos = new PromiscousByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(pbos);
        oos.writeObject(request);
        s.write(ByteBuffer.wrap(pbos.getBuf()));
    }

    private static void read(SelectionKey key) {

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
                client.close();
                return;
            }

        } catch (IOException e) {
            System.out.println("error reading from server " + e.getMessage());
            return;
        }

        try(
                ByteArrayInputStream bis = new ByteArrayInputStream(byteArrayOutputStream.getBuf(), 0, byteArrayOutputStream.getCount());
                ObjectInputStream ois = new ObjectInputStream(bis);
        ) {
            Object obj = ois.readObject();

            System.out.println(obj.toString());


        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("failed parsing received data, " + ex.getMessage());
        }

    }
}


