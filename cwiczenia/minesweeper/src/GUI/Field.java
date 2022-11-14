package GUI;

import javax.swing.*;

public class Field extends JButton {
    private int m,n;
    public Field(int m, int n) {
        super();
        this.m = m;
        this.n = n;
    }

    public int getM() {
        return this.m;
    }

    public int getN() {
        return this.n;
    }

}
