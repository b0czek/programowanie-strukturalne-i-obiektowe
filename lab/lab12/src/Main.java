import KonwerterWalut.Waluta;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            SwingUtilities.invokeAndWait(() -> new Waluta());
        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Could not start gui - " + ex.getMessage());
        }


    }
}