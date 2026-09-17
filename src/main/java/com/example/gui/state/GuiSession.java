package com.example.gui.state;

/**
 * Holds GUI-local state that needs to survive a screen swap but has
 * nowhere to live yet because the real GameState/PlayerState (Person B's
 * side) aren't implemented - currently just the name entered on
 * SetupScreen. Owned by HeartsGame, one instance per app run.
 */
public class GuiSession {

    private String playerName = "You";

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
