package Uno.GUI.Views;

import Uno.Engine.Card.Action;
import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.Card.Value;
import Uno.Engine.GameDirection;
import Uno.Engine.Pile.Pile;
import Uno.Engine.Player.PlayerInfo;
import Uno.GUI.GamePanels.ChallengePanel;
import Uno.GUI.GamePanels.ColorPane;
import Uno.GUI.GamePanels.PanelsOnCircle;
import Uno.GUI.GamePanels.PlayerPanel;
import Uno.GUI.Providers.CardImageProvider;
import Uno.GUI.Providers.ClientProvider;
import Uno.Network.Client.GameClient;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.ClientRequest.RequestType;
import Uno.Network.Server.Message.MessageType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;

public class Game extends View {

    private JLayeredPane cardPane;
    private JPanel bottomPane;
    private PanelsOnCircle playersPanel;
    private JPanel centerPane;
    private JButton drawButton;
    private JButton passButton;
    private JButton unoButton;
    private JButton callOutButton;
    private JPanel sidePanel = null;

    private JLabel directionLabel;
    private JLabel colorLabel;

    private String currentPlayer ="";
    private PlayerInfo[] playerInfos;
    public static int CARD_PANE_WIDTH = 800;

    private JButton lastCard;

    public Game(Consumer<String> viewSwitcher) {
        super(viewSwitcher);
        CardImageProvider.init();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                getJFrame().setSize(1200, 800);

            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

//        centerPane = new JLayeredPane();
        centerPane = new JPanel();
        centerPane.setPreferredSize(new Dimension(300, 400));
        playersPanel = new PanelsOnCircle(new JPanel[]{

        }, 100 ,100);
        centerPane.add(playersPanel);

        this.add(centerPane);

        bottomPane = new JPanel();
        bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.X_AXIS));

        cardPane = new JLayeredPane();
        cardPane.setPreferredSize(new Dimension(CARD_PANE_WIDTH, 200));
        bottomPane.add(cardPane);

        lastCard = createCardButton(new Card(Color.NONE, Value.NONE, Action.NONE));
        centerPane.add(lastCard);

        this.add(bottomPane);

        JPanel controlsPane = new JPanel();
        controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.X_AXIS));

        passButton = new JButton("PASS TURN");
        passButton.addActionListener(new SimpleRequestHandler(RequestType.PASS_TURN));
        controlsPane.add(passButton);

        passButton = new JButton("DRAW A CARD");
        passButton.addActionListener(new SimpleRequestHandler(RequestType.DRAW_CARD));
        controlsPane.add(passButton);

        unoButton = new JButton("UNO!");
        unoButton.addActionListener(new SimpleRequestHandler(RequestType.YELL_UNO));
        controlsPane.add(unoButton);

        callOutButton = new JButton("CALL OUT");
        callOutButton.addActionListener(new SimpleRequestHandler(RequestType.CATCH_NOT_YELLED_UNO));
        controlsPane.add(callOutButton);

        colorLabel = new JLabel();
        controlsPane.add(colorLabel);

        controlsPane.add(Box.createRigidArea(new Dimension(20, 0)));

        directionLabel = new JLabel();
        controlsPane.add(directionLabel);

        this.add(controlsPane);

        addMessageHandlers();
    }

    public void addMessageHandlers() {
        GameClient client = ClientProvider.getGameClient();
        client.addMessageHandler(MessageType.HAND, hand -> {
            Pile cards  =  (Pile) hand;
            SwingUtilities.invokeLater(() -> {
                cardPane.removeAll();

                for(int i = 0 ; i < cards.size(); i++) {
                    Card card = cards.get(i);

                    JButton button = createCardButton(card);
                    button.addActionListener(new CardClickHandler(card));

                    int w = CARD_PANE_WIDTH - CardImageProvider.cardWidth;
                    button.setBounds((w / cards.size()) * i, 0, CardImageProvider.cardWidth, CardImageProvider.cardHeight);
                    cardPane.add(button, new Integer(i));
                }
                cardPane.revalidate();
                cardPane.repaint();
            });
        });

        client.addMessageHandler(MessageType.DISCARD_PILE, pile -> {
            Pile discardPile = (Pile) pile;
            SwingUtilities.invokeLater(() -> {
                updateLastCard(discardPile.getLastCard());
            });
        });
        client.addMessageHandler(MessageType.PLAYERS_DATA, players -> {
            playerInfos = (PlayerInfo[]) players;
            SwingUtilities.invokeLater(() -> {

                updatePlayers();
            });

        });

        client.addMessageHandler(MessageType.CARD_PLACED, c -> {
            Card card = (Card) c;
            SwingUtilities.invokeLater(() -> {

                updateLastCard(card);
            });
        });
        client.addMessageHandler(MessageType.CURRENT_TURN_PLAYER, (player) -> {
            PlayerInfo playerInfo = (PlayerInfo) player;
            currentPlayer = playerInfo.getName();
            SwingUtilities.invokeLater(() -> {

                updatePlayers();
            });
        });

        client.addMessageHandler(MessageType.PLAYER_DATA, o -> {
            PlayerInfo playerInfo = (PlayerInfo) o;
            for(int i = 0; i < playerInfos.length; i++) {
                if(playerInfos[i].getName().equals(playerInfo.getName())) {
                    playerInfos[i]= playerInfo;
                    break;
                }
            }
            SwingUtilities.invokeLater(() -> {
                updatePlayers();

            });
        });

        client.addMessageHandler(MessageType.CURRENT_COLOR, (o) -> {
            Color color = (Color) o;
            SwingUtilities.invokeLater(() -> {

                colorLabel.setText(color.toString());
            });
        });


        client.addMessageHandler(MessageType.GAME_DIRECTION, (o) -> {
            GameDirection direction = (GameDirection) o;
            SwingUtilities.invokeLater(() -> {

                directionLabel.setText(direction.toString());
            });
        });

    }


    private JButton createCardButton(Card card) {
        JButton button = new JButton();
        button.setMargin(new Insets(0,0,0,0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setIcon(new ImageIcon(CardImageProvider.get(card)));
        return button;
    }

    private class CardClickHandler implements ActionListener {
        private Card card;

        public CardClickHandler(Card card) {
            this.card = card;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(!isPlayersTurn()) {
                return;
            }
            if(card.isWildCard()) {
                if(sidePanel != null) {
                    bottomPane.remove(sidePanel);
                    sidePanel = null;
                }
                sidePanel = new ColorPane((color) -> {
                    card.getAction().setChangeColorTo(color);
                    requestCardPlayed();
                });
                bottomPane.add(sidePanel);
            }
            else {
                requestCardPlayed();
            }

        }
        private void requestCardPlayed() {
            try {
                ClientProvider.getGameClient().sendRequest(new ClientRequest(RequestType.PLAY_CARD, card), (response) -> {
                    System.out.println(response);
                });
            } catch (IOException e) {
                System.out.println("failed to send request");
            }
        }
    }


    private class SimpleRequestHandler implements ActionListener {
        private RequestType requestType;

        public SimpleRequestHandler(RequestType requestType) {
            this.requestType = requestType;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(!isPlayersTurn()) {
                return;
            }
            try {
                ClientProvider.getGameClient().sendRequest(new ClientRequest(requestType));
            }
            catch (IOException io) {
                System.out.println("failed to send request");

            }
        }
    }

    private boolean isPlayersTurn() {
//        return true;
        return currentPlayer.equals(ClientProvider.getGameClient().getUsername());
    }

    private void updateLastCard(Card card) {
        lastCard.setIcon(new ImageIcon(CardImageProvider.get(card)));
        if(isPlayersTurn()) {
            Optional<PlayerInfo> player = Arrays.stream(playerInfos).filter(playerInfo -> playerInfo.getName().equals(ClientProvider.getGameClient().getUsername())).findFirst();
            if(player.isEmpty()) {
                return;
            }
            if(!player.get().isCanChallenge()) {
                return;
            }
            if(sidePanel != null) {
                bottomPane.remove(sidePanel);
                sidePanel = null;
            }
            sidePanel = new ChallengePanel((shouldChallenge) -> {
                RequestType requestType = shouldChallenge ? RequestType.CHALLENGE_WILDCARD : RequestType.PASS_TURN;
                try {
                    ClientProvider.getGameClient().sendRequest(new ClientRequest(requestType));
                }
                catch (IOException io) {
                    System.out.println("failed to send request");

                }


                bottomPane.remove(sidePanel);
                sidePanel = null;
            });
            bottomPane.add(sidePanel);
        }

    }

    private void updatePlayers() {
        if(playerInfos == null) {
            return;
        }


        if(isPlayersTurn()) {
            cardPane.setBorder(new LineBorder(java.awt.Color.GREEN, 3));
        }
        else {
            cardPane.setBorder(null);
            if(sidePanel != null) {
                bottomPane.remove(sidePanel);
                sidePanel = null;
            }
        }


        if(playersPanel != null) {
            centerPane.remove(playersPanel);
        }
        PlayerPanel[] playerPanels = Arrays.stream(playerInfos)
                .filter(playerInfo -> !playerInfo.getName().equals(ClientProvider.getGameClient().getUsername()))
                .sorted(Comparator.comparingInt(PlayerInfo::getOrdinal))
                .map(playerInfo -> new PlayerPanel(playerInfo, playerInfo.getName().equals(currentPlayer)))
                .toArray(PlayerPanel[]::new);

        playersPanel = new PanelsOnCircle(playerPanels, 150 ,100);
        centerPane.add(playersPanel, new Integer(0));
    }

}
