package Uno.Engine.Player;

import java.io.*;

public class PlayerInfo implements Serializable {
    public static final long serialVersionUID = 1L;


    private String name;
    private int cardsInHandCount;
    private int ordinal;

    public PlayerInfo() {}

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
