package Uno.Engine.Player;

import Uno.Engine.Card.Card;
import Uno.Engine.Pile.Pile;

import java.util.Collection;

public class Player {
    private String name;
    private Pile hand;
    private boolean yelledUno = false;

    public Player(String name, Collection<Card> hand) {
        this.name = name;
        this.hand = new Pile(hand);
    }

    public String getName() {
        return name;
    }

    public Pile getHand() {
        return hand;
    }

    public boolean  removeCardFromHand(Card card) {
        return this.getHand().remove(card);
    }
    public void addCardToHand(Card card) {
        this.getHand().add(card);
    }



}
