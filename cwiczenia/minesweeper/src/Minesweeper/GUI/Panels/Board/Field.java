package Minesweeper.GUI.Panels.Board;

import Minesweeper.plansza.Pole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Field extends JButton {
    public static final Color[] labelColors = {
            new Color(123,32,88),
            new Color(255,50,30),
            new Color(0,200,200),
            new Color(255,0,255),
            new Color(0,0,139),
            new Color(150,75,0),
            new Color(0,255,0),
            new Color(0,0,0),
            new Color(160,170,175)
    };
    private Pole f;
    private String iconName;
    public Field(Pole f, FieldActionCallback callback) {
        super();
        this.setFont(FieldFontProvider.getFont());
        this.setMargin(new Insets(0, 0, 0, 0));

        this.f = f;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(SwingUtilities.isRightMouseButton(e)) {
                    callback.fieldFlagToggled(fieldThis());
                }
                else {
                    callback.fieldRevealed(fieldThis());
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
                setFont(FieldFontProvider.getFont());
            }
        });

    }

    public int getM() {
        return this.f.getM();
    }

    public int getN() {
        return this.f.getN();
    }

    public Pole getPole() {
        return this.f;
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

    public void refreshField() {
        this.setEnabled(true);
        this.removeIcon();
        switch(this.f.getState()) {
            case Odkryta:
                this.setContentAreaFilled(false);
                if(this.f.getIsBomb()) {
                    this.setIcon(FieldIcons.Bomb);
                }
                else {
                    int nearBombs = f.getNearBombsCount();
                    if(nearBombs > 0) {
                        this.setText(String.format("%d", f.getNearBombsCount()));
                        this.setForeground(Field.labelColors[nearBombs]);
                    }

                }
                break;
            case Zflagowana:
                this.setIcon(FieldIcons.Flag);
                break;
        }
    }
    private Field fieldThis() {
        return this;
    }
}
