package GUI;
import plansza.Plansza;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class GUI {
    private JFrame frame;
    private GridLayout layout;
    private Field[] fields;
    private ImageIcon bombIcon;
    public GUI(Plansza plansza) {
        frame = new JFrame();
        frame.setTitle("kocham wszystkie kotki na calym swiecie");

        this.layout = new GridLayout(plansza.getN(), plansza.getM());
        frame.getContentPane().setLayout(this.layout);



        fields = new Field[plansza.getN() *plansza.getM()];

        frame.setBounds(100, 100, 800, 800);

        try {
            this.bombIcon = new ImageIcon(ImageIO.read( new File("assets/cat.png")));

        }
        catch(IOException ex) {
            System.out.println("dupa");

        }


        for(int i = 0; i < plansza.getM(); i++) {
            for(int j = 0 ; j < plansza.getN(); j++) {
                    Field field = new Field(i,j);
                    field.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            Field field = (Field)actionEvent.getSource();

                            if(plansza.getCell(field.getM(), field.getN())) {
                                field.setIcon(bombIcon);
                            }
                            else {
                                field.setEnabled(false);

                            }
                        }
                    });
                    frame.add(field);

            }
        }


        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    }

}