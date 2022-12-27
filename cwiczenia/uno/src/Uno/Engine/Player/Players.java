package Uno.Engine.Player;

import Uno.Engine.GameDirection;
import Uno.Engine.Round.RoundEventNotifier;

import java.util.ArrayList;
import java.util.Collection;

public class Players {
    private ArrayList<Player> players = new ArrayList<>();
    private GameDirection gameDirection = GameDirection.CLOCKWISE;
    private int previousPlayerIdx = 0;
    private int currentPlayerIdx = 0;

    private RoundEventNotifier notifier = null;

    public Players() {}

    public Players(Collection<Player> players) {
        players.forEach(p -> this.players.add(p));
    }

    public void switchDirection() {
        gameDirection = (gameDirection == GameDirection.CLOCKWISE ? GameDirection.COUNTER_CLOCKWISE : GameDirection.CLOCKWISE);
        if(notifier != null) {
            notifier.notifyDirectionSwitch(gameDirection);
        }
    }
    public GameDirection getGameDirection() {
        return this.gameDirection;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }

    public Player peekPreviousPlayer() { return players.get(previousPlayerIdx); }
    public Player peekNextPlayer() {
        return players.get(getNextPlayerIdx());
    }

    public Player getNextPlayer() {
        goToNextPlayer();
        return getCurrentPlayer();
    }

    public void goToNextPlayer() {
        previousPlayerIdx = currentPlayerIdx;
        currentPlayerIdx = getNextPlayerIdx();

        getCurrentPlayer().setYelledUno(false);
        getCurrentPlayer().setDrewCard(false);

        if(notifier != null) {
            notifier.notifyTurnFinish(getCurrentPlayer().getInfo());
        }
    }

    private int getNextPlayerIdx() {
        return  (currentPlayerIdx + players.size() + (gameDirection == GameDirection.CLOCKWISE ? 1 : -1)) % players.size();
    }

    public void setNotifier(RoundEventNotifier notifier) {
        this.notifier = notifier;
    }

    public int size() {
        return players.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
