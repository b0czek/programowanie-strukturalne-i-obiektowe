package Uno.Engine;

import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.GameDirection;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.PlayerInfo;
import Uno.Engine.Round.Round;

import java.util.Scanner;
import java.util.stream.Collectors;

public class BasicGame {

    private Round round;
    private Scanner scanner;

    private boolean gameEnded = false;

    private void printPlayer(Player player) {
        System.out.println("Gracz: " + player.getName());
        System.out.println("Karty: \n" + player.getHand().stream().map(card -> card.toString()).collect(Collectors.joining("\n")));
        System.out.println("Aktualna karta na stosie: " + player.controller.getLastPlayedCard().toString());
        System.out.println("Aktualny kolor: " + player.controller.getCurrentColor());
        System.out.println("Kierunek gry: " + player.controller.getGameDirection());
        System.out.println("Gracze: ");
        for(PlayerInfo p : player.controller.getPlayersInfo()) {
            System.out.println((p.getOrdinal() == player.getOrdinal() ? ">":"") + p.getName() + " - " + p.getCardsInHandCount());
        }

        System.out.println();
    }

    private void chooseCard(Player player) {
        System.out.println("Wybierz karte: ");
        for(int i = 0 ; i < player.getHand().size(); i++) {
            System.out.println(i + ": " + player.getHand().get(i).toString());
        }
        int idx = scanner.nextInt();
        Card playedCard = player.getHand().get(idx);

        if(playedCard.isWildCard()) {
            System.out.println("Wybierz nowy kolor stosu");
            for(int i = 0 ; i < Color.values().length; i++) {
                System.out.println(i + ": " + Color.class.getEnumConstants()[i]);
            }
            Color newColor = Color.values()[scanner.nextInt()];
            playedCard.getAction().setChangeColorTo(newColor);
        }

        player.controller.playCard(player, playedCard);
    }

    private void chooseMove(Player player) {
        printPlayer(player);
        while(round.getPlayers().getCurrentPlayer() == player) {
            System.out.println("Wybierz akcje");
            System.out.println("1. playCard");
            System.out.println("2. challengeWildcard");
            System.out.println("3. yellUno");
            System.out.println("4. catchNotYelledUno");
            System.out.println("5. drawCard");
            System.out.println("6. passTurn");

            try {
                switch(scanner.nextInt()) {
                    case 1 -> chooseCard(player);
                    case 2 -> player.controller.challengeWildcard(player);
                    case 3 -> player.controller.yellUno(player);
                    case 4 -> player.controller.catchNotYelledUno(player);
                    case 5 -> player.controller.drawCard(player);
                    case 6 -> player.controller.passTurn(player);
                }

            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
            printPlayer(player);

        }

    }

    public BasicGame(Round round) {
        this.round = round;
        this.scanner = new Scanner(System.in);

        this.round.addRoundEndListener(winner -> {
            gameEnded = true;
            System.out.println("BRAWA DLA " + winner.getName());
        });

        while(!gameEnded) {
            chooseMove(round.getPlayers().getCurrentPlayer());
        }

    }
}
