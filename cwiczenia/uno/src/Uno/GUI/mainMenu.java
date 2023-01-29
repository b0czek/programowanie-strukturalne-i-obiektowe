package Uno.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Uno.Engine.Card.Card;
import Uno.Engine.Card.Value;
import Uno.Engine.Game;
import Uno.Engine.Pile.Pile;
import Uno.Engine.Player.Player;



public class mainMenu extends JFrame {

    // lista graczy, przysisk ready
    private GridBagConstraints c;
    private JPanel main;
    private JPanel hidden;
    private JPanel userPanel;
    private JPanel radioPanel;
    private JPanel computerPanel;
    private ArrayList<JButton> cardButtons;
    private JButton drawButton;
    private JButton user1Card;
    private JButton user2Card;
    private JButton user3Card;
    private JButton user4Card;
    private JButton user5Card;
    private JButton user6Card;
    private JButton user7Card;
    private JButton user8Card;
    private JLabel[] lNames;
    private JLabel nameLabel;
    private JButton [] comCards;
    private JRadioButton [] wildColors;
    private ButtonGroup buttonGroup;
    private JLabel gameStatus;
    private Game game;
    private Card lastCardPlayed;
    private String usersName;
    private Pile usersHand;
    private ArrayList<Card> usersCards;
    private boolean userActed;
    private JCheckBox usersTurn;
    private JCheckBox over;
    private String winner;
    private Card userPlayed;

    private Timer timer;


    public mainMenu()
    {
        setTitle("Uno");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane (main);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        initializeHiddenPanel();
        initializeUserPanel();
        initializeRadioPanel ();
        c = new GridBagConstraints();
        initializeComputerPanel();

        gameStatus = new JLabel ("current color: none; current value: none.");
        c.insets = new Insets(20, 0, 0, 20);
        c.weightx = 0.15;
        computerPanel.add(gameStatus);
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        computerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(hidden);
        main.add(userPanel);
        main.add(radioPanel);
        main.add(computerPanel);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        pack();
        setVisible(true);

    }

    void initializeHiddenPanel () {
        // the hidden panel is used to hold two checkboxes use for listening to item changes
        hidden = new JPanel();
        // check box for the whether it's the user's turn to play
        userActed = false;
        usersTurn = new JCheckBox("user's turn");
        usersTurn.setSelected(true);

        usersTurn.setVisible(false);
        hidden.add(usersTurn);
        // check box for whether the game is over
        over = new JCheckBox("game over");
        over.setSelected(false);

        over.setVisible(false);
        hidden.add(over);
    }
    void initializeUserPanel () {
        // userPanel has the user's label and card buttons
        userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        usersName = "";
     //   usersName = JOptionPane.showInputDialog(this, "Enter your name and start the game.");
      //  if (usersName == null) System.exit(0);
      //  if (usersName.equals("")) usersName = "User";
        nameLabel = new JLabel( "Use has 7 cards");
        nameLabel.setSize(150, 100);
        userPanel.add(nameLabel);

        // add a button for drawing; button is always disabled unless no legal cards exist
        drawButton = new JButton("Draw");
        drawButton.setEnabled(false);


        userPanel.add(drawButton);
        usersCards = new ArrayList<>();
        Player PlayerInfo;
        usersCards.add(new Card(Uno.Engine.Card.Color.YELLOW, Value.ONE));
        usersCards.add(new Card(Uno.Engine.Card.Color.RED, Value.TWO));
        usersCards.add(new Card(Uno.Engine.Card.Color.YELLOW, Value.ONE));
        cardButtons = new ArrayList<JButton>(usersCards.size());
        // add cards to the GUI and add actionListener to the cards
        for (int i = 0; i < usersCards.size(); i++) {
            JButton b = new JButton();
            b.setOpaque(true);
            b.setBorderPainted(false);


            userPanel.add(b);
            cardButtons.add(i, b);
        }
    }
        void initializeRadioPanel () {
            // this panel is for JRadioButtons for the user to choose a color for their wild cards
            radioPanel = new JPanel();
            radioPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel yourWildCard = new JLabel("Your wild card color:");
            radioPanel.add(yourWildCard);
            wildColors = new JRadioButton[8];
            buttonGroup = new ButtonGroup();
            String[] wildLabels = {"Red", "Blue", "Yellow", "Green"};
            for (int i = 0; i < 4; i++) {
                wildColors[i] = new JRadioButton(wildLabels[i]);
                wildColors[i].setActionCommand(wildLabels[i].substring(0, 1));
                buttonGroup.add(wildColors[i]);
                radioPanel.add(wildColors[i]);
            }
            wildColors[0].setSelected(true);
        }

