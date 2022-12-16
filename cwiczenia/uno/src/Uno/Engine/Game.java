package Uno.Engine;

import Uno.Engine.Card.Card;
import Uno.Engine.Deck.Deal;
import Uno.Engine.Deck.Deck;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.PlayerCircle;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Game {
    private PlayerCircle players;

    public Game(String[] playerNames) {
        Deck deck = new Deck();
        Deal deal = new Deal(deck, playerNames.length);
        Card[][] deals = deal.getDeals();
        players = new PlayerCircle(IntStream.range(0, playerNames.length)
                                    .mapToObj(i -> new Player(playerNames[i], List.of(deals[i])))
                                    .collect(Collectors.toSet()));
        System.out.println(players.size());
        Player firstPlayer = players.getCurrentPlayer();
        do {
            System.out.println("player: " + players.getCurrentPlayer().getName());
            System.out.println(players.getCurrentPlayer().getHand());
        } while(firstPlayer != players.getNextPlayer());

        System.out.println(deal.getRemainder());

    }

}
