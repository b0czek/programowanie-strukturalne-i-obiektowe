package Uno;

import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        Game game = new Game(new String[]{"natalia1", "natalia2", "darek"});
        try {
            GameServer server = new GameServer(42069);
            server.start();
            new Thread(() ->  {
                try {
                    while(true) {

//                        Message message = new Message(MessageType.CHAT, "dupa");
//                        server.broadcast(message);
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