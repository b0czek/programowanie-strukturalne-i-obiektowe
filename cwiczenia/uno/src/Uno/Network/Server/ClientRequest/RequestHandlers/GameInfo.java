package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameClient;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class GameInfo {
    public static void handleClientsData(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendOkResponse(request, MessageType.CLIENTS_DATA, getClientsData(gameServer));

    }
    public static void handleClientData(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendOkResponse(request, MessageType.CLIENT_DATA, gameServer.getClients().get(serverClient));
    }
    public static void handleGameState(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendOkResponse(request, MessageType.GAME_STATE, gameServer.getGameState());
    }


    public static GameClient[] getClientsData(GameServer gameServer) {
        return gameServer.getClients().values().toArray(GameClient[]::new);
    }
}
