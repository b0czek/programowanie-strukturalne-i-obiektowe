package GUI.Panels;

import GUI.Field;
import plansza.Plansza;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel {
    private Field[] fields;
    private ImageIcon bombIcon;


    public Board(Plansza plansza) {
        super();

        this.setLayout(new GridLayout(plansza.getN(), plansza.getM()));

        this.fields = new Field[plansza.getN() * plansza.getM()];


        try {
            this.bombIcon = new ImageIcon(ImageIO.read( new File("assets/cat.png")));

        }
        catch(IOException ex) {
            System.out.println("dupa");
        }


        for(int i = 0; i < plansza.getM(); i++) {
            for(int j = 0 ; j < plansza.getN(); j++) {
                    Field field = new Field(i,j);
                    field.addActionListener(actionEvent -> {

                        if(plansza.getCell(field.getM(), field.getN())) {
                            field.setIcon(bombIcon);
                        }
                        else {
                            field.setEnabled(false);

                        }
                    });
                    this.add(field);

            }
        }
    }
}
