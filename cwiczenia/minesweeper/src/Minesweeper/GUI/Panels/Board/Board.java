package Minesweeper.GUI.Panels.Board;

import Minesweeper.GUI.Panels.Board.VictoryParticles.SplitterSystem;
import Minesweeper.GUI.Panels.Board.VictoryParticles.VictoryParticles;
import Minesweeper.plansza.Plansza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Arrays;

public class Board extends JLayeredPane {
    private static final int maxFieldSize = 75;
    private static final int minFieldSize = 1;
    private Field[][] fields;
    private Plansza plansza;

    private JPanel board;
    private VictoryParticles victoryParticles;

    private int calculateFieldSize(Plansza plansza) {
        int m = Math.min(this.getWidth() / plansza.getM(), this.getHeight() / plansza.getN());
        return Math.max(Math.min(maxFieldSize, m), minFieldSize);

    }
    public Dimension calculateBoardSize(Plansza plansza) {
        int fSize = calculateFieldSize(plansza);
        return new Dimension(plansza.getM() * fSize, plansza.getN() * fSize);
    }


    public Board(Plansza plansza) {
        super();
        this.plansza = plansza;

        this.board = new JPanel();
        this.board.setLayout(new GridLayout(plansza.getN(), plansza.getM()));

        this.victoryParticles = new VictoryParticles(new SplitterSystem());

        this.add(this.board, JLayeredPane.DEFAULT_LAYER);
        this.add(this.victoryParticles, JLayeredPane.POPUP_LAYER );

        this.fields = new Field[plansza.getM()][plansza.getN()];

        FieldFontProvider.init(20);
        try {
            FieldIconStore.init();
        }
        catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("dupa");
        }

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                Dimension boardSize= calculateBoardSize(plansza);
                setPreferredSize(boardSize);

                int fSize = calculateFieldSize(plansza);
                FieldIconStore.scaleAll(fSize);

                FieldFontProvider.changeFontSize(fSize - 5);

                board.setSize   (boardSize);
                victoryParticles.setSize(boardSize);
            }
        });


        for(int j = 0 ; j < plansza.getN(); j++) {
            for(int i = 0; i < plansza.getM(); i++) {
                    Field field = createField(i,j);
                    this.board.add(field);
                    this.fields[i][j] = field;

            }
        }
    }
    public Field createField(int i, int j) {
        return new Field(plansza.getField(i, j), new FieldActionCallback() {
            @Override
            public void fieldRevealed(Field field) {
                plansza.revealField(field.getPole())
                        .ifPresent(pola -> Arrays.stream(pola)
                                .forEach(pole -> fields[pole.getM()][pole.getN()].refreshField()));


                if(plansza.isGameWon()) {
                    // win
                    victoryParticles.startAnimation();
                }
            }

            @Override
            public void fieldFlagToggled(Field field) {
                plansza.toggleFlag(field.getPole())
                        .ifPresent(pole -> fields[pole.getM()][pole.getN()].refreshField());
            }
        });
    }

}
