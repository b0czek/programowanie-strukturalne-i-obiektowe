package Uno.Engine.Pile;

import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DiscardPile extends Pile {

    private Color currentColor;

    public DiscardPile(Card startingCard) {

        super(startingCard);
        currentColor = startingCard.getColor();
    }

    public Card getLastCard() {
        return this.get(this.size() -1);
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public boolean canUseCard(Card card) {
        if(card.isWildCard()) {
            return true;
        }
        if(card.getValue() == this.get(this.size()-1).getValue() || card.getColor() == this.getCurrentColor()) {
            return true;
        }
        return false;
    }


    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public List<Card> clearPile() {
        List<Card> subList = this.subList(0, this.size() - 2);

        Card lastCard = this.getLastCard();
        this.clear();
        this.add(lastCard);

        return subList;
    }

}
