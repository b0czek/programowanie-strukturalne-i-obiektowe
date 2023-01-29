package Uno.Network.Server.Message;

import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.GameDirection;
import Uno.Engine.Pile.DiscardPile;
import Uno.Engine.Pile.Pile;
import Uno.Engine.Player.PlayerInfo;
import Uno.Network.Server.Chat.ChatHistory;
import Uno.Network.Server.Game.GameClient;
import Uno.Network.Server.Game.GameState;

public enum MessageType {
    EMPTY_RESPONSE(null),
    CHAT_MESSAGE(String.class),
    CHAT_HISTORY(ChatHistory.class),
    CLIENTS_DATA(GameClient[].class),
    CLIENT_DATA(GameClient.class),
    CLIENT_JOINED(GameClient.class), // in game it means reconnect
    CLIENT_LEFT(GameClient.class), // in game it means disconnect
    GAME_STATE(GameState.class),

    // in-game
    PLAYER_DATA(PlayerInfo.class),
    PLAYERS_DATA(PlayerInfo[].class),

    HAND(Pile.class),
    GAME_DIRECTION(GameDirection.class),
    CURRENT_TURN_PLAYER(PlayerInfo.class),
    CURRENT_COLOR(Color.class),
    DISCARD_PILE(Pile.class),
    DRAW_PILE_SIZE(Integer.class),

    // event based
    CARD_PLACED(Card.class),
    UNO_YELL(PlayerInfo.class),
    ROUND_END(PlayerInfo.class);

    MessageType(Class attachmentClass) {

    }

}
