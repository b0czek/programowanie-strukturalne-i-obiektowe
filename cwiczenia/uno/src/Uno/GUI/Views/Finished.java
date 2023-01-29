package Uno.GUI.Views;

import Uno.Engine.Player.PlayerInfo;
import Uno.GUI.Providers.ClientProvider;
import Uno.Network.Server.Message.MessageType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

public class Finished extends View{

    public Finished(Consumer<String> viewSwitcher) {
        super(viewSwitcher);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("");
        t.setFont(new Font("Arial", Font.BOLD, 32));
        JLabel t2 = new JLabel("won the game");

        JButton backButton = new JButton("GO TO MAIN MENU");
        backButton.addActionListener(e -> {
            try {
                ClientProvider.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        ClientProvider.getGameClient().addMessageHandler(MessageType.ROUND_END, p -> {
            PlayerInfo winner = (PlayerInfo) p;
            SwingUtilities.invokeLater(() -> {
                t.setText(winner.getName());
            });
        });

        this.add(t);
        this.add(t2);
        this.add(backButton);
    }
}
