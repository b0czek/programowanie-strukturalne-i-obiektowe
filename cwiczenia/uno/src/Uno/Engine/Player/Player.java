package Uno.Engine.Player;

import Uno.Engine.Card.Card;
import Uno.Engine.Pile.Pile;
import Uno.Engine.Round.RoundEventNotifier;

import java.util.Collection;

public class Player {
    private final String name;
    private final Pile hand;
    private final int ordinal;
    private boolean yelledUno = false;
    private boolean challengeable = false;
    private boolean drewCard = false;

    public PlayerController controller;

    public Player(int ordinal, PlayerController controller, String name, Collection<Card> hand) {
        this.ordinal = ordinal;
        this.controller = controller;
        this.name = name;
        this.hand = new Pile(hand);
    }

    public String getName() {
        return name;
    }

    public Pile getHand() {
        return hand;
    }

    public static Card removeCardFromHand(Player player, Card card, RoundEventNotifier notifier) {

        notifier.notifyCardRemovedFromHand(player.getInfo());
        return player.getHand().remove(card) ? card : null;

    }
    public static void addCardToHand(Player player, Card card, RoundEventNotifier notifier) {
        player.getHand().add(card);
        notifier.notifyCardAddedToHand(player.getInfo());
    }

    public boolean didYellUno() {
        return yelledUno;
    }

    public void setYelledUno(boolean yelledUno) {
        this.yelledUno = yelledUno;
    }

    public boolean isChallengeable() {
        return challengeable;
    }

    public void setChallengeable(boolean challengeable) {
        this.challengeable = challengeable;
    }

    public boolean didDrawCard() {
        return drewCard;
    }

    public void setDrewCard(boolean drewCard) {
        this.drewCard = drewCard;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public PlayerInfo getInfo() {
        return new PlayerInfo(name, hand.size(), ordinal);
    }

}
