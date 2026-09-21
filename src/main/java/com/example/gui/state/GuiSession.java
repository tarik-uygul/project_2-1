package com.example.gui.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds GUI-local state that needs to survive a screen swap - currently
 * the four player names entered on SetupScreen. Owned by HeartsGame, one
 * instance per app run.
 */
public class GuiSession {

    private List<String> playerNames = new ArrayList<>(Arrays.asList("Player 1", "Player 2", "Player 3", "Player 4"));

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = new ArrayList<>(playerNames);
    }
}
