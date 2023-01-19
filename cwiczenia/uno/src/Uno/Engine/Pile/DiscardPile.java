package Uno.Engine.Pile;

import Uno.Engine.Card.Action;
import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.Card.Value;
import Uno.Engine.Round.RoundEventNotifier;

import java.util.List;

public class DiscardPile extends Pile {

    private Color previousColor;
    private Color currentColor;
    private RoundEventNotifier notifier = null;

    public DiscardPile() {

        super();
        currentColor = Color.NONE;
        previousColor = currentColor;
    }

    public void place(Card card) {
        if(!this.canUseCard(card)) {
            throw new IllegalArgumentException("Card cannot be played");
        }
        if(!card.isWildCard()) {
            this.setCurrentColor(card.getColor());
        }

        super.add(card);

        if(notifier != null) {
            notifier.notifyCardPlaced(card);
        }
    }



    public Color getCurrentColor() {
        return currentColor;
    }
    public void setCurrentColor(Color color) {
        previousColor = currentColor;
        this.currentColor = color;

        if(notifier != null) {
            notifier.notifyCurrentColorChange(currentColor);
        }
    }

    public void setNotifier(RoundEventNotifier notifier) {
        this.notifier = notifier;
    }

    public boolean canUseCard(Card card) {
        return  this.getCurrentColor() == Color.NONE || // happens only if game starts with wildcard
                card.isWildCard() ||
                (card.getValue() != Value.NONE && card.getValue() == getLastCard().getValue()) ||
                (card.getAction() != Action.NONE && card.getAction() == getLastCard().getAction()) ||
                card.getColor() == this.getCurrentColor();
    }

    // wild draw 4 legality check
    public boolean hadCardOfMatchingColor(Pile pile) {
        return pile.stream().anyMatch(card -> card.getAction() == Action.NONE && card.getColor() == previousColor);
    }

    public boolean canUseAnyCard(Pile pile ) {
        return pile.stream().anyMatch(this::canUseCard);
    }


    public List<Card> clearPile() {
        List<Card> subList = this.subList(0, this.size() - 2);

        Card lastCard = this.getLastCard();
        this.clear();
        this.add(lastCard);

        this.notifier.notifyDiscardPileCleared();

        return subList;
    }

}
