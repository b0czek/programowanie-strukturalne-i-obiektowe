package Uno.Engine.Card;

import Uno.Engine.Pile.DiscardPile;
import Uno.Engine.Pile.DrawPile;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.PlayerCircle;
import Uno.Engine.Round.Round;

public enum Action {

    DRAW_TWO {
        @Override
        public void executeAction(PlayerCircle playerCircle, DiscardPile discardPile, DrawPile drawPile) {
            drawCardsToNextPlayer(playerCircle, drawPile, 2);
        }
    },
    REVERSE {
        @Override
        public void executeAction(PlayerCircle playerCircle, DiscardPile discardPile, DrawPile drawPile) {
            playerCircle.switchDirection();

        }
    },
    SKIP {
        @Override
        public void executeAction(PlayerCircle playerCircle, DiscardPile discardPile, DrawPile drawPile) {
            playerCircle.getNextPlayer();

        }
    },
    WILD_CARD {
        @Override
        public void executeAction(PlayerCircle playerCircle, DiscardPile discardPile, DrawPile drawPile) {
            if(this.getChangeColorTo() != null) {
                discardPile.setCurrentColor(this.getChangeColorTo());
            }
        }
    },
    WILD_DRAW_4 {
        @Override
        public void executeAction(PlayerCircle playerCircle, DiscardPile discardPile, DrawPile drawPile) {
            if(this.getChangeColorTo() != null) {
                discardPile.setCurrentColor(this.getChangeColorTo());
            }
            Action.drawCardsToNextPlayer(playerCircle, drawPile, 4);
        }
    },
    NONE {
        @Override
        public void executeAction(PlayerCircle playerCircle, DiscardPile discardPile, DrawPile drawPile) {
        }
    };

    private Color changeColorTo = null;

    public abstract void executeAction(PlayerCircle playerCircle, DiscardPile discardPile, DrawPile drawPile);

    public void setChangeColorTo(Color color) {
        this.changeColorTo = color;
    }

    public Color getChangeColorTo() {
        return changeColorTo;
    }

    private static void drawCardsToNextPlayer(PlayerCircle playerCircle, DrawPile drawPile, int drewCards) {
        Player nextPlayer = playerCircle.getNextPlayer();
        for(int i = 0 ; i < drewCards; i++){
            nextPlayer.addCardToHand(drawPile.draw());

        }
    }

}
