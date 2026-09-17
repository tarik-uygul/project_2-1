package com.example.gui.state;

import com.example.card.Card;

import java.util.List;

/**
 * Read-only contract GameScreen renders against (Handoff Point #2 in
 * hearts-gui-decisions.md). The game-logic side implements this on top of
 * the real GameState/PlayerState once those are built; the GUI only ever
 * reads through this interface, never mutates state directly.
 *
 * Convention: playerIndex 0 is always the local/human player.
 */
public interface GameStateView {

    int getPlayerCount();

    String getPlayerName(int playerIndex);

    /** Full hand contents - only meaningful for playerIndex 0 (the local player). */
    List<Card> getPlayerHand(int playerIndex);

    /** For opponents, only the count is shown — never their card contents. */
    int getHandSize(int playerIndex);

    int getCurrentPlayerIndex();

    /** Cards played in the trick so far, in play order. */
    List<Card> getCurrentTrickCards();

    /** Player index who played each card in getCurrentTrickCards(), same order/length. */
    List<Integer> getCurrentTrickPlayers();

    int getPlayerScore(int playerIndex);
}
