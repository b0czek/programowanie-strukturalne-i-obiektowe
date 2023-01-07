package Uno;

import Uno.Engine.Game;
import Uno.Network.Server.Message.Command;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Server;
import Uno.Network.Utilities.PromiscousByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) {
//        Game game = new Game(new String[]{"f", "s", "t"});
        try {
            Server server = new Server(42069);
            server.start();
            new Thread(() ->  {
                try {
                    while(true) {
                        Message message = new Message(Command.CHAT, "jebac disa kurwe zwisa");
                        PromiscousByteArrayOutputStream pbos = new PromiscousByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(pbos);
                        oos.writeObject(message);
                        server.broadcast(pbos.getBuf());
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();


        }
        catch (IOException ex) {
            System.out.println("could not start game server: " + ex.getMessage());
        }
    }
}