package Uno.Network.Server.Game;

import Uno.Engine.Game;
import Uno.Network.Server.Server;

import java.io.IOException;

public class GameServer {
    private GameState gameState = GameState.LOBBY;

    private Game game;

    public GameServer(int port) throws IOException {
        Server server = new Server(42069);

    }




}
