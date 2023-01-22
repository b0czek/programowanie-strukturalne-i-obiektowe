package Uno.Network.Client;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.ClientRequest.RequestType;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.Message.Response;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class GameClient {
    private final String username;
    private final String host;
    private final int port;
    private Client client;
    private final ConcurrentHashMap<MessageType, ArrayList<Consumer<Object>>> messageHandlers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Consumer<Response>> responseHandlers = new ConcurrentHashMap<>();
    private final ArrayList<Runnable> disconnectHandlers = new ArrayList<>();

    private final Timer timer = new Timer();

    public GameClient(String username, String host, int port) throws IOException {

        for(MessageType messageType : MessageType.values()) {
            messageHandlers.put(messageType, new ArrayList<>());
        }
        this.username = username;
        this.host = host;
        this.port = port;

        this.connect();
    }

    private void connect() throws IOException {
        client = new Client(host, port);
        client.addEventListener(new ClientEventHandler());
        client.start();
    }

    public void reconnect() throws IOException {
        this.connect();
        this.join();
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
            disconnectHandlers.forEach(Runnable::run);
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
            System.out.println("error reading message from server " + errorMessage);
        }
    }

    public void addDisconnectHandler(Runnable handler) {
        disconnectHandlers.add(handler);
    }
    public boolean removeDisconnectHandler(Runnable handler) {
        return disconnectHandlers.remove(handler);
    }

    public void addMessageHandler(MessageType messageType, Consumer<Object> messageHandler) {
        messageHandlers.get(messageType).add(messageHandler);
    }
    public boolean removeMessageHandler(MessageType messageType, Consumer<Object> messageHandler) {
        return messageHandlers.get(messageType).remove(messageHandler);
    }

    public void sendRequest(ClientRequest request) throws IOException {
        if(request.isAttachmentValid()) {
            throw new InvalidObjectException("invalid object attached to the request");
        }
        client.writeRequest(request);
    }

    public void sendRequest(ClientRequest request, Consumer<Response> handler) throws IOException {
        responseHandlers.put(request.getRequestUuid(), handler);
        sendRequest(request);
    }
}
