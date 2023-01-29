package Uno.GUI.Views;

import Uno.GUI.Providers.ClientProvider;
import Uno.Network.Client.Client;
import Uno.Network.Client.GameClient;
import Uno.Network.Server.Chat.ChatHistory;
import Uno.Network.Server.Chat.ChatMessage;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.ClientRequest.RequestType;
import Uno.Network.Server.Message.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Lobby extends View {
    private JList clients;
    private DefaultListModel clientsListModel;

    private JButton readyButton;

    private JTextArea chat;
    private JScrollPane chatScrollPane;

    private JTextField chatInput;

    public Lobby(Consumer<String> viewSwitcher) {
        super(viewSwitcher);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel headerLabel;
        headerLabel = new JLabel("DOS", SwingConstants.CENTER);
        JPanel p = new JPanel();
        p.add(headerLabel);
        this.add(p);

        clientsListModel = new DefaultListModel();
        clients = new JList(clientsListModel);


        this.add(new JScrollPane(clients));


        this.readyButton = new JButton("READY");

        this.add(readyButton);

        chat = new JTextArea();
        chat.setRows(8);
        chat.setEditable(false);
        chatScrollPane = new JScrollPane(chat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(chatScrollPane);


        this.chatInput = new JTextField();
        this.add(chatInput);

        addMessageHandlers();

    }

    public void addMessageHandlers() {
        GameClient client = ClientProvider.getGameClient();
        client.addMessageHandler(MessageType.CHAT_MESSAGE, (message) -> {
            ChatMessage chatMessage = (ChatMessage) message;
            SwingUtilities.invokeLater(() -> {

                chat.append(chatMessage.formatMessage());
                chatScrollPane.getVerticalScrollBar().setValue(chatScrollPane.getVerticalScrollBar().getMaximum());
            });
        });

        client.addMessageHandler(MessageType.CHAT_HISTORY, (history) -> {
            ChatHistory chatHistory = (ChatHistory) history;
            SwingUtilities.invokeLater(() -> {
                chat.setText("");
                chatHistory.forEach(message -> chat.append(message.formatMessage()));
                chatScrollPane.getVerticalScrollBar().setValue(chatScrollPane.getVerticalScrollBar().getMaximum());

            });

        });

        client.addMessageHandler(MessageType.CLIENTS_DATA, (c) -> {
            Uno.Network.Server.Game.GameClient[] gameClients = (Uno.Network.Server.Game.GameClient[]) c;
            SwingUtilities.invokeLater(() -> {

                clients.clearSelection();
                clientsListModel.removeAllElements();
                Arrays.stream(gameClients).forEach(gameClient -> clientsListModel.addElement(formatClient(gameClient)));
            });

        });

        client.addMessageHandler(MessageType.CLIENT_JOINED, (c) -> {
            Uno.Network.Server.Game.GameClient gameClient = (Uno.Network.Server.Game.GameClient) c;
            SwingUtilities.invokeLater(() -> {

                clientsListModel.addElement(formatClient(gameClient));
            });
        });

        chatInput.addActionListener(actionEvent -> {
            try {
                client.sendRequest(new ClientRequest(RequestType.CHAT_MESSAGE, chatInput.getText()), response -> {
                    if(response.isRequestFailed()) {
                        System.out.println(response.getStatusMessage());
                    }
                    else {
                        chatInput.setText("");
                    }
                });
            } catch (IOException e) {
                System.out.println("failed to send message " + e.getMessage());
            }
        });

        readyButton.addActionListener(actionEvent -> {
            try {

                client.sendRequest(new ClientRequest(RequestType.TOGGLE_READY, null), (response) -> {
                    if(response.isRequestFailed()) {
                        System.out.println(response.getStatusMessage());
                    }
                });
            }
            catch(IOException e) {

                System.out.println("failed to send message " + e.getMessage());
            }
        });


    }
    private String formatClient(Uno.Network.Server.Game.GameClient gameClient) {
        return gameClient.getName() + " - " + (gameClient.isReady() ? "READY" : "NOT READY");
    }

}
