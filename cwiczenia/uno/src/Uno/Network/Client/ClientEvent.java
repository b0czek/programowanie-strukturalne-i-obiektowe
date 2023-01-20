package Uno.Network.Client;

import Uno.Network.Server.Message.Message;

public interface ClientEvent {
    void onDisconnect();
    void onMessage(Message message);
    void onReadError(String errorMessage);

}
