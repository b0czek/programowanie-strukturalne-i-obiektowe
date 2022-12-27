package Uno.Engine.Pile;

import Uno.Engine.Card.Card;
import Uno.Engine.Round.RoundEventNotifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DrawPile extends Pile {
    private final DiscardPile discardPile;
    private RoundEventNotifier notifier = null;

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
        Card c = super.draw();

        if(notifier != null ) {
            notifier.notifyCardDrew();
        }

        return c;

    }

    public void setNotifier(RoundEventNotifier notifier) {
        this.notifier = notifier;
    }
}
