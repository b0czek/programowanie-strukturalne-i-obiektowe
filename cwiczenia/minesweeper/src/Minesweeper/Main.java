package Minesweeper;

import Minesweeper.GUI.GUI;
import Minesweeper.plansza.Plansza;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Plansza plansza = new Plansza();

        try {
            SwingUtilities.invokeAndWait(() -> new GUI(plansza));
        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Could not start gui - " + ex.getMessage());
        }
    }
}