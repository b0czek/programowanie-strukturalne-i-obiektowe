package Minesweeper.GUI.Views;

import Minesweeper.GUI.Panels.Board.Board;
import Minesweeper.GUI.Panels.TopBar.TopBar;
import Minesweeper.GUI.Panels.TopBar.TopBarCallback;
import Minesweeper.plansza.Plansza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameView extends View {

    private float margin = 0.1f;

    private Board board;
    private TopBar topBar;

    private Plansza plansza;
    public GameView(Plansza plansza, GameViewCallback callback) {
        super();
        this.plansza = plansza;

        this.setLayout(new GridBagLayout());
        this.createTopBar(callback);


        this.createBoard(plansza);


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeBoard();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                resizeBoard();
            }
        });
    }

    private void createTopBar(GameViewCallback callback) {

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridy = 0;
        this.topBar = new TopBar(plansza, new TopBarCallback() {
            @Override
            public void onBackButton() {
                callback.onMenuReturn();
            }

            @Override
            public void onResetButton() {
                plansza.init(plansza.getSelectedDifficulty());
                createBoard(plansza);
                revalidate();
            }
        });

        this.add(topBar, c);
    }

    private int subtractMargin(int val) {
        return (int) (val - val*margin);
    }

    private void resizeBoard(Dimension dimension){
        Dimension targetSize = new Dimension(subtractMargin(dimension.width),subtractMargin(dimension.height - this.topBar.getHeight()));
        board.setSize(targetSize);
    }
    private void resizeBoard() {
        resizeBoard(getSize());
    }

    private void createBoard(Plansza plansza) {
        if(this.board != null) {
            this.remove(this.board);

        }

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.gridy = 1;

        this.board = new Board(plansza);
        this.add(this.board, c);
        this.resizeBoard();
    }

    // reset board on returning view
    @Override
    public void onViewShown() {
        super.onViewShown();
        this.createBoard(plansza);
        this.revalidate();
        this.topBar.gameTimer.reset();
    }
}
