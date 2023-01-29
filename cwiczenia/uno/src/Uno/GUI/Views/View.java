package Uno.GUI.Views;

import javax.swing.*;
import java.util.function.Consumer;

public class View extends JPanel {

    protected Consumer<String> viewSwitcher;

    public View(Consumer<String> viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    protected void switchView(String viewName) {
        viewSwitcher.accept(viewName);
    }

    protected JFrame getJFrame() {
        return (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
    }



}
