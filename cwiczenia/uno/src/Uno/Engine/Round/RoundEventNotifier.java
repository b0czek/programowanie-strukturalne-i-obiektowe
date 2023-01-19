package Uno.Engine.Round;

import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.GameDirection;
import Uno.Engine.Player.PlayerInfo;

import java.util.ArrayList;

public class RoundEventNotifier {

    private final ArrayList<RoundEvent> roundEventListeners = new ArrayList<>();

    public void addRoundEventListener(RoundEvent listener) {
        roundEventListeners.add(listener);
    }
    public void removeRoundEventListener(RoundEvent listener) {
        roundEventListeners.remove(listener);
    }

    public void notifyDirectionSwitch(GameDirection newDirection) {
        roundEventListeners.forEach(listener -> listener.onDirectionSwitch(newDirection));
    }
    public void notifyTurnFinish(PlayerInfo nextPlayer) {
        roundEventListeners.forEach(listener -> listener.onTurnFinish(nextPlayer));
    }

    public void notifyCardPlaced(Card placedCard) {
        roundEventListeners.forEach(listener -> listener.onCardPlaced(placedCard));
    }


    public void notifyCardDrew() {
        roundEventListeners.forEach(RoundEvent::onCardDrew);

    }

    public void notifyDiscardPileCleared() {
        roundEventListeners.forEach(RoundEvent::onDiscardPileCleared);
    }

    public void notifyCurrentColorChange(Color newColor) {
        roundEventListeners.forEach(listener -> listener.onCurrentColorChange(newColor));

    }

    public void notifyCardRemovedFromHand(PlayerInfo affectedPlayer) {
        roundEventListeners.forEach(listener -> listener.onCardRemovedFromHand(affectedPlayer));

    }

    public void notifyCardAddedToHand(PlayerInfo affectedPlayer) {
        roundEventListeners.forEach(listener -> listener.onCardAddedToHand(affectedPlayer));

    }

    public void notifyYelledUno(PlayerInfo yellingPlayer) {
        roundEventListeners.forEach(listener -> listener.onYelledUno(yellingPlayer));

    }

    public void notifyRoundEnd(PlayerInfo winner) {
        roundEventListeners.forEach(listener -> listener.onRoundEnd(winner));

    }

}
