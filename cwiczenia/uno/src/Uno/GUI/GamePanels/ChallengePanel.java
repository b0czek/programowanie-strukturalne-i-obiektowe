package Uno.GUI.GamePanels;

import Uno.Engine.Card.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class ChallengePanel extends JPanel {
    private Consumer<Boolean> onSelect;

    public ChallengePanel(Consumer<Boolean> onSelect) {
        this.onSelect = onSelect;

        this.setLayout(new GridLayout(2,2));
        String[] actions = new String[] { "CHALLENGE", "PASS"};
        for(int i = 0 ; i < actions.length; i ++) {
            JButton b = new JButton(actions[i].toString());
            b.addActionListener(new SelectionHandler(i == 0));
            add(b);
        }


    }


    private class SelectionHandler implements  ActionListener {

        private boolean value ;

        public SelectionHandler(boolean value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            onSelect.accept(value);
        }
    }

}
