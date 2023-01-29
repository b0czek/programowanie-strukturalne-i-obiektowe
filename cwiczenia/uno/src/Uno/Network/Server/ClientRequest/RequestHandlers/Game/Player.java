package Uno.Network.Server.ClientRequest.RequestHandlers.Game;

import Uno.Engine.Card.Card;
import Uno.Engine.Pile.Pile;
import Uno.Engine.Player.PlayerInfo;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.ServerClient;

import java.io.IOException;
import java.util.Optional;

public class Player {
    public static void handleGetPlayer(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        String name = (String)request.getAttachment();
        Optional<Uno.Engine.Player.Player> player = getPlayer(gameServer,serverClient).controller.getPlayerInfo(name);
        if(player.isPresent()) {
            serverClient.sendOkResponse(request, MessageType.PLAYER_DATA, player.get().getInfo());
        }
        else {
            serverClient.sendEmptyErrorResponse(request, "No such player");
        }
    }
    public static void handleGetPlayers(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        PlayerInfo[] playerInfos = getPlayer(gameServer, serverClient).controller.getPlayersInfo();
        serverClient.sendOkResponse(request, MessageType.PLAYERS_DATA, playerInfos);
    }
    public static void handleGetHand(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        Pile hand = getPlayer(gameServer,serverClient).getHand();
        serverClient.sendOkResponse(request, MessageType.HAND, hand);
    }
    public static void handlePlayCard(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        var player = getPlayer(gameServer, serverClient);
        try {
            player.controller.playCard(player, (Card) request.getAttachment());
        }
        catch(Exception ex) {
            serverClient.sendEmptyErrorResponse(request, ex.getMessage());
            return;
        }
        serverClient.sendOkResponse(request, MessageType.HAND, player.getHand());
    }

    public static void handleChallengeWildcard(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        var player = getPlayer(gameServer, serverClient);
        try {
            player.controller.challengeWildcard(player);
        }
        catch(Exception ex) {
            serverClient.sendEmptyErrorResponse(request, ex.getMessage());
            return;
        }
        serverClient.sendEmptyOkResponse(request);
    }

    public static void handleCatchNotYelledUno(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        var player = getPlayer(gameServer, serverClient);
        try {
            player.controller.catchNotYelledUno();
        }
        catch(Exception ex) {
            serverClient.sendEmptyErrorResponse(request, ex.getMessage());
            return;
        }
        serverClient.sendEmptyOkResponse(request);
    }

    public static void handleDrawCard(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        var player = getPlayer(gameServer, serverClient);
        try {
            player.controller.drawCard(player);
        }
        catch(Exception ex) {
            serverClient.sendEmptyErrorResponse(request, ex.getMessage());
            return;
        }
        serverClient.sendEmptyOkResponse(request);
    }


    public static void handlePassTurn(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        var player = getPlayer(gameServer, serverClient);
        try {
            player.controller.passTurn(player);
        }
        catch(Exception ex) {
            serverClient.sendEmptyErrorResponse(request, ex.getMessage());
            return;
        }
        serverClient.sendEmptyOkResponse(request);
    }


    public static void yellUno(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        Uno.Engine.Player.Player player = getPlayer(gameServer, serverClient);

        try {
            player.controller.yellUno(player);
        }
        catch(Exception ex) {
            serverClient.sendEmptyErrorResponse(request, ex.getMessage());
            return;
        }

        serverClient.sendEmptyOkResponse(request);
    }


    private static Uno.Engine.Player.Player getPlayer(GameServer gameServer, ServerClient serverClient) {
        return gameServer.getClients().get(serverClient).getPlayer();
    }
}
