package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class Lobby {
    public static void handleClientReady(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        gameServer.getClients().get(serverClient).setReady(true);
        serverClient.sendEmptyOkResponse(request);

        if(gameServer.getClients().values().stream().allMatch(gameClient -> gameClient.isReady())) {
            // TODO: implement changing gamemode to in progress
        }
    }
    public static void handleClientNotReady(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        gameServer.getClients().get(serverClient).setReady(false);
        serverClient.sendEmptyOkResponse(request);
    }
}
