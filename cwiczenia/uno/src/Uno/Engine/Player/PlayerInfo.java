package Uno.Engine.Player;

import java.io.*;

public class PlayerInfo implements Serializable {
    public static final long serialVersionUID = 1L;


    private String name;
    private int cardsInHandCount;
    private int ordinal;

    private boolean canChallenge;
    private boolean yelledUno;
    public PlayerInfo() {}

    public PlayerInfo(String name, int cardsInHandCount, int ordinal, boolean yelledUno, boolean canChallenge) {
        this.name = name;
        this.cardsInHandCount = cardsInHandCount;
        this.ordinal = ordinal;
        this.yelledUno = yelledUno;
        this.canChallenge = canChallenge;
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

    public boolean isYelledUno() {
        return yelledUno;
    }

    public boolean isCanChallenge() {
        return canChallenge;
    }
}
