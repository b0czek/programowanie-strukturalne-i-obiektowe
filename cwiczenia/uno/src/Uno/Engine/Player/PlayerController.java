package Uno.Engine.Player;

import Uno.Engine.Card.Action;
import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.GameDirection;
import Uno.Engine.Round.Round;
import Uno.Engine.Round.RoundEvent;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerController {
    private Round round;

    public PlayerController(Round round) {
        this.round = round;
    }

    public void playCard(Player player, Card playedCard) {
        checkTurn(player);

        if(round.getPlayers().peekPreviousPlayer().isChallengeable()) {
            throw new IllegalStateException("Player needs to select whether to challenge previous player");
        }

        if(!player.getHand().contains(playedCard)) {
            throw new IllegalArgumentException("Player may not play card they don't have in their hand");
        }
        if(!round.getDiscardPile().canUseCard(playedCard)) {
            throw new IllegalArgumentException("Card cannot be played");
        }
        Player.removeCardFromHand(player, playedCard, round.getNotifier());

        round.useCard(playedCard);


        // round won
        if(player.getHand().size() == 0) {
            round.notifyRoundEndListeners(player);
            round.getNotifier().notifyRoundEnd(player.getInfo());
        }
        else {
            round.getPlayers().goToNextPlayer();

        }

    }

    public void challengeWildcard(Player player) {
        Player previousPlayer = round.getPlayers().peekPreviousPlayer();
        Player currentPlayer = round.getPlayers().getCurrentPlayer();
        checkTurn(player);

        if(!previousPlayer.isChallengeable()) {
            throw new IllegalStateException("Player may not challenge if no Draw 4 wildcard was played in previous turn");
        }
        if(round.getDiscardPile().hadCardOfMatchingColor(previousPlayer.getHand())) {
            for(int i = 0; i < 4; i++) {
                Card removedCard = Player.removeCardFromHand(currentPlayer, currentPlayer.getHand().getLastCard(), round.getNotifier());
                Player.addCardToHand(previousPlayer, removedCard, round.getNotifier());
            }

        }
        else {
            for(int i = 0 ; i < 2; i++) {
                Player.addCardToHand(currentPlayer, round.getDrawPile().draw(), round.getNotifier());
            }
            round.getPlayers().goToNextPlayer();
        }

        round.getPlayers().peekPreviousPlayer().setChallengeable(false);
    }

    public void yellUno(Player player) {
        checkTurn(player);

        if(player.getHand().size() != 2) {
            throw new IllegalStateException("Cannot yell UNO when you are not about to play next-to-last card");
        }
        round.getNotifier().notifyYelledUno(player.getInfo());
        player.setYelledUno(true);
    }

    public void catchNotYelledUno(Player player) {
        if(round.getPlayers().peekPreviousPlayer().getHand().size() != 1) {
            throw new IllegalStateException("Previous player did not need to yell UNO");
        }
        if(round.getPlayers().peekPreviousPlayer().didYellUno()) {
            throw new IllegalStateException("Previous player yelled UNO");
        }
        for(int i = 0 ; i < 2 ; i ++) {
            Player.addCardToHand(round.getPlayers().peekPreviousPlayer(), round.getDrawPile().draw(), round.getNotifier());
        }
    }

    public void drawCard(Player player) {
        checkTurn(player);

        if(player.didDrawCard()) {
            throw new IllegalStateException("Cannot draw card more than once");
        }
        player.setDrewCard(true);

        drawCardFromPile(player);
        if(!round.getDiscardPile().canUseAnyCard(player.getHand())) {
            passTurn(player);
        }
    }

    public void passTurn(Player player) {
        checkTurn(player);

        round.getPlayers().peekPreviousPlayer().setChallengeable(false);

        if(!player.didDrawCard()) {
            drawCardFromPile(player);
        }

        round.getPlayers().goToNextPlayer();

    }


    public Color getCurrentColor() {
        return round.getDiscardPile().getCurrentColor();
    }
    public Card getLastPlayedCard() {
        return round.getDiscardPile().getLastCard();
    }
    public GameDirection getGameDirection() {
        return round.getPlayers().getGameDirection();
    }
    public PlayerInfo[] getPlayersInfo() {
        return round.getPlayers()
                .getPlayers()
                .stream()
                .map(player -> player.getInfo())
                .toArray(PlayerInfo[]::new);
    }

    public PlayerInfo getCurrentTurnPlayer() {
        return round.getPlayers().getCurrentPlayer().getInfo();
    }


    public void addRoundEventListener(RoundEvent listener) {
        round.getNotifier().addRoundEventListener(listener);
    }
    public void removeRoundEventListener(RoundEvent listener) {
        round.getNotifier().removeRoundEventListener(listener);
    }

    private void checkTurn(Player player) {
        if(round.getPlayers().getCurrentPlayer() != player) {
            throw new IllegalStateException("It's not this player's turn");
        }
    }
    private void drawCardFromPile(Player player) {
        Player.addCardToHand(player, round.getDrawPile().draw(), round.getNotifier());

    }

}
