package GUI.Views;

import GUI.Panels.Board;
import plansza.Plansza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameView extends View {



    private Board board;
    public GameView(Plansza plansza) {
        super();
        this.board = new Board(plansza);
        this.add(this.board);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                System.out.println(board.getWidth());
                board.setSize(getWidth(),getHeight());
            }
        });
    }
}
