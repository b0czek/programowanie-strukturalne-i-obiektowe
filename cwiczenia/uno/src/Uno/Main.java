package Uno;

import Uno.Network.Server.Game.GameServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        Game game = new Game(new String[]{"natalia1", "natalia2", "darek"});
        try {
            GameServer server = new GameServer(42069);
            server.start();
        }
        catch (IOException ex) {
            System.out.println("could not start game server: " + ex.getMessage());
        }
    }
}