    void initializeComputerPanel () {
        // this panel is for the labels and cards of the computers
        computerPanel = new JPanel();
        computerPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor=GridBagConstraints.WEST;
        c.weightx = 0.10;
        c.weighty = 0.0;
        c.insets = new Insets(40, 0, 0, 40);

        lNames = new JLabel[8];
        lNames[0] = nameLabel;
        JLabel user1 = new JLabel ("User1 has 7 cards");
        user1.setSize(150,100);
        computerPanel.add(user1);

        lNames[1] = nameLabel;
        JLabel user2 = new JLabel ("User2 has 7 cards");
        user2.setSize(150,100);
        computerPanel.add(user2);

        lNames[2] = nameLabel;
        JLabel user3 = new JLabel("User3 has 7 cards");
        user3.setSize(150,100);
        computerPanel.add(user3);

        lNames[3] = nameLabel;
        JLabel user4 = new JLabel("User4 has 7 cards");
        user4.setSize(150,100);
        computerPanel.add(user4);
        lNames[4] = nameLabel;
        JLabel user5 = new JLabel("User5 has 7 cards");
        user5.setSize(150,100);
        computerPanel.add(user5);
        lNames[5] = nameLabel;
        JLabel user6 = new JLabel("User6 has 7 cards");
        user6.setSize(150,100);
        computerPanel.add(user6);
        lNames[6] = nameLabel;
        JLabel user7 = new JLabel("User7 has 7 cards");
        user7.setSize(150,100);
        computerPanel.add(user7);
        lNames[7] = nameLabel;
        JLabel user8 = new JLabel("User8 has 7 cards");
        user8.setSize(150,100);
        computerPanel.add(user8);
        // the cards that the computer players play are disabled JButtons
        user1Card = new JButton();
        user1Card.setOpaque(true);
        user1Card.setBorderPainted(false);

        user2Card = new JButton();
        user2Card.setOpaque(true);
        user2Card.setBorderPainted(false);

        user3Card = new JButton();
        user3Card.setOpaque(true);
        user3Card.setBorderPainted(false);

        user4Card = new JButton();
        user4Card.setOpaque(true);
        user4Card.setBorderPainted(false);

        user5Card = new JButton();
        user5Card.setOpaque(true);
        user5Card.setBorderPainted(false);

        user6Card = new JButton();
        user6Card.setOpaque(true);
        user6Card.setBorderPainted(false);

        user7Card = new JButton();
        user7Card.setOpaque(true);
        user7Card.setBorderPainted(false);

        user8Card = new JButton();
        user8Card.setOpaque(true);
        user8Card.setBorderPainted(false);

        comCards = new JButton[9];
        comCards[0] = new JButton();
        comCards[1] = user1Card;
        comCards[2] = user2Card;
        comCards[3] = user3Card;
        comCards[4] = user4Card;
        comCards[5] = user5Card;
        comCards[6] = user6Card;
        comCards[7] = user7Card;
        comCards[8] = user8Card;
    }
    public String getusersName () {
        return usersName;
    }


    /**
     * Return a Hand object.
     **/
    public Pile getUsersHand() {
        return usersHand;
    }
    public ArrayList<Card> getUsersCards() {
        return usersCards;
    }


    /** Return the game object.
     **/
    public Game getGame() {
        return game;
    }


    /** Return the winner field.
     **/
    public String getWinner() {
        return winner;
    }

    /** Set the winner field.
     **/
    public void setWinner(String winner) {
        this.winner = winner;
    }


    /** Return an array of JLabels representing all the names of the players.
     **/
    public JLabel [] getLNames() {
        return lNames;
    }


    /** Get the buttons representing the cards of the users.
     **/
    public ArrayList<JButton> getCardButtons () {
        return cardButtons;
    }

    /** Add a button to the ArrayList of buttons representing the user's cards.
     **/
    public void addCardButtons(JButton b) {
        this.cardButtons.add(b);
    }


    /** Select or de-select the usersTurn checkbox.
     **/
    public void setUsersTurn (boolean usersTurn) {
        this.usersTurn.setSelected(usersTurn);
    }


    /** Set the lastCardPlayed field.
     **/
    public void setLastCardPlayed (Card lastCardPlayed) {
        this.lastCardPlayed = lastCardPlayed;
    }


    /** Set the gameStatus label.
     *
     * @param color     the current color of the game
     * @param value     the current value of the game
     *
     **/
    public void setGameStatus (String color, String value) {
        String s = "current color: " + color + "; current value: " + value;
        gameStatus.setText(s);
    }


    /** Select or de-select the game over checkbox.
     **/
    public void setOver (boolean over) {
        this.over.setSelected(over);
    }


