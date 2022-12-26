package Uno.Engine.Round;

import Uno.Engine.Card.Card;
import Uno.Engine.Deck.Deal;
import Uno.Engine.Deck.Deck;
import Uno.Engine.Pile.DiscardPile;
import Uno.Engine.Pile.DrawPile;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.PlayerCircle;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Round {
    private PlayerCircle players;
    private DiscardPile discardPile;
    private DrawPile drawPile;

    public Round(String[] playerNames) {
        Deck deck = new Deck();
        Deal deal = new Deal(deck, playerNames.length);
        Card[][] deals = deal.getDeals();
        players = new PlayerCircle(IntStream.range(0, playerNames.length)
                .mapToObj(i -> new Player(playerNames[i], List.of(deals[i])))
                .collect(Collectors.toSet()));

        System.out.println(players.size());

        for(Player player : players.getPlayers()) {
            System.out.println("player: " + player.getName());
            System.out.println(player.getHand());
        }

        discardPile = new DiscardPile(deal.getRemainder().draw());
        drawPile = new DrawPile(deal.getRemainder(), discardPile);
    }

    public void playCard(Player player, Card playedCard) {
        if(!player.getHand().contains(playedCard)) {
            throw new IllegalArgumentException("Player may not play card they don't have in their hand");
        }
        if(!discardPile.canUseCard(playedCard)) {
            throw new IllegalArgumentException("Card cannot be played");
        }

        player.removeCardFromHand(playedCard);

        if(playedCard.isActionCard()) {
            playedCard.getAction().executeAction(players, discardPile, drawPile);
        }

        this.players.goToNextPlayer();
    }


}
