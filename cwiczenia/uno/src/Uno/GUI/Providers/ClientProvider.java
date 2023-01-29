package Uno.GUI.Providers;

import Uno.Network.Client.GameClient;
import Uno.Network.Server.Game.GameServer;

import java.io.IOException;

public class ClientProvider {
    private static GameClient gameClient = null;

    public static boolean isRunning() {
        return gameClient != null;
    }
    public static void init(String username, String host, int port) throws IOException {
        if(isRunning()) {
            close();
        }
        gameClient = new GameClient(username, host, port);
    }
    public static void close() throws IOException {
        if(isRunning()) {
            gameClient.disconnect();
            gameClient = null;
            if(ServerProvider.isRunning()) {
                ServerProvider.close();
            }
        }
    }

    public static GameClient getGameClient() {
        return gameClient;
    }
}
