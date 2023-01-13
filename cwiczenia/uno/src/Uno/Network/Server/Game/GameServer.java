package Uno.Network.Server.Game;

import Uno.Engine.Game;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Server;
import Uno.Network.Server.ServerEvent;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class GameServer {
    private GameState gameState = GameState.LOBBY;
    private Server server;
    private Game game;

    public GameServer(int port) throws IOException {
        server = new Server(port);
        server.addEventListener(new ServerEventHandler());
    }

    public void start() {
        this.server.start();
    }

    private class ServerEventHandler implements ServerEvent {

        @Override
        public void onClientConnected(SelectionKey key) {
            if(gameState == GameState.LOBBY) {

            }
        }

        @Override
        public void onClientDisconnected(SelectionKey key) {

        }

        @Override
        public void onClientRead(SelectionKey key, Message message) {

        }
    }
}
