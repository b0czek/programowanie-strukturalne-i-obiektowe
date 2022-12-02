package GUI.Panels;
import java.util.Date;
import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;
import javax.swing.*;
public class GameTimer {
    Timer timer = new Timer();
    int t=0;
    public void czas() {

        TimerTask action = new TimerTask()
        {
            public void run(){
                t++;
                System.out.println(t);
            }
        };
        this.timer.scheduleAtFixedRate(action, 1000, 1000);

    }


}