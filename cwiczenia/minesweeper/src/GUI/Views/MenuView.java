package GUI.Views;

import GUI.Difficulty.Difficulty;

import javax.swing.*;



public class MenuView extends View {


    public MenuView(MenuViewCallback callback) {
        super();

        for (Difficulty difficulty: Difficulty.difficulties) {

            JButton b = new JButton(difficulty.getDifficultyName());
            b.addActionListener(actionEvent -> callback.onDifficultySelect(difficulty));
            this.add(b);

        }

    }

}
