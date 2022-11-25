package GUI.Panels;

import plansza.Plansza;
import plansza.Stan;

import javax.swing.*;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Arrays;

public class Board extends JPanel {
    private static final int maxFieldSize = 75;
    private static final int minFieldSize = 25;
    private Field[][] fields;

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
        this.setLayout(new GridLayout(plansza.getN(), plansza.getM()));

        this.fields = new Field[plansza.getM()][plansza.getN()];

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
                super.componentResized(e);
                Dimension boardSize= calculateBoardSize(plansza);
                setPreferredSize(boardSize);

                int fSize = calculateFieldSize(plansza);
                FieldIconStore.scaleAll(fSize);

            }
        });


        for(int j = 0 ; j < plansza.getN(); j++) {
            for(int i = 0; i < plansza.getM(); i++) {
                    Field field = new Field(plansza.getField(i, j), new FieldActionCallback() {
                        @Override
                        public void fieldRevealed(Field field) {
                            plansza.revealField(field.getPole())
                                    .ifPresent(pola -> Arrays.stream(pola).forEach(pole -> fields[pole.getM()][pole.getN()].refreshField()));
                        }

                        @Override
                        public void fieldFlagToggled(Field field) {
                            plansza.toggleFlag(field.getPole())
                                    .ifPresent(pole -> fields[pole.getM()][pole.getN()].refreshField());
                        }
                    });
                    this.add(field);
                    this.fields[i][j] = field;

            }
        }
    }
}
