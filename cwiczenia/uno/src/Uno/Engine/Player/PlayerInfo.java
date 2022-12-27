package Uno.Engine.Player;

public class PlayerInfo {
    private final String name;
    private final int cardsInHandCount;
    private final int ordinal;

    public PlayerInfo(String name, int cardsInHandCount, int ordinal ) {
        this.name = name;
        this.cardsInHandCount = cardsInHandCount;
        this.ordinal = ordinal;
    }

    public String getName() {
        return name;
    }

    public int getCardsInHandCount() {
        return cardsInHandCount;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
