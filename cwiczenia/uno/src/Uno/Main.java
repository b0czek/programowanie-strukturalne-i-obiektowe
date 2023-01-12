package Uno;

import Uno.Engine.Game;
import Uno.Network.Server.Message.Command;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        Game game = new Game(new String[]{"natalia1", "natalia2", "darek"});
        try {
            Server server = new Server(42069);
            server.start();
            new Thread(() ->  {
                try {
                    while(true) {

                        Message message = new Message(Command.CHAT, "dupa");
                        server.broadcast(message);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();


        }
        catch (IOException ex) {
            System.out.println("could not start game server: " + ex.getMessage());
        }
    }
}