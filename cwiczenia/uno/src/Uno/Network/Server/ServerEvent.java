package Uno.Network.Server;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Message.Message;

import java.nio.channels.SelectionKey;

public interface ServerEvent {
    void onServerStart();
    void onClientConnected(ServerClient serverClient);
    void onClientDisconnected(ServerClient serverClient);
    void onClientRead(ServerClient serverClient, ClientRequest request);

    void onClientReadError(ServerClient serverClient, String errorMessage);
}
