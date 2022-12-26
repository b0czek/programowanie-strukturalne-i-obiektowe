package Uno.Engine;

import Uno.Engine.Card.Card;
import Uno.Engine.Deck.Deal;
import Uno.Engine.Deck.Deck;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.PlayerCircle;
import Uno.Engine.Round.Round;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Game {

    public Game(String[] playerNames) {

        Round round = new Round(playerNames);
    }

}
