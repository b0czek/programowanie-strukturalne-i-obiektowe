package Uno.Engine.Round;

import Uno.Engine.Player.Player;

public interface RoundEndCallback {
    void onRoundEnd(Player winner);
}
