package GUI.Views;

import GUI.Panels.Board;
import plansza.Plansza;

import javax.swing.*;
import java.awt.*;

public class GameView extends View {
    public GameView(Plansza plansza) {
        super();
        this.add(new Board(plansza));
    }
}
