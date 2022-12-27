package Uno.Engine;

import Uno.Engine.Round.Round;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;


public class Game {

    private HashMap<String, Integer> playerScores = new HashMap<>();

    public Game(String[] playerNames) {
        Arrays.stream(playerNames).forEach(name -> playerScores.put(name, 0));

        while(playerScores.values().stream().mapToInt(v -> v).max().orElse(0) < 500) {
            Round round = new Round(playerNames);
            round.addRoundEndListener(winner -> playerScores.merge(winner.getName(), round.sumPoints(), Integer::sum));
            BasicGame basicGame = new BasicGame(round);

        }
    }

}
