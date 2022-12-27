package Uno.Engine.Round;

import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.GameDirection;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.PlayerInfo;

public interface RoundEvent {


    void onDirectionSwitch(GameDirection newDirection);

    void onTurnFinish(PlayerInfo nextPlayer);

    void onCardPlaced(Card placedCard);

    void onCardDrew();

    void onCurrentColorChange(Color newColor);

    void onCardRemovedFromHand(PlayerInfo affectedPlayer);

    void onCardAddedToHand(PlayerInfo affectedPlayer);

    void onYelledUno(PlayerInfo yellingPlayer);

    void onRoundEnd(PlayerInfo winner);



}
