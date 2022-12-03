package Minesweeper.GUI.Panels.TopBar;

import Minesweeper.plansza.Plansza;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends JLabel {
    Timer timer;
    int t;
    public GameTimer(Plansza plansza) {
        super();
        t = 0;
        timer = new Timer();
        TimerTask action = new TimerTask() {
            @Override
            public void run() {
                t++;
                if(plansza.getRevealedFieldsCount() == 0) {
                    reset();
                }
                else if(!plansza.isGameFinished()) {
                    setText(String.format("%02d:%02d", t/60, t%60));
                }
            }
        };


        timer.scheduleAtFixedRate(action, 0, 1000);
    }


    public void reset() {
        t = 0;
        this.setText("00:00");
    }


}