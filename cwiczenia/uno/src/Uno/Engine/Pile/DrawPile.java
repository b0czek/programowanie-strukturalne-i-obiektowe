package Uno.Engine.Pile;

import Uno.Engine.Card.Card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DrawPile extends Pile {
    private DiscardPile discardPile;
    public DrawPile(Collection<Card> d, DiscardPile discardPile) {
        super(d);
        this.discardPile = discardPile;
    }

    @Override
    public Card draw() {
        if(this.size() == 0) {
            List<Card> pile = discardPile.clearPile();
            Collections.shuffle(pile);
            this.addAll(pile);
        }

        return super.draw();

    }
}
