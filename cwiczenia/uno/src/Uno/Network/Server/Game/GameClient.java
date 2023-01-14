package Uno.Network.Server.Game;

import Uno.Network.Server.ServerClient;

import java.io.Serializable;

public class GameClient implements Serializable {
    public static final long serialVersionUID = 1L;

    private String name;
    private transient ServerClient serverClient;
    private boolean isConnected = true;

    private boolean isReady = false;

    public GameClient(String name, ServerClient serverClient) {
        this.name = name;
        this.serverClient = serverClient;
    }

    public void setServerClient(ServerClient serverClient) {
        this.serverClient = serverClient;
    }

    public String getName() {
        return name;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
