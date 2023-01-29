package Uno.GUI.Views;

import Uno.GUI.Dialogs.HostDialog;
import Uno.GUI.Dialogs.JoinDialog;
import Uno.GUI.GUI;
import Uno.GUI.Providers.ClientProvider;
import Uno.GUI.Providers.ServerProvider;
import Uno.Network.Server.Game.GameState;
import Uno.Network.Server.Message.MessageType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

public class MainMenu extends View {
    private GUI  gui;
    private JButton hostButton;
    private JButton joinButton;
    private JButton quit;

    private JLabel title;
    private JLabel x;
    private JLabel y;

    public MainMenu(GUI gui) {
        super(gui::viewSwitcher);
        this.gui = gui;

        this.setLayout(new GridLayout(2, 3));
        title = new JLabel("WELCOME TO DOS", SwingConstants.CENTER);
        quit = new JButton("QUIT");
        hostButton = new JButton("HOST");
        joinButton = new JButton("JOIN");
        x = new JLabel("");
        y = new JLabel("");

        this.add(x);
        this.add(title);
        this.add(y);
        this.add(joinButton);
        this.add(hostButton);
        this.add(quit);

        hostButton.addActionListener(e -> {
            HostDialog dialog = new HostDialog(this.getJFrame());
            dialog.setVisible(true);
            String username = dialog.getUsername();
            if(username == null) {
                return;
            }
            try {
                ServerProvider.init(() -> {
                    if(!clientConnect(username, "localhost")) {
                        try {
                            ServerProvider.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to open a server, reason: " + ex.getMessage(), "Could not start a lobby", JOptionPane.ERROR_MESSAGE);
            }

        });

        joinButton.addActionListener(e -> {
            JoinDialog dialog = new JoinDialog(this.getJFrame());
            dialog.setVisible(true);
            String address = dialog.getAddress();
            String username = dialog.getUsername();
            if(username == null || address == null) {
                return;
            }
            clientConnect(username, address);


        });

        quit.addActionListener(e -> System.exit(0));

    }
    public boolean clientConnect(String username, String address) {
        String[] addressSplit = address.split(":");
        int port = 42069;
        if(addressSplit.length == 2) {
            try {
                port = Integer.parseInt(addressSplit[1]);
                address = addressSplit[0];
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Port number you entered is not valid number", "Invalid port number", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        }

        try {
            ClientProvider.init(username, address, port);
            View lobby = new Lobby(viewSwitcher);
            View game = new Game(viewSwitcher);
            View finished = new Finished(viewSwitcher);
            gui.addView(lobby, GameState.LOBBY.toString());
            gui.addView(game, GameState.IN_PROGRESS.toString());
            gui.addView(finished, GameState.FINISHED.toString());

            ClientProvider.getGameClient().addMessageHandler(MessageType.GAME_STATE, o -> {
                GameState gameState = (GameState) o;
                switchView(gameState.toString());
            });
            ClientProvider.getGameClient().addDisconnectHandler(() -> {
                System.out.println("client disconnected");
                gui.removeView(lobby);
                gui.removeView(game);
                gui.removeView(finished);
                this.getJFrame().setSize(600,400);
                switchView("MainMenu");
            });


            ClientProvider.getGameClient().join((result) -> {
                if(result.isRequestFailed()) {
                    JOptionPane.showMessageDialog(this, result.getStatusMessage(), "Failed to join server", JOptionPane.ERROR_MESSAGE);

                }
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Connection could not be established: " + e.getMessage(), "Could not connect to server", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        return true;
    }
}
