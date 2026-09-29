package com.example.gui.state;

import com.example.cards.Card;

import java.util.List;

/**
 * What the GUI tells the game logic when a player does something. The GUI
 * never changes game state itself: it calls these, then re-reads
 * GameStateView. Rules (is this card legal? who wins the trick?) belong to
 * the implementation, not the GUI.
 */
public interface GameActions {

    /** playerIndex has chosen these 3 cards to pass. */
    void passCards(int playerIndex, List<Card> cards);

    /** Returns false if the move is rejected (the GUI then shows an "invalid move" message). */
    boolean playCard(int playerIndex, Card card);
}
