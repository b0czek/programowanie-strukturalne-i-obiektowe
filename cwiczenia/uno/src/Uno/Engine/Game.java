package Uno.Engine;

import Uno.Engine.Round.Round;


public class Game {

    public Game(String[] playerNames) {

        Round round = new Round(playerNames);
        BasicGame basicGame = new BasicGame(round);
    }

}
