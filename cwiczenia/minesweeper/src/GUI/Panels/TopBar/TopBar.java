package GUI.Panels.TopBar;

import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {
    public static final int TOPBAR_HEIGHT = 200;
    public TopBar() {
        super();
        this.setBackground(new Color(20,200,20));
        JButton back = new JButton("<-");
//        back.addActionListener(actionEvent -> callback.onMenuReturn());
        this.add(back);
    }
}
