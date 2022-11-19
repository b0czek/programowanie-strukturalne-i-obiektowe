package GUI.Panels;

import GUI.Field;
import plansza.Plansza;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel {
    private static final int maxFieldSize = 75;
    private static final int minFieldSize = 25;
    private Field[] fields;
    private ImageIcon bombIcon;
    private Image bomb;

    private int calculateFieldSize(Plansza plansza) {
        int m = Math.min(this.getWidth() / plansza.getM(), this.getHeight() / plansza.getN());
        return Math.max(Math.min(maxFieldSize, m), minFieldSize);

    }
    public Dimension calculateBoardSize(Plansza plansza) {
        int fSize = calculateFieldSize(plansza);
        return new Dimension(plansza.getM() * fSize, plansza.getN() * fSize);
    }

    private void setFieldIcon(Field field) {
        field.setIcon(this.bombIcon);
        field.setDisabledIcon(this.bombIcon);
    }


    public Board(Plansza plansza) {
        super();
        this.setLayout(new GridLayout(plansza.getN(), plansza.getM()));

        this.fields = new Field[plansza.getN() * plansza.getM()];


        try {
            this.bomb = ImageIO.read( new File("assets/cat.png"));
            this.bombIcon = (new ImageIcon(this.bomb));


        }
        catch(IOException ex) {
            System.out.println("dupa");
        }

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension boardSize= calculateBoardSize(plansza);
                setPreferredSize(boardSize);

                int fSize = calculateFieldSize(plansza);
                bombIcon = new ImageIcon(bomb.getScaledInstance(fSize, fSize, Image.SCALE_FAST));

            }
        });


        for(int i = 0; i < plansza.getM(); i++) {
            for(int j = 0 ; j < plansza.getN(); j++) {
                    Field field = new Field(i,j);
                    field.addActionListener(actionEvent -> {
                        field.setEnabled(false);
                        if(plansza.getCell(field.getM(), field.getN())) {
                            this.setFieldIcon(field);
                            field.addComponentListener(new ComponentAdapter() {
                                @Override
                                public void componentResized(ComponentEvent e) {
                                    super.componentResized(e);
                                    // refresh icon on resize
                                    setFieldIcon(field);

                                }
                            });
                        }
                    });
                    this.add(field);

            }
        }
    }
}
