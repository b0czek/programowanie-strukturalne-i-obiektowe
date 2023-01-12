package Uno.Network.Server;

import Uno.Network.Server.Message.Message;

import java.nio.channels.SelectionKey;

public interface ServerEvent {
    void onClientConnected(SelectionKey key);
    void onClientDisconnected(SelectionKey key);
    void onClientRead(SelectionKey key, Message message);
}
