package Uno.GUI.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HostDialog extends JDialog {
    private JTextField usernameField;
    private String username = null;
    private JOptionPane optionPane;

    private String[] options = new String[]{ "Create game", "Cancel" };

    public HostDialog(Frame frame) {
        super(frame, "Host", true);


        usernameField = new JTextField();

        optionPane = new JOptionPane(new Object[]{"Select your username", usernameField},
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        this.setContentPane(optionPane);
        this.setSize(300,150);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                usernameField = null;
                HostDialog.this.dispose();
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                usernameField.requestFocusInWindow();
            }
        });

        optionPane.addPropertyChangeListener((e) -> {
            if(!e.getPropertyName().equals("value")){
                return;
            }
            if(options[0].equals(optionPane.getValue())) {
                username = null;
                int length = usernameField.getText().length();

                if(length > 3 && length < 20) {
                    username = usernameField.getText();
                }
                if(username == null) {
                    usernameField.selectAll();
                    JOptionPane.showMessageDialog(this, "Username you entered is either too long or too short", "Invalid username", JOptionPane.ERROR_MESSAGE);
                    usernameField.requestFocusInWindow();
                }
                else {
                    // valid
                    this.dispose();
                }
            }
            else {
                // cancelled
                this.dispose();
            }
        });

    }

    public String getUsername() {
        return username;
    }
}
