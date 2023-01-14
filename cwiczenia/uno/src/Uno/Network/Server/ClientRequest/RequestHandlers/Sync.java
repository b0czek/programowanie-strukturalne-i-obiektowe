package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class Sync {
    public static void handleSync(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendEmptyOkResponse(request);
    }

}
