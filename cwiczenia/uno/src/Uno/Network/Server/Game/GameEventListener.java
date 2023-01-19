package Uno.Network.Server.Game;

import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.GameDirection;
import Uno.Engine.Player.PlayerInfo;
import Uno.Engine.Round.RoundEvent;
import Uno.Network.Server.Message.Message;
import Uno.Network.Server.Message.MessageType;

import java.io.IOException;
import java.util.Optional;

public class GameEventListener implements RoundEvent {
    private final GameServer gameServer;

    public GameEventListener(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void onDirectionSwitch(GameDirection newDirection) {
        gameServer.getServer().broadcast(new Message(MessageType.GAME_DIRECTION, newDirection));
    }

    @Override
    public void onTurnFinish(PlayerInfo nextPlayer) {
        gameServer.getServer().broadcast(new Message(MessageType.CURRENT_TURN_PLAYER, nextPlayer));
    }

    @Override
    public void onCardPlaced(Card placedCard) {
        gameServer.getServer().broadcast(new Message(MessageType.CARD_PLACED, placedCard));
    }

    @Override
    public void onCardDrew() {
        gameServer.getServer().broadcast(new Message(MessageType.DRAW_PILE_SIZE, gameServer.getGame().getRound().getDrawPile().size()));
    }

    @Override
    public void onDiscardPileCleared() {
        gameServer.getServer().broadcast(new Message(MessageType.DRAW_PILE_SIZE, gameServer.getGame().getRound().getDrawPile().size()));
        gameServer.getServer().broadcast(new Message(MessageType.DISCARD_PILE, gameServer.getGame().getRound().getDiscardPile()));

    }

    @Override
    public void onCurrentColorChange(Color newColor) {
        gameServer.getServer().broadcast(new Message(MessageType.CURRENT_COLOR, newColor));
    }

    @Override
    public void onCardRemovedFromHand(PlayerInfo affectedPlayer) {
        gameServer.getServer().broadcast(new Message(MessageType.PLAYER_DATA, affectedPlayer));
        sendHand(affectedPlayer.getName());
    }

    @Override
    public void onCardAddedToHand(PlayerInfo affectedPlayer) {
        gameServer.getServer().broadcast(new Message(MessageType.PLAYER_DATA, affectedPlayer));
        sendHand(affectedPlayer.getName());

    }

    @Override
    public void onYelledUno(PlayerInfo yellingPlayer) {
        gameServer.getServer().broadcast(new Message(MessageType.UNO_YELL, yellingPlayer));
    }

    @Override
    public void onRoundEnd(PlayerInfo winner) {
        gameServer.setGameState(GameState.FINISHED);
        gameServer.getServer().broadcast(new Message(MessageType.ROUND_END, winner));
    }

    private void sendHand(String name) {
        var client = findClient(name);
        if(client.isPresent()) {
            try {
                client.get().getServerClient().sendMessage(new Message(MessageType.HAND, client.get().getPlayer().getHand()));
            } catch (IOException e) {
                System.out.println("failed to send hand to player");
            }
        }
        else {
            System.out.println("failed to find player to send hand to");
        }
    }

    private Optional<GameClient> findClient(String name) {
        return gameServer.getClients()
                .values()
                .stream()
                .filter(client -> client.getName().equals(name))
                .findFirst();

    }
}
