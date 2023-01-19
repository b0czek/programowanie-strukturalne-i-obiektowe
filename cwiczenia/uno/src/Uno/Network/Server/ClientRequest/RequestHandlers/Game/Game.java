package Uno.Network.Server.ClientRequest.RequestHandlers.Game;

import Uno.Engine.Card.Color;
import Uno.Engine.GameDirection;
import Uno.Engine.Pile.DiscardPile;
import Uno.Engine.Player.PlayerInfo;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class Game {
    public static void handleGetCurrentColor(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        Color currentColor = gameServer.getGame().getRound().getDiscardPile().getCurrentColor();
        serverClient.sendOkResponse(request, MessageType.CURRENT_COLOR, currentColor);
    }
    public static void handleGetDiscardPile(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        DiscardPile discardPile = gameServer.getGame().getRound().getDiscardPile();
        serverClient.sendOkResponse(request, MessageType.DISCARD_PILE, discardPile);
    }
    public static void handleGetGameDirection(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        GameDirection gameDirection = gameServer.getGame().getRound().getPlayers().getGameDirection();
        serverClient.sendOkResponse(request, MessageType.GAME_DIRECTION, gameDirection);

    }
    public static void handleGetDrawPileSize(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        int drawPileSize = gameServer.getGame().getRound().getDrawPile().size();
        serverClient.sendOkResponse(request, MessageType.DRAW_PILE_SIZE, drawPileSize);

    }
    public static void handleGetCurrentTurnPlayer(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        PlayerInfo playerInfo = gameServer.getGame().getRound().getPlayers().getCurrentPlayer().getInfo();
        serverClient.sendOkResponse(request, MessageType.CURRENT_TURN_PLAYER, playerInfo);
    }

}