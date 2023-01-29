package Uno.GUI.Dialogs;

import Uno.Network.Client.GameDiscovery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;

public class JoinDialog extends JDialog {
    private GameDiscovery gameDiscovery;

    private JList discoveredServers;
    private DefaultListModel listModel;


    private JTextField addressField;
    private String address;
    private JTextField usernameField;
    private String username = null;
    private JOptionPane optionPane;

    private String[] options = new String[]{ "Join game", "Cancel" };

    public JoinDialog(Frame frame) {
        super(frame, "Join", true);


        listModel = new DefaultListModel();
        discoveredServers = new JList(listModel);
        discoveredServers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        discoveredServers.setVisibleRowCount(5);
        discoveredServers.addListSelectionListener(listSelectionEvent -> {
            String val = (String)discoveredServers.getSelectedValue();
            if(val != null) {
                addressField.setText(val.split("@")[1]);
            }
        });
        JScrollPane listScrollPane = new JScrollPane(discoveredServers);

        try {
            gameDiscovery = new GameDiscovery(servers -> {
                discoveredServers.clearSelection();
                listModel.removeAllElements();
                servers.forEach(server -> listModel.addElement(server.getHostname() + "@" + server.getAddress() + ":" + server.getPort()));
            });

        } catch (SocketException e) {
            System.out.println("failed to start game discovery: "+ e.getMessage());
        }

        usernameField = new JTextField();
        addressField = new JTextField();

        optionPane = new JOptionPane(new Object[]{"Servers discovered in local network", listScrollPane, "Server's address",addressField, "Select your username", usernameField},
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        this.setContentPane(optionPane);
        this.setSize(300,250);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                usernameField = null;
                JoinDialog.this.dispose();
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                addressField.requestFocusInWindow();
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

                address = null;
                if(addressField.getText().length() > 0) {
                    address = addressField.getText();
                }

                if(username == null) {
                    usernameField.selectAll();
                    JOptionPane.showMessageDialog(this, "Username you entered is either too long or too short", "Invalid username", JOptionPane.ERROR_MESSAGE);
                    usernameField.requestFocusInWindow();
                }
                else if(address == null) {
                    addressField.selectAll();
                    JOptionPane.showMessageDialog(this, "You must enter address of a server", "Invalid server address", JOptionPane.ERROR_MESSAGE);
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
    public String getAddress() {
        return address;
    }

    @Override
    public void dispose() {
        gameDiscovery.close();
        super.dispose();

    }
}
