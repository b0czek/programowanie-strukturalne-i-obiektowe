package Uno.Engine.Pile;

import Uno.Engine.Card.Card;
import Uno.Engine.Deck.Deck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Pile extends ArrayList<Card> {

    public Pile() {
        super();
    }

    public Pile(Card card) { super(); this.add(card); }

    public Pile(Collection<Card> d) {
        super();
        this.addAll(d);
    }

    public void shuffle() {
        Collections.shuffle(this);
    }
    public Card draw() {
        return this.remove(this.size() - 1);
    }

}
