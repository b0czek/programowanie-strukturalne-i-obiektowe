package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class Heartbeat {
    public static void handleHeartbeat(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        if(!gameServer.getClients().get(serverClient).isConnected()) {
            serverClient.sendEmptyErrorResponse(request, "cannot heartbeat non connected player");
        }
        gameServer.getClients().get(serverClient).updateLastHeartbeatTime();
        serverClient.sendEmptyOkResponse(request);
    }
}
