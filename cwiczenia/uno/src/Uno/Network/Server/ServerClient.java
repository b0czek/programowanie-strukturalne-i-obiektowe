package Uno.Network.Server;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Message.Response;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ServerClient {
    private SelectionKey selectionKey;
    private String remoteAddress;
    public ServerClient(SelectionKey selectionKey, String remoteAddress) {
        this.selectionKey = selectionKey;
        this.remoteAddress = remoteAddress;
    }

    public void sendMessage(Message message) throws IOException {
        SocketChannel socketChannel = ((SocketChannel) selectionKey.channel());
        ByteBuffer bb = Message.wrapMessage(message);
        int w;
        while ((w = socketChannel.write(bb)) > 0 || bb.remaining() > 0) {
        }
    }

    public void sendEmptyErrorResponse(ClientRequest responseTo, String statusMessage) throws IOException {
        this.sendResponse(responseTo, MessageType.EMPTY_RESPONSE, null, statusMessage,true);
    }
    public void sendEmptyOkResponse(ClientRequest responseTo) throws IOException {
        this.sendResponse(responseTo, MessageType.EMPTY_RESPONSE, null, "OK", false);
    }
    public void sendOkResponse(ClientRequest responseTo, MessageType messageType, Object attachment) throws IOException {
        this.sendResponse(responseTo, messageType, attachment, "OK", false);
    }

    public void sendResponse(ClientRequest responseTo, MessageType messageType, Object attachment, String statusMessage, boolean requestFailed) throws IOException {

        this.sendMessage(new Response(messageType, attachment, responseTo.getRequestUuid(), statusMessage, requestFailed));
    }

    public static ServerClient getClientFromKey(SelectionKey key) {
        return (ServerClient) key.attachment();
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }
}
