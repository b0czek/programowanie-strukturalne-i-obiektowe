package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public interface RequestHandler {
    void execute(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException;

}
