package Uno.Network.Client;

import Uno.Engine.Deck.Deck;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.PlayerController;
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
import java.util.Set;

public class Client {
    private static Selector selector;

    private static ByteBuffer buffer = ByteBuffer.allocate(64 * 1024);

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, InvocationTargetException {
        SocketChannel s = SocketChannel.open(new InetSocketAddress("192.168.1.48", 42069));
        s.configureBlocking(false);
        System.out.println("connecting to " + s.getRemoteAddress());

        selector = Selector.open();
        s.register(selector, SelectionKey.OP_READ);


        PromiscousByteArrayOutputStream pbos = new PromiscousByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(pbos);

        oos.writeObject(new Player(0, new PlayerController(null), "chuj", new Deck()));
        System.out.println(pbos.getCount());

        s.write(ByteBuffer.wrap(pbos.getBuf()));

        while (s.isOpen()) {

            try {
                System.out.println("selecting");
                selector.select();
            } catch (IOException e) {
                System.out.println("could not select keys " + e.getMessage());
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isConnectable()) {
                    System.out.println("connectable");
                    s.finishConnect();
                }
                if (key.isReadable()) {
                    System.out.println("readable");

                    read(key);
                }
            }
        }
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
            System.out.println("error reading from client " + e.getMessage());
            return;
        }

        try(
                ByteArrayInputStream bis = new ByteArrayInputStream(byteArrayOutputStream.getBuf(), 0, byteArrayOutputStream.getCount());
                ObjectInputStream ois = new ObjectInputStream(bis);

        ) {
            Object obj = ois.readObject();
            System.out.println(obj.toString());
//            System.out.println(Arrays.toString(player.getHand().toArray()));


        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("failed parsing received data, " + ex.getMessage());
        }

    }

        //        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
//        System.out.println("opened object output stream");
//
//        while(true) {
//                oos.writeObject();
//
////            if(ois.available() >0) {
////                System.out.println(ois.readObject());
////            }
//
//        }
}


