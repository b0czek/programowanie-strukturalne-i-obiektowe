package Uno.GUI.GamePanels;

import Uno.Engine.Player.PlayerInfo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayerPanel extends JPanel {
    public PlayerPanel(PlayerInfo playerInfo, boolean currentPlayer) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Player: " + playerInfo.getName()));
        this.add(new JLabel("Cards in hand:" + playerInfo.getCardsInHandCount()));
        if(currentPlayer) {
            this.setBorder(new LineBorder(Color.GREEN, 3));
        }
        if(playerInfo.isYelledUno()) {
            this.setBackground(Color.BLUE);
        }
    }
}
