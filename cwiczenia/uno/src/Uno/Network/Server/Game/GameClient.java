package Uno.Network.Server.Game;

import Uno.Engine.Player.Player;
import Uno.Network.Server.ServerClient;

import java.io.Serializable;
import java.time.Instant;

public class GameClient implements Serializable {
    public static final long serialVersionUID = 2L;

    private final String name;
    private transient ServerClient serverClient;
    private transient Player player;
    private boolean isConnected = true;

    private boolean isReady = false;
    private Instant lastHeartbeatTime = Instant.now();

    public GameClient(String name, ServerClient serverClient) {
        this.name = name;
        this.serverClient = serverClient;
    }

    public ServerClient getServerClient() {
        return serverClient;
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

    public Instant getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }

    public void updateLastHeartbeatTime() {
        this.lastHeartbeatTime = Instant.now();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
