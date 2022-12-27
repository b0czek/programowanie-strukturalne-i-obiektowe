package Uno.Engine.Round;

import Uno.Engine.Card.ActionContext;
import Uno.Engine.Card.Card;
import Uno.Engine.Deck.Deal;
import Uno.Engine.Deck.Deck;
import Uno.Engine.Pile.DiscardPile;
import Uno.Engine.Pile.DrawPile;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.Players;
import Uno.Engine.Player.PlayerController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Round {
    private final Players players;
    private final DiscardPile discardPile;
    private final DrawPile drawPile;

    private final RoundEventNotifier notifier;

    private final PlayerController controller;

    private final ArrayList<RoundEndCallback> roundEndListeners;

    public Round(String[] playerNames) {
        Deck deck = new Deck();
        Deal deal = new Deal(deck, playerNames.length);
        Card[][] deals = deal.getDeals();

        controller = new PlayerController(this);
        notifier = new RoundEventNotifier();

        players = new Players(IntStream.range(0, playerNames.length)
                .mapToObj(i -> new Player(i, controller, playerNames[i], List.of(deals[i])))
                .collect(Collectors.toSet()));
        players.setNotifier(notifier);


        discardPile = new DiscardPile();
        discardPile.setNotifier(notifier);

        drawPile = new DrawPile(deal.getRemainder(), discardPile);
        drawPile.setNotifier(notifier);

        useCard(drawPile.getLastCard());

        roundEndListeners = new ArrayList<>();
    }

    public void useCard(Card playedCard) {
        getDiscardPile().place(playedCard);

        if(playedCard.isActionCard()) {
            playedCard.getAction().executeAction(new ActionContext(players, discardPile, drawPile, notifier));
        }
    }

    public int sumPoints() {
        return players.getPlayers()
                .stream()
                .flatMapToInt(player -> player
                            .getHand()
                            .stream()
                            .mapToInt(card -> card.getPoints())
                )
                .sum();
    }


    public void addRoundEndListener(RoundEndCallback callback) {
        roundEndListeners.add(callback);
    }
    public void notifyRoundEndListeners(Player winner) {
        roundEndListeners.forEach(listener -> listener.onRoundEnd(winner));
    }

    public Players getPlayers() {
        return players;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }

    public RoundEventNotifier getNotifier() {
        return notifier;
    }
}
