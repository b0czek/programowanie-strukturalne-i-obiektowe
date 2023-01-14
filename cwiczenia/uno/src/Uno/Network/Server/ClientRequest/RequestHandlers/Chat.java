package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.Chat.ChatMessage;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class Chat {
    public static void handleChatMessage(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        String message = (String) request.getAttachment();
        if(message.length() == 0) {
            serverClient.sendEmptyErrorResponse(request, "message cannot be empty");
            return;
        }
        if(message.length() > 100) {
            serverClient.sendEmptyErrorResponse(request, "message too long");
            return;
        }
        ChatMessage chatMessage = new ChatMessage(message, gameServer.getClients().get(serverClient).getName());
        gameServer.getChatHistory().add(chatMessage);
        System.out.println("new chat message: "+chatMessage);
        gameServer.getServer().broadcast(new Message(MessageType.CHAT_MESSAGE, chatMessage));
    }

    public static void handleChatHistory(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendOkResponse(request, MessageType.CHAT_HISTORY, gameServer.getChatHistory());
    }


}
