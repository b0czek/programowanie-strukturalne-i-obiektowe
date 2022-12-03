package Minesweeper.GUI.Panels.TopBar;

import Minesweeper.plansza.Plansza;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TopBar extends JPanel {
    public GameTimer gameTimer;

    private JButton createImageButton(String path, int size, String fallbackText) {
        JButton button;
        try {
            button = new JButton((new ImageIcon(ImageIO.read(new File(path)).getScaledInstance(size,size, Image.SCALE_FAST))));
        } catch (IOException e) {
            button = new JButton(fallbackText);
        }
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);

        return button;
    }

    public TopBar(Plansza plansza, TopBarCallback callback) {
        super();

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.7;
        c.ipady = 10;
        c.gridx = 0;
        c.insets = new Insets(10, 0,0 ,0);
        c.weightx = 1.0;
        c.gridx = 1;

        JButton back = createImageButton("assets/Back.png", 40, "<-");
        back.addActionListener(actionEvent -> callback.onBackButton());
        this.add(back,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.01;
        c.gridx = 2;

        JButton reset = createImageButton("assets/Icon.png", 40, "reset");
        reset.addActionListener(actionEvent -> {
            callback.onResetButton();
            gameTimer.reset();
        });
        this.add(reset,c);


        c.weightx = 1.0;
        c.gridx = 3;

        gameTimer = new GameTimer(plansza);
        gameTimer.setHorizontalAlignment(SwingConstants.RIGHT);
        gameTimer.setVerticalAlignment(SwingConstants.CENTER);
        gameTimer.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(gameTimer,c);


    }

}
