package Uno.Network.Server.ClientRequest;


import Uno.Engine.Card.Card;
import Uno.Network.Server.ClientRequest.RequestHandlers.*;
import Uno.Network.Server.ClientRequest.RequestHandlers.Game.Game;
import Uno.Network.Server.ClientRequest.RequestHandlers.Game.Player;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Game.GameState;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public enum RequestType {
    JOIN(null, String.class, Join::handleClientJoin),
    HEARTBEAT(null, null, Heartbeat::handleHeartbeat),
    SYNC(null, null, Sync::handleSync),
    CHAT_MESSAGE(null, String.class, Chat::handleChatMessage),
    CHAT_HISTORY(null, null, Chat::handleChatHistory),
    CLIENTS_DATA(null, null, GameInfo::handleClientsData),
    CLIENT_DATA(null, null, GameInfo::handleClientData),
    GAME_STATE(null, null, GameInfo::handleGameState),

    GET_PLAYERS(GameState.IN_PROGRESS, null, Player::handleGetPlayer),
    GET_PLAYER(GameState.IN_PROGRESS, String.class, Player::handleGetPlayers),
    GET_HAND(GameState.IN_PROGRESS, null, Player::handleGetHand),

    GET_GAME_DIRECTION(GameState.IN_PROGRESS, null, Game::handleGetGameDirection),
    GET_CURRENT_COLOR(GameState.IN_PROGRESS, null, Game::handleGetCurrentColor),
    GET_DISCARD_PILE(GameState.IN_PROGRESS, null, Game::handleGetDiscardPile),
    GET_DRAW_PILE_SIZE(GameState.IN_PROGRESS, null, Game::handleGetDrawPileSize),
    GET_CURRENT_TURN_PLAYER(GameState.IN_PROGRESS, null, Game::handleGetCurrentTurnPlayer),

    PLAY_CARD(GameState.IN_PROGRESS, Card.class, Player::handlePlayCard),
    CHALLENGE_WILDCARD(GameState.IN_PROGRESS, null, Player::handleChallengeWildcard),
    CATCH_NOT_YELLED_UNO(GameState.IN_PROGRESS, null, Player::handleCatchNotYelledUno),
    DRAW_CARD(GameState.IN_PROGRESS, null, Player::handleDrawCard),
    PASS_TURN(GameState.IN_PROGRESS, null, Player::handlePassTurn),
    YELL_UNO(GameState.IN_PROGRESS, null, Player::yellUno),


    SET_READY(GameState.LOBBY, null, Lobby::handleClientReady),
    SET_NOT_READY(GameState.LOBBY, null, Lobby::handleClientNotReady),
    TOGGLE_READY(GameState.LOBBY, null, Lobby::handleToggleReady);

    private final RequestHandler handler;
    private final Class attachmentClass;
    private final GameState targetGameState;

    RequestType(GameState targetGameState, Class attachmentClass, RequestHandler handler) {
        this.handler = handler;
        this.attachmentClass = attachmentClass;
        this.targetGameState = targetGameState;
    }

    public void executeHandler(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        this.handler.execute(gameServer, serverClient, request);
    }

    public GameState getTargetGameState() {
        return targetGameState;
    }

    public Class getAttachmentClass() {
        return attachmentClass;
    }
}
