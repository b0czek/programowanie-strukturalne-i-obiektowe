package Uno.Engine.Card;

import Uno.Engine.Pile.DiscardPile;
import Uno.Engine.Pile.DrawPile;
import Uno.Engine.Player.Player;
import Uno.Engine.Player.Players;
import Uno.Engine.Round.RoundEventNotifier;

public enum Action {

    DRAW_TWO {
        @Override
        public void executeAction(ActionContext actionContext) {
            // if it's a starting card
            if(actionContext.getDiscardPile().getCurrentColor() == Color.NONE) {
                drawCardToPlayer(actionContext.getNotifier(), actionContext.getPlayers().getCurrentPlayer(), actionContext.getDrawPile(), 2);
            }
            else {

                drawCardsToNextPlayer(actionContext, 2, true);
            }
        }
    },
    REVERSE {
        @Override
        public void executeAction(ActionContext actionContext) {
            actionContext.getPlayers().switchDirection();

        }
    },
    SKIP {
        @Override
        public void executeAction(ActionContext actionContext) {
            actionContext.getPlayers().getNextPlayer();

        }
    },
    WILD_CARD {
        @Override
        public void executeAction(ActionContext actionContext) {
            actionContext.getDiscardPile().setCurrentColor(this.getChangeColorTo() != null ? this.getChangeColorTo() : actionContext.getDiscardPile().getCurrentColor());
        }
    },
    WILD_DRAW_4 {
        @Override
        public void executeAction(ActionContext actionContext) {
            actionContext.getDiscardPile().setCurrentColor(this.getChangeColorTo() != null ? this.getChangeColorTo() : actionContext.getDiscardPile().getCurrentColor());

            actionContext.getPlayers().getCurrentPlayer().setChallengeable(true);

            Action.drawCardsToNextPlayer(actionContext, 4, false);
        }
    },
    NONE {
        @Override
        public void executeAction(ActionContext actionContext) {
        }
    };

    private Color changeColorTo = null;

    public abstract void executeAction(ActionContext actionContext);

    public void setChangeColorTo(Color color) {
        this.changeColorTo = color;
    }

    public Color getChangeColorTo() {
        return changeColorTo;
    }

    private static void drawCardsToNextPlayer(ActionContext actionContext, int drewCards, boolean skipPlayer) {
        Player player = skipPlayer ? actionContext.getPlayers().getNextPlayer() : actionContext.getPlayers().peekNextPlayer();
        drawCardToPlayer(actionContext.getNotifier(), player, actionContext.getDrawPile(), drewCards);
    }
    private static void drawCardToPlayer(RoundEventNotifier notifier, Player player, DrawPile drawPile, int drewCards) {
        for(int i = 0 ; i < drewCards; i++){
            Player.addCardToHand(player, drawPile.draw(), notifier);

        }
    }

}
