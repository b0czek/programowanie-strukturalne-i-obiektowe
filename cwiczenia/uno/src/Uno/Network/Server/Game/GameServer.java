package Uno.Network.Server.Game;

import Uno.Engine.Game;
import Uno.Network.Server.Chat.ChatHistory;
import Uno.Network.Server.Chat.ChatMessage;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.ClientRequest.RequestType;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.Server;
import Uno.Network.Server.ServerClient;
import Uno.Network.Server.ServerEvent;

import java.io.IOException;
import java.util.HashMap;

public class GameServer {
    private GameState gameState = GameState.LOBBY;
    private Server server;
    private Game game;
    private ChatHistory chatHistory = new ChatHistory();
    private HashMap<ServerClient, GameClient> clients = new HashMap<>();


    public GameServer(int port) throws IOException {
        server = new Server(port);
        server.addEventListener(new ServerEventHandler());
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

    public HashMap<ServerClient, GameClient> getClients() {
        return clients;
    }

    private class ServerEventHandler implements ServerEvent {

        @Override
        public void onClientConnected(ServerClient serverClient) {

        }

        @Override
        public void onClientDisconnected(ServerClient serverClient) {
            if(!clients.containsKey(serverClient)) {
                // dont care
                return;
            }
            // delete joined players only if it's still lobby
            if(gameState == GameState.IN_PROGRESS) {
                clients.get(serverClient).setConnected(false);
            }
            else {
                clients.remove(serverClient);
            }
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
                System.out.println("FIX ME XDXD");
            }
        }

        @Override
        public void onClientReadError(ServerClient serverClient, String errorMessage) {
            System.out.println("CLIENT READ ERROR " + errorMessage);
        }
    }








}
