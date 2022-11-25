package GUI.Views;

import GUI.Panels.Board;
import plansza.Plansza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameView extends View {



    private Board board;
    private Plansza plansza;
    public GameView(Plansza plansza, GameViewCallback callback) {
        super();
        this.plansza = plansza;

        JButton back = new JButton("<-");
        back.addActionListener(actionEvent -> callback.onMenuReturn());
        this.add(back);


//        JButton reset = new JButton("reset");
//        reset.addActionListener(actionEvent -> {
//            plansza.init(plansza.getSelectedDifficulty());
//            createBoard(plansza);
//            resizeBoard();
//        });
//        this.add(reset);

        this.createBoard(plansza);


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                System.out.println(board.getWidth());
                resizeBoard();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                resizeBoard();
            }
        });
    }

    private void resizeBoard(){
        board.setSize(getWidth(),getHeight());
    }

    private void createBoard(Plansza plansza) {
        if(this.board != null) {
            this.remove(this.board);

        }
        this.board = new Board(plansza);
        this.add(this.board);
        this.resizeBoard();
    }

    // reset board on returning view
    @Override
    public void onViewShown() {
        super.onViewShown();
        this.createBoard(plansza);
    }
}
