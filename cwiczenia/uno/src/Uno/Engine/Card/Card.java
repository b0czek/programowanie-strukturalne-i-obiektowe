package Uno.Engine.Card;

public class Card {

    private final Color color;
    private final Value value;
    private final Action action;


    public Card(Color color, Value value, Action action) {
        this.color = color;
        this.value = value;
        this.action = action;
        }

    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
        this.action = Action.NONE;
    }

    public Color getColor() {
        return color;
    }

    public Value getValue() {
        return value;
    }

    public Action getAction() {
        return action;
    }

    public boolean isWildCard() {
        return this.action == Action.WILD_CARD || this.action == Action.WILD_DRAW_4;
    }

    public boolean isActionCard() {
        return this.action != Action.NONE;
    }

    public boolean isNumberCard() {
        return this.value != Value.NONE;
    }


    public int getPoints() {
        switch(this.action) {
            case NONE:
                return this.value.ordinal();
            case DRAW_TWO:
            case REVERSE:
            case SKIP:
                return 20;
            case WILD_CARD:
            case WILD_DRAW_4:
                return 50;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("[Card: color: %s, value: %s, action: %s]",
                this.color.toString(),
                this.value.toString(),
                this.action.toString()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Card c)) {
            return false;
        }
        return c.getAction() == this.action && c.getColor() == this.color && c.getValue() == this.value;
    }

}