    /** Set the text of the labels indicating which player has how many cards.
     **/
    public void setLabelText (JLabel l, String name, int number) {
        if (number != 1) {
            l.setText (name + " has " + number + " cards");
        } else {
            l.setText (name + " has 1 card");
        }
    }


    /** A helper method to revalidate and repaint all the panels with the card buttons.
     **/
    public void resetPanel () {
        computerPanel.revalidate();
        computerPanel.repaint();
        userPanel.revalidate();
        userPanel.repaint();
        main.revalidate();
        main.repaint();
    }


    /** Change the game status based on the card played by the user.
     *
     * @param       index, index of the card played in the hand
     **/
    public void playedCard(int index) {
        userPanel.remove(cardButtons.get(index));
        resetPanel();
        cardButtons.remove(index);
        userPlayed = usersHand.remove(index);


        // if the user has no cards left they win
        if (usersHand.size() == 0 || usersHand.size() <= 0) {
            setWinner("You");
            setOver(true);
        }
    }

        // if the user plays a wild card, set the current color according to the radio button selected



    /** Reset the card displayed next to a computer player label, representing the last action they took.
     *
     * @param       t, the turn indicating which computer played
     **/

    /** Invoke actions of the computer players after the user has acted. This will keep going until it's the user's
     *  turn again.
     *
     * @param       index, index of the card played in the user's hand
     **/

        // determine which computer to play next based on the direction and current skip of the game


        // use a timer to create a delay between each player; this also serves as a while loop;
        // it will make each computer play a turn until it's the user's turn again, or until someone wins




                    /**
                     * Enable all the buttons corresponding to the cards legal to play in the user's hand.
                     **/
                    public void resetButtons() {
                        for (int i = 0; i < usersCards.size(); i++) {
                            cardButtons.get(i).setEnabled(false);
                        }
                        drawButton.setEnabled(false);
                    }


                    /** Enable all the buttons corresponding to the cards legal to play in the user's hand.
                     *
                     **/

                    /**
                     * Add component to the computerPanel.
                     *
                     * @param comp
                     * @param row    row index of the gridbag layout
                     * @param column column index of the layout
                     * @param width
                     * @param height
                     **/
                    public void addToComputerPanel(Component comp, int row, int column, int width, int height) {
                        // set gridx and gridy
                        c.gridx = column;
                        c.gridy = row;

                        // set gridwidth and gridheight
                        c.gridwidth = width;
                        c.gridheight = height;

                        // add component
                        computerPanel.add(comp, c);
                    }


                    /**
                     * Add component to the userPanel.
                     *
                     * @param comp
                     **/
                    public void addToUserPanel(Component comp) {
                        userPanel.add(comp);
                    }


                    /**
                     * Set the background color of a JButton based on the color of the corresponding card.
                     *
                     * @param c, String indicating the color of the card
                     * @param b, the button
                     **/
                    public void setButtonColor(String c, JButton b) {
                        if (c.equals("R")) {
                            b.setBackground(new Color(242, 95, 92));
                        } else if (c.equals("B")) {
                            b.setBackground(new Color(36, 123, 160));
                        } else if (c.equals("Y")) {
                            b.setBackground(new Color(255, 224, 102));
                        } else if (c.equals("G")) {
                            b.setBackground(new Color(136, 212, 152));
                        } else if (c.equals("W")) {
                            b.setBackground(Color.LIGHT_GRAY);
                        } else if (c.equals("")) {
                            b.setBackground(Color.WHITE);
                        }
                    }


                    /**
                     * Set the size, margin and font of a button.
                     *
                     * @param b, the button
                     **/
                    public void setButtonFormat(JButton b) {
                        b.setMargin(new Insets(1, 1, 1, 1));
                        b.setMinimumSize(new Dimension(80, 100));
                        b.setMaximumSize(new Dimension(80, 100));
                        b.setPreferredSize(new Dimension(80, 100));
                        b.setFont(new Font("Tahoma", Font.BOLD, 10));
                    }


                    /**
                     * Set the size of a label.
                     *
                     * @param l,     a JLabel object
                     * @param width
                     * @param height
                     **/
                    public void setLabelFormat(JLabel l, int width, int height) {
                        l.setMinimumSize(new Dimension(width, height));
                        l.setMaximumSize(new Dimension(width, height));
                        l.setPreferredSize(new Dimension(width, height));
                    }


                    /**
                     * Helper method to generate integers betwee a range, inclusive,
                     *
                     * @param a, the lower bound
                     * @param b, the upper bound
                     * @return a random integer
                     **/
                    public static int rint(int a, int b) {
                        return ((int) (Math.random() * (b - a + 1))) + a;
                    }


                    /**
                     * ItemListener to monitor the change of the user's turn checkbox.
                     **/


                    public static void main(String[] arg) {
                        mainMenu X = new mainMenu();
                    }
                }