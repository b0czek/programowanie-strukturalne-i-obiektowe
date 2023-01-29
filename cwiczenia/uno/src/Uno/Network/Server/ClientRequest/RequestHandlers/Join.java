package Uno.Network.Server.ClientRequest.RequestHandlers;

import Uno.Network.Server.ClientRequest.ClientRequest;
import Uno.Network.Server.Game.GameClient;
import Uno.Network.Server.Game.GameServer;
import Uno.Network.Server.Game.GameState;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;
import Uno.Network.Server.ServerClient;

import java.io.IOException;
import java.util.Map;

public class Join {


    public static void handleClientJoin(GameServer gameServer, ServerClient serverClient, ClientRequest request) throws IOException {
        String name = (String)request.getAttachment();
        if(name.length() < 3) {
            serverClient.sendEmptyErrorResponse(request, "name too short");
            return;
        }
        if(name.length() > 40) {
            serverClient.sendEmptyErrorResponse(request, "name too long");
            return;
        }
        if(gameServer.getClients().containsKey(serverClient)) {
            serverClient.sendEmptyErrorResponse(request, "cannot join more than once");
            return;
        }

        var existingClient = gameServer.getClients()
                .entrySet()
                .stream()
                .filter(client -> client.getValue().getName().equals(name))
                .map(Map.Entry::getKey)
                .toArray(ServerClient[]::new);

        // new player joining lobby
        if(gameServer.getGameState() == GameState.LOBBY && existingClient.length == 0 && gameServer.getClients().size() < 10) {
            gameServer.getClients().put(serverClient, new GameClient(name, serverClient));
        }
        // player reconnecting
        else if(gameServer.getGameState() == GameState.IN_PROGRESS && existingClient.length == 1 && !gameServer.getClients().get(existingClient[0]).isConnected()) {
            GameClient client = gameServer.getClients().get(existingClient[0]);
            gameServer.getClients().remove(existingClient[0]);

            client.setServerClient(serverClient);
            client.setConnected(true);
            gameServer.getClients().put(serverClient, client);
        }
        else {
            serverClient.sendEmptyErrorResponse(request, "cannot join the game");
            return;
        }
        System.out.println("player " + name + " joined the game");
        gameServer.getServer().broadcast(new Message(MessageType.CLIENT_JOINED, gameServer.getClients().get(serverClient)));
        serverClient.sendEmptyOkResponse(request);
        // send sync data to player
        Sync.syncPlayer(gameServer, serverClient);


    }

}
