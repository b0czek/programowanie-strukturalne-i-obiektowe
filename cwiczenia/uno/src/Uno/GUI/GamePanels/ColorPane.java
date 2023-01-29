package Uno.GUI.GamePanels;

import Uno.Engine.Card.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class ColorPane extends JPanel {
    private Consumer<Color> onSelect;

    public ColorPane(Consumer<Color> onSelect) {
        this.onSelect = onSelect;

        this.setLayout(new GridLayout(2,2));
        Color[] colors = new Color[] { Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};
        for(int i = 0 ; i < colors.length; i ++) {
            JButton b = new JButton(colors[i].toString());
            b.addActionListener(new SelectionHandler(colors[i]));
            add(b);
        }


    }


    private class SelectionHandler implements  ActionListener {

        private Color color ;

        public SelectionHandler(Color color) {
            this.color = color;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            onSelect.accept(color);
        }
    }

}
