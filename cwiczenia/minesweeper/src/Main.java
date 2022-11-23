import GUI.GUI;
import plansza.Difficulty;
import plansza.Plansza;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Plansza plansza = new Plansza();
//        plansza.init(Difficulty.difficulties[0]);
        plansza.Drukowanie();

        try {
            SwingUtilities.invokeAndWait(() -> new GUI(plansza));
        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Could not start gui - " + ex.getMessage());
        }
    }
}