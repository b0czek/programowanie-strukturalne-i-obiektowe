package Uno.Engine;

import Uno.Engine.Round.Round;

public class Game {

    private final Round round;
    public Game(String[] playerNames) {
        round = new Round(playerNames);

    }
    public Round getRound() {
        return round;
    }
}
