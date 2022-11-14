import GUI.GUI;
import plansza.Plansza;
import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        Plansza plansza = new Plansza(10,10);
        plansza.init(10);
        plansza.Drukowanie();
        SwingUtilities.invokeAndWait(() -> new GUI(plansza));
    }
}