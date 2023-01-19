package Uno.Network.Server.Game;

import Uno.Engine.Game;
import Uno.Network.Server.Chat.ChatHistory;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.ClientRequest.RequestHandlers.Sync;
import Uno.Network.Server.ClientRequest.RequestType;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.Server;
import Uno.Network.Server.ServerClient;
import Uno.Network.Server.ServerEvent;

import java.io.IOException;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {
    public static final int SECONDS_TO_TIMEOUT = 20;
    public static final int SYNC_PERIOD = 10;
    private GameState gameState = GameState.LOBBY;
    private final Server server;
    private Game game = null;
    private final ChatHistory chatHistory = new ChatHistory();
    private final ConcurrentHashMap<ServerClient, GameClient> clients = new ConcurrentHashMap<>();

    public GameServer(int port) throws IOException {
        server = new Server(port);
        server.addEventListener(new ServerEventHandler());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                playerTimeoutHandler();
            }
        }, 0, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                syncClients();
            }
        }, 0, SYNC_PERIOD * 1000);

    }

    public void start() {
        this.server.start();
    }


    public GameState getGameState() {
        return gameState;
    }

    public Server getServer() {
        return server;
    }

    public Game getGame() {
        return game;
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public ConcurrentHashMap<ServerClient, GameClient> getClients() {
        return clients;
    }

    private class ServerEventHandler implements ServerEvent {

        @Override
        public void onClientConnected(ServerClient serverClient) {

        }

        @Override
        public void onClientDisconnected(ServerClient serverClient) {
            System.out.println(clients.size());

            if(!clients.containsKey(serverClient)) {
                // dont care
                return;
            }
            // delete joined players only if it's still a lobby
            if(gameState == GameState.IN_PROGRESS) {
                clients.get(serverClient).setConnected(false);
            }
            else {
                clients.remove(serverClient);

            }
            server.broadcast(new Message(MessageType.CLIENT_LEFT, clients.get(serverClient)));

        }

        @Override
        public void onClientRead(ServerClient serverClient, ClientRequest request) {
            try {
                if(request.getRequestType() != RequestType.JOIN && !clients.containsKey(serverClient)) {
                    serverClient.sendEmptyErrorResponse(request, "you must join the game first");
                }
                if(!request.isAttachmentValid()) {
                    serverClient.sendEmptyErrorResponse(request, "invalid attachment");
                    return;
                }
                GameState targetGameState = request.getRequestType().getTargetGameState();
                if(targetGameState != null && targetGameState != gameState) {
                    return;
                }
                request.getRequestType().executeHandler(GameServer.this, serverClient, request);

            }
            catch(IOException ex) {
                System.out.println("exception in client read method");
            }
        }

        @Override
        public void onClientReadError(ServerClient serverClient, String errorMessage) {
            System.out.println("CLIENT READ ERROR " + errorMessage);
        }
    }

    private void playerTimeoutHandler() {
        Instant timeoutAfter = Instant.now().minusSeconds(SECONDS_TO_TIMEOUT);
        clients.forEach((key, value) -> {
            System.out.println(value.getName() + " " + value.getLastHeartbeatTime());
            if (timeoutAfter.isAfter(value.getLastHeartbeatTime())) {
                System.out.println("client " + value.getName() + " disconnected due to timeout");
                disconnectClient(key);
            }
        });

    }

    private void disconnectClient(ServerClient serverClient) {
        server.handleDisconnect(serverClient.getSelectionKey());
    }

    public void setGameState(GameState state) {
        this.gameState = state;
        if(state == GameState.IN_PROGRESS) {
            String[] clientNames = clients.values().stream().map(GameClient::getName).toArray(String[]::new);
            this.game = new Game(clientNames);

            this.game.getRound().getNotifier().addRoundEventListener(new GameEventListener(this));

            for(GameClient client : this.clients.values()) {
                var players = this.game.getRound().getPlayers().getPlayers();
                client.setPlayer(players.stream().filter(player -> player.getName().equals(client.getName())).findFirst().get());
            }
        }
        else {
            this.game = null;
        }
        syncClients();
    }

    private void syncClients() {
        Sync.syncPlayers(this, clients.values().stream().map(GameClient::getServerClient).toArray(ServerClient[]::new));
    }


}
