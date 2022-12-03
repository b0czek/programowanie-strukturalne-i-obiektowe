package Minesweeper.GUI;
import Minesweeper.GUI.Views.GameView;
import Minesweeper.GUI.Views.MenuView;
import Minesweeper.GUI.Views.View;
import Minesweeper.plansza.Plansza;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class GUI {
    private JFrame frame;

    private Plansza plansza;

    public GUI(Plansza plansza) {
        this.plansza = plansza;

        frame = new JFrame();
        frame.setTitle("kocham wszystkie kotki na calym swiecie");

        try {
            frame.setIconImage(ImageIO.read(new File("assets/Icon.png")));
        }
        catch(IOException e) {
            System.out.println("could not load icon asset");
        }

        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setLayout(new CardLayout());
        this.addView(new MenuView(difficulty -> {
            this.plansza.init(difficulty);
            this.setCurrentView(GameView.class.getName());
        }));
        this.addView(new GameView(plansza, () -> this.setCurrentView(MenuView.class.getName())));


        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addView(View view) {
        this.frame.getContentPane().add(view, view.getClass().getName());
    }

    private void setCurrentView(String viewName) {
        Container content = this.frame.getContentPane();
        Optional<Component> component = Arrays.stream(content.getComponents())
                                                .filter(c -> c.getClass().getName().matches(viewName))
                                                .findAny();
        if(component.isEmpty()) {
            // should throw i guess
            return;
        }
        ((CardLayout)content.getLayout()).show(content, viewName);
        ((View)component.get()).onViewShown();
        this.frame.setSize(this.frame.getWidth()-1, this.frame.getHeight());

    }
}
