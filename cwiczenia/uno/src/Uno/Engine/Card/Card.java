package Uno.Engine.Card;

public class Card {

    private Color color;
    private Value value;
    private Action action;


    private Card() {}


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

    @Override
    public String toString() {
        return String.format("[Card: color: %s, value: %d, action: %s]", Color.class.getEnumConstants()[this.color.ordinal()], this.value.ordinal(), Action.class.getEnumConstants()[this.action.ordinal()]);
    }
}
