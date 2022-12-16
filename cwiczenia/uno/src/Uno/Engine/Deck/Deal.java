package Uno.Engine.Deck;

import Uno.Engine.Card.Card;
import Uno.Engine.Pile.Pile;

public class Deal {
    private final static int CARDS_DEALT = 7;
    private Card[][] deals;
    private Pile remainder;


    public Deal(Deck deck, int playerCount) {
        deals = new Card[playerCount][CARDS_DEALT];
        Deck d = (Deck) deck.clone();

        for(int i = 0; i < deals.length; i++) {
            for(int j = 0; j < CARDS_DEALT; j++) {
                deals[i][j] = deck.draw();
            }
        }
        this.remainder = new Pile(d);
    }

    public Card[][] getDeals() {
        return deals;
    }

    public Pile getRemainder() {
        return remainder;
    }
}
