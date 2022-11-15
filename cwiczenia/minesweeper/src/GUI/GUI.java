package GUI;
import GUI.Views.GameView;
import GUI.Views.MenuView;
import GUI.Views.View;
import plansza.Plansza;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame frame;

    private Plansza plansza;

    public GUI(Plansza plansza) {
        this.plansza = plansza;

        frame = new JFrame();
        frame.setTitle("kocham wszystkie kotki na calym swiecie");
        frame.setBounds(0, 0, 800, 800);

        frame.getContentPane().setLayout(new CardLayout());
        this.addView(new MenuView(difficulty -> {
              this.setCurrentView(GameView.class.getName());
        }));
        this.addView(new GameView(plansza));



        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addView(View view) {
        this.frame.getContentPane().add(view, view.getClass().getName());
    }

    private void setCurrentView(String viewName) {
        Container content = this.frame.getContentPane();
        ((CardLayout)this.frame.getContentPane().getLayout()).show(content, viewName);
    }
}
