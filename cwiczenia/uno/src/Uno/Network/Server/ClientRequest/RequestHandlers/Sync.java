package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Engine.Player.Player;
import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameClient;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Game.GameState;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class Sync {
    public static void handleSync(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        serverClient.sendEmptyOkResponse(request);
        syncPlayer(gameServer, serverClient);
    }


    public static void syncPlayer(GameServer gameServer, ServerClient serverClient) throws IOException {
        serverClient.sendMessage(new Message(MessageType.GAME_STATE, gameServer.getGameState()));
        serverClient.sendMessage(new Message(MessageType.CHAT_HISTORY, gameServer.getChatHistory()));
        serverClient.sendMessage(new Message(MessageType.CLIENTS_DATA, gameServer.getClients().values().toArray(GameClient[]::new)));

        if(gameServer.getGameState() == GameState.IN_PROGRESS) {
            Player player = gameServer.getClients().get(serverClient).getPlayer();
            serverClient.sendMessage(new Message(MessageType.PLAYERS_DATA, player.controller.getPlayersInfo()));
            serverClient.sendMessage(new Message(MessageType.HAND, player.getHand()));
            serverClient.sendMessage(new Message(MessageType.GAME_DIRECTION, player.controller.getGameDirection()));
            serverClient.sendMessage(new Message(MessageType.DISCARD_PILE, player.controller.getDiscardPile()));
            serverClient.sendMessage(new Message(MessageType.CURRENT_COLOR, player.controller.getCurrentColor()));
            serverClient.sendMessage(new Message(MessageType.DRAW_PILE_SIZE, player.controller.getDrawPileSize()));
            serverClient.sendMessage(new Message(MessageType.CURRENT_TURN_PLAYER, player.controller.getCurrentTurnPlayer()));
        }
    }

    public static void syncPlayers(GameServer gameServer, ServerClient ...serverClients) {
        for(ServerClient serverClient : serverClients) {
            try {
                Sync.syncPlayer(gameServer, serverClient);
            }
            catch (IOException ex) {
                System.out.println("Failed to sync player " + gameServer.getClients().get(serverClient).getName()+": "+ ex.getMessage());
            }
        }
    }

}
