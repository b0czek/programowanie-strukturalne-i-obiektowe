package Uno.Network.Client;

import Uno.Network.Server.Chat.ChatMessage;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.ClientRequest.RequestType;
import Uno.Network.Server.Message.MessageType;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class BasicClient {
    private static boolean isConnected = true;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("address: ");
        String address = scanner.nextLine();
        GameClient client = new GameClient(username, address, 42069);

        client.addMessageHandler(MessageType.CHAT_MESSAGE, (message) -> {
            ChatMessage chatMessage = (ChatMessage) message;
            System.out.println(chatMessage);
        });
        client.addDisconnectHandler(() -> isConnected = false);



        while(isConnected) {
            String message = scanner.nextLine();
            client.sendRequest(new ClientRequest(RequestType.CHAT_MESSAGE, message));
        }
    }
}
