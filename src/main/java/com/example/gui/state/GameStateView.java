package com.example.gui.state;

import com.example.cards.Card;

import java.util.List;

/**
 * Read-only contract the GUI renders against (Handoff Point #2 in
 * hearts-gui-decisions.md). The game-logic side implements this on top of
 * the real GameState/PlayerState once those are built; the GUI only ever
 * reads through this interface. Changes go the other way, through GameActions.
 *
 * The game is 4 real people sharing one laptop, so there is no single
 * "local" player: the GUI asks for the hand of whoever is currently sitting
 * at the laptop, and hides it behind a turn gate between players.
 */
public interface GameStateView {

    int getPlayerCount();

    String getPlayerName(int playerIndex);

    /** Full hand contents. The GUI only asks for the hand of the player currently at the laptop. */
    List<Card> getPlayerHand(int playerIndex);

    /** Number of cards in a player's hand - what the other players see as face-down cards. */
    int getHandSize(int playerIndex);

    int getCurrentPlayerIndex();

    /** Cards played in the trick so far, in play order. */
    List<Card> getCurrentTrickCards();

    /** Player index who played each card in getCurrentTrickCards(), same order/length. */
    List<Integer> getCurrentTrickPlayers();

    int getPlayerScore(int playerIndex);

    /**
     * Index of the player that playerIndex passes their 3 cards to this
     * round, or -1 if this is a no-passing round (the GUI then skips the
     * passing phase entirely).
     */
    int getPassTarget(int playerIndex);

    /** The 3 cards playerIndex received during passing; empty until everyone has passed. */
    List<Card> getReceivedCards(int playerIndex);
}
