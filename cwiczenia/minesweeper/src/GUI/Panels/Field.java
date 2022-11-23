package GUI.Panels;

import plansza.Pole;
import plansza.Stan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Field extends JButton {
    private int m,n;
    private Pole f;
    private String iconName;
    public Field(int m, int n, Pole f) {
        super();
        this.m = m;
        this.n = n;
        this.f = f;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Stan state = f.getState();
                System.out.println(state.name());
                if(state == Stan.Odkryta) {
                    return;
                }

                if(SwingUtilities.isRightMouseButton(e)) {
                    toggleFlag();
                }
                else if(state == Stan.Zakryta){
                    f.setState(Stan.Odkryta);
                    setEnabled(false);
                    if(f.getBomb()) {
                        setIcon(FieldIcons.Bomb);
                    }
                }
            }
        });


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(iconName != null) {
                    setIcon(iconName);
                }
            }
        });

    }

    public int getM() {
        return this.m;
    }

    public int getN() {
        return this.n;
    }

    public void setIcon(FieldIcons fieldIcon) {
        this.setIcon(fieldIcon.name());
    }

    public void setIcon(String iconName) {
        this.iconName = iconName;
        ImageIcon icon = FieldIconStore.getIcon(iconName).getScaledIcon();
        super.setIcon(icon);
        super.setDisabledIcon(icon);

    }
    public void removeIcon() {
        this.iconName = null;
        super.setIcon(null);
    }

    public void toggleFlag() {
        if (this.f.getState() == Stan.Zflagowana) {
            this.removeIcon();
            this.f.setState(Stan.Zakryta);
        } else {
            this.setIcon(FieldIcons.Flag);
            this.f.setState(Stan.Zflagowana);

        }
    }
}
