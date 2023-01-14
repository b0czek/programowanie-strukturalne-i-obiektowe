package Uno.Network.Server.ClientRequest;


import Uno.Network.Server.ClientRequest.RequestHandlers.*;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Game.GameState;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public enum RequestType {
    JOIN(null, String.class, Join::handleClientJoin), // join the game
    SYNC(null, null, Sync::handleSync), // request game data transmission
    CHAT_MESSAGE(null, String.class, Chat::handleChatMessage),
    CHAT_HISTORY(null, null, Chat::handleChatHistory),
    CLIENTS_INFO(null, null, GameInfo::handleClientsInfo),
    CLIENT_INFO(null, null, GameInfo::handleClientInfo),
    GAME_STATE(null, null, GameInfo::handleGameState),


    SET_READY(GameState.LOBBY, null, Lobby::handleClientReady),
    SET_NOT_READY(GameState.LOBBY, null, Lobby::handleClientNotReady);


    private RequestHandler handler;
    private Class attachmentClass = null;
    private GameState targetGameState;

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
