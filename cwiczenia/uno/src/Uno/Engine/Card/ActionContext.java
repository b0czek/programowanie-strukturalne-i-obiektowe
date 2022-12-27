package Uno.Engine.Card;

import Uno.Engine.Pile.DiscardPile;
import Uno.Engine.Pile.DrawPile;
import Uno.Engine.Player.Players;
import Uno.Engine.Round.RoundEventNotifier;

public class ActionContext {
    private final Players players;
    private final DiscardPile discardPile;
    private final DrawPile drawPile;
    private final RoundEventNotifier notifier;

    public ActionContext(Players players, DiscardPile discardPile, DrawPile drawPile, RoundEventNotifier notifier) {
        this.players = players;
        this.discardPile = discardPile;
        this.drawPile = drawPile;
        this.notifier = notifier;
    }

    public Players getPlayers() {
        return players;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }

    public RoundEventNotifier getNotifier() {
        return notifier;
    }
}
