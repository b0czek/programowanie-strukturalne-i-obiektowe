package Uno.Network.Client;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.ClientRequest.RequestType;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.Message.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class GameClient {
    private final String username;
    private Client client;
    private ConcurrentHashMap<MessageType, ArrayList<Consumer<Object>>> messageHandlers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, Consumer<Response>> responseHandlers = new ConcurrentHashMap<>();
    private Timer timer = new Timer();

    public GameClient(String username, String host, int port) throws IOException {
        client = new Client(host, port);
        client.addEventListener(new ClientEventHandler());

        for(MessageType messageType : MessageType.values()) {
            messageHandlers.put(messageType, new ArrayList<>());
        }
        this.username = username;
    }

    public void join() throws IOException {
        this.sendRequest(new ClientRequest(RequestType.JOIN, username), (Response response) -> {
            if(response.isRequestFailed()) {
                System.out.println("failed to join the game");
            }
            else {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            sendRequest(new ClientRequest(RequestType.HEARTBEAT));
                        } catch (IOException e) {
                            System.out.println("failed to send heartbeat");
                        }
                    }
                }, 0, 5000);
            }
        });
    }

    private class ClientEventHandler implements  ClientEvent {

        @Override
        public void onDisconnect() {
            timer.cancel();
        }

        @Override
        public void onMessage(Message message) {
            if(message instanceof Response) {
                Response response = (Response) message;
                var handler = responseHandlers.get(response.getContextUUID());
                if(handler != null) {
                    handler.accept(response);
                    responseHandlers.remove(response.getContextUUID());
                }
            }
            messageHandlers.get(message.getMessageType()).forEach(handler -> handler.accept(message.getAttachment()));
        }

        @Override
        public void onReadError(String errorMessage) {

        }
    }

    public void addMessageHandler(MessageType messageType, Consumer<Object> messageHandler) {
        messageHandlers.get(messageType).add(messageHandler);
    }
    public boolean removeMessageHandler(MessageType messageType, Consumer<Object> messageHandler) {
        return messageHandlers.get(messageType).remove(messageHandler);
    }

    public void sendRequest(ClientRequest request) throws IOException {
        client.writeRequest(request);
    }

    public void sendRequest(ClientRequest request, Consumer<Response> handler) throws IOException {
        responseHandlers.put(request.getRequestUuid(), handler);
        sendRequest(request);
    }
}
