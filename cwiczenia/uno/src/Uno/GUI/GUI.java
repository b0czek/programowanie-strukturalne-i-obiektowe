package Uno.GUI;

import Uno.GUI.Views.Game;
import Uno.GUI.Views.Lobby;
import Uno.GUI.Views.MainMenu;
import Uno.GUI.Views.View;
import Uno.Network.Server.Game.GameState;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public class GUI {
    private JFrame frame;

    public GUI() {
        frame = new JFrame("DOS");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new CardLayout());
//        frame.getContentPane().add(new Game(this::viewSwitcher), "game");
        frame.getContentPane().add(new MainMenu(this), "MainMenu");

    }

    public void addView(View view, String name) {
        frame.getContentPane().add(view, name);
    }
    public void removeView(View view) {
        frame.getContentPane().remove(view);
    }

    public void viewSwitcher(String viewName) {
        Container content = this.frame.getContentPane();
        ((CardLayout)content.getLayout()).show(content, viewName);
    }



}
