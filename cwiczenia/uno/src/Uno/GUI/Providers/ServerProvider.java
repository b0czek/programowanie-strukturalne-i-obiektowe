package Uno.GUI.Providers;

import Uno.Network.Server.Game.GameServer;

import java.io.IOException;

public class ServerProvider {
    private static GameServer gameServer = null;

    public static boolean isRunning() {
        return gameServer != null;
    }
    public static void init(Runnable startListener) throws IOException {
        if(isRunning()) {
            close();
        }
        gameServer = new GameServer(42069, startListener);
    }
    public static void close() throws IOException {
        if(isRunning()) {
            gameServer.stop();
            gameServer = null;
        }
    }


}
