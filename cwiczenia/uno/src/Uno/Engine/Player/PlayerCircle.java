package Uno.Engine.Player;

import Uno.Engine.GameDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PlayerCircle {
    private ArrayList<Player> players = new ArrayList<>();
    private GameDirection gameDirection = GameDirection.CLOCKWISE;
    private int currentPlayerIdx = 0;

    public PlayerCircle() {}

    public PlayerCircle(Collection<Player> players) {
        players.forEach(p -> this.players.add(p));
    }

    public void switchDirection() {
        gameDirection = (gameDirection == GameDirection.CLOCKWISE ? GameDirection.COUNTER_CLOCKWISE : GameDirection.CLOCKWISE);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }

    public Player getNextPlayer() {
        currentPlayerIdx = (currentPlayerIdx + (gameDirection == GameDirection.CLOCKWISE ? 1 : -1)) % players.size();
        return getCurrentPlayer();
    }

    public int size() {
        return players.size();
    }

}
