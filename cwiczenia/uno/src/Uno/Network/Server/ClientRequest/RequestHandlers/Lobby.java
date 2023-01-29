package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameClient;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Game.GameState;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.Server;
import Uno.Network.Server.ServerClient;

import java.io.IOException;

public class Lobby {
    public static void handleClientReady(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        setReady(gameServer, serverClient);
        serverClient.sendEmptyOkResponse(request);
        gameServer.getServer().broadcast(new Message(MessageType.CLIENTS_DATA, GameInfo.getClientsData(gameServer)));

    }
    public static void handleClientNotReady(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        gameServer.getClients().get(serverClient).setReady(false);
        gameServer.getServer().broadcast(new Message(MessageType.CLIENTS_DATA, GameInfo.getClientsData(gameServer)));

        serverClient.sendEmptyOkResponse(request);
    }

    public static void handleToggleReady(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        GameClient c = gameServer.getClients().get(serverClient);
        if(c.isReady()) {
            c.setReady(false);
        }
        else {
            setReady(gameServer, serverClient);
        }

        serverClient.sendEmptyOkResponse(request);
        gameServer.getServer().broadcast(new Message(MessageType.CLIENTS_DATA, GameInfo.getClientsData(gameServer)));

    }

    public static void setReady(GameServer gameServer, ServerClient serverClient) {
        gameServer.getClients().get(serverClient).setReady(true);
        gameServer.getServer().broadcast(new Message(MessageType.CLIENTS_DATA, GameInfo.getClientsData(gameServer)));

        if(gameServer.getClients().values().stream().allMatch(GameClient::isReady)) {
            if(gameServer.getClients().size() >= 3) {
                gameServer.setGameState(GameState.IN_PROGRESS);

            }
        }
    }

}
