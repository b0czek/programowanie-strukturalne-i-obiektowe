package Uno.Engine.Deck;

import Uno.Engine.Card.Action;
import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.Card.Value;
import Uno.Engine.Pile.Pile;

import java.util.Random;

public class Deal {
    private final static int CARDS_DEALT = 7;
    private final Card[][] deals;
    private final Pile remainder;


    public Deal(Deck deck, int playerCount) {
        deals = new Card[playerCount][CARDS_DEALT];
        Deck d = (Deck) deck.clone();

        for(int i = 0; i < deals.length; i++) {
            for(int j = 0; j < CARDS_DEALT; j++) {
                deals[i][j] = deck.draw();
            }
        }

        // prevent from draw 4 wildcard being at the top of the remainder
        // swap it with random card in the deck
        while(d.get(d.size() -1).getAction() == Action.WILD_DRAW_4) {
            int randomCardIdx = new Random().nextInt(d.size());
            Card temp = d.get(d.size() - 1);
            d.set(d.size() - 1, d.get(randomCardIdx));
            d.set(randomCardIdx, temp);
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
