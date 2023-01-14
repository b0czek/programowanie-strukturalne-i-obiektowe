package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class GameInfo {
    public static void handleClientsInfo(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendOkResponse(request, MessageType.CLIENTS_INFO, gameServer.getClients().values().toArray(ServerClient[]::new));

    }
    public static void handleClientInfo(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendOkResponse(request, MessageType.CLIENT_INFO, gameServer.getClients().get(serverClient));
    }
    public static void handleGameState(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendOkResponse(request, MessageType.GAME_STATE, gameServer.getGameState());
    }
}
