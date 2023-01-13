package KonwerterWalut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Double.parseDouble;

public class Waluta {
    private JFrame frame;
    private JTextField euroField;
    private JTextField plnField;

    public Waluta() {
        this.frame = new JFrame("Konwerter walut");

        Container content = frame.getContentPane();
        content.setLayout(new GridLayout(3, 2));

        content.add(new JLabel("Złoty"));

        plnField = new JTextField();
        content.add(plnField);
        plnField.addActionListener(new TextFieldActionListener());

        content.add(new JLabel("Euro"));

        euroField = new JTextField();
        content.add(euroField);
        euroField.addActionListener(new TextFieldActionListener());


        JButton button = new JButton("Wyczyść pola");
        content.add(button);
        button.addActionListener(new ButtonActionListener());

        frame.setSize(350,150);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private class TextFieldActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String valueString = ((JTextField)actionEvent.getSource()).getText();
            double value;
            try {
                value = Double.parseDouble(valueString);
            }
            catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Nieprawidłowy format liczby", "",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(actionEvent.getSource() == euroField) {
                plnField.setText(String.format("%.2f",  Konwerter.EuroToPLN(value)));
            }
            else if(actionEvent.getSource() == plnField) {
                euroField.setText(String.format("%.2f",  Konwerter.PLNToEuro(value)));
            }
        }
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            euroField.setText("");
            plnField.setText("");
        }
    }

}
