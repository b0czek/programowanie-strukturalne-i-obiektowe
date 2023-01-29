package Uno.GUI.GamePanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class PanelsOnCircle extends JPanel {
    private static final int RADIUS = 250;
    private static final int GAP = 80;
    private static final int PREF_W = 2 * RADIUS + 2 * GAP;
    private static final int PREF_H = PREF_W ;

    private JPanel[] panels;
    private JPanel panel;
    private int width, height;
    public PanelsOnCircle(JPanel[] panels, int width, int height) {
        setLayout(new BorderLayout());

        this.panels = panels;
        this.width = width;
        this.height = height;

        addPanels();
    }

    private void addPanels() {
        if(panel != null) {
            this.remove(panel);
        }

        panel = new JPanel(null);
        int slices = panels.length + 3;

        for (int i = 2; i < slices - 1 ; i++) {
            double phi = (i * Math.PI * 2) / slices;

            JPanel smallPanel = panels[i - 2];

            int x = (int) (RADIUS * Math.sin(phi) + RADIUS - width / 2) + GAP;
            int y = (int) (RADIUS * Math.cos(phi) + RADIUS - height / 2) + GAP;
            smallPanel.setBounds(x, y, width, height);

            panel.add(smallPanel);
        }
        this.add(panel);

    }

    public void updatePanels(JPanel[] panels) {
        this.panels = panels;
        this.addPanels();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

}