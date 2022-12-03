package Minesweeper.GUI.Views;

import Minesweeper.GUI.Panels.Board.Field;
import Minesweeper.ProjectInfo;
import Minesweeper.plansza.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MenuView extends View {

    private String createTitleString() {
        Random generator = new Random();
        int start = generator.nextInt(Field.labelColors.length);
        String title = IntStream.range(0, ProjectInfo.TITLE.length())
                .mapToObj(i -> {
                    Color color = Field.labelColors[(start+i) % Field.labelColors.length];
                    return String.format("<font color=\"#%02X%02X%02X\">%c</font>", color.getRed(), color.getGreen(), color.getBlue(), ProjectInfo.TITLE.charAt(i));
                })
                .collect(Collectors.joining());
        return String.format("<html><b>%s</b></html>", title);
    }

    private void addComponents(MenuViewCallback callback) {
        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0 ; i < 3 ; i ++) {
            JButton button = new JButton("");
            button.setEnabled(false);
            button.setBorder(BorderFactory.createEmptyBorder());
            c.weightx = i % 2 == 0 ? 0.75 : 0.2;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i;
            c.gridy = 0;
            this.add(button, c);
        }
        c.weighty = 0.05;

        JLabel title = new JLabel(createTitleString());
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        this.add(title, c);

        for (int i = 0; i < Difficulty.difficulties.length; i++) {
            Difficulty difficulty = Difficulty.difficulties[i];
            JButton b = new JButton(difficulty.getDifficultyName().toUpperCase());

            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = i+2;
            c.ipady = 20;
            c.ipadx = 20;
            c.insets = new Insets(10,0,0,0);

            b.setContentAreaFilled(false);
            b.addActionListener(actionEvent -> callback.onDifficultySelect(difficulty));
            b.setFont(new Font("Arial", Font.BOLD, 20));
            this.add(b, c);

        }


        JLabel authors = new JLabel("Autorzy: " + String.join(", ", ProjectInfo.AUTHORS));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.8;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy++;
        c.ipady = 0;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.PAGE_END;
        this.add(authors, c);


    }



    public MenuView(MenuViewCallback callback) {
        super();
        this.setLayout(new GridBagLayout());
        addComponents(callback);



    }
}
