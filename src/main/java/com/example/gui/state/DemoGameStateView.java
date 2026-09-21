package com.example.gui.state;

import com.example.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Placeholder GameStateView + GameActions so the screens have something to
 * run against while com.example.game.GameState is still an empty stub. It
 * enforces NO rules: any card can be played, passed cards are only recorded
 * (hands don't change), tricks aren't scored. Card has no rank/suit yet
 * either, so hands are anonymous Card instances. Delete this class once the
 * real game logic implements the two interfaces.
 */
public class DemoGameStateView implements GameStateView, GameActions {

    private static final int PLAYERS = 4;
    private static final int HAND_SIZE = 13;

    private final List<String> names;
    private final int[] scores = {12, 20, 5, 30};
    private final List<List<Card>> hands = new ArrayList<>();
    private final List<List<Card>> passed = new ArrayList<>();
    private final List<Card> trickCards = new ArrayList<>();
    private final List<Integer> trickPlayers = new ArrayList<>();
    private int currentPlayerIndex;
    private int round;

    public DemoGameStateView(List<String> names) {
        this.names = new ArrayList<>(names);
        deal();
    }

    /** Fresh hands for the next round; the pass direction rotates left, right, across, none. */
    public void startNextRound() {
        round++;
        deal();
    }

    private void deal() {
        hands.clear();
        passed.clear();
        for (int p = 0; p < PLAYERS; p++) {
            List<Card> hand = new ArrayList<>();
            for (int i = 0; i < HAND_SIZE; i++) {
                hand.add(new Card());
            }
            hands.add(hand);
            passed.add(new ArrayList<>());
        }
        trickCards.clear();
        trickPlayers.clear();
        currentPlayerIndex = 0;
    }

    @Override
    public int getPlayerCount() {
        return PLAYERS;
    }

    @Override
    public String getPlayerName(int playerIndex) {
        return names.get(playerIndex);
    }

    @Override
    public List<Card> getPlayerHand(int playerIndex) {
        return hands.get(playerIndex);
    }

    @Override
    public int getHandSize(int playerIndex) {
        return hands.get(playerIndex).size();
    }

    @Override
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    @Override
    public List<Card> getCurrentTrickCards() {
        return trickCards;
    }

    @Override
    public List<Integer> getCurrentTrickPlayers() {
        return trickPlayers;
    }

    @Override
    public int getPlayerScore(int playerIndex) {
        return scores[playerIndex];
    }

    @Override
    public int getPassTarget(int playerIndex) {
        switch (round % 4) {
            case 0:
                return (playerIndex + 1) % PLAYERS;
            case 1:
                return (playerIndex + 3) % PLAYERS;
            case 2:
                return (playerIndex + 2) % PLAYERS;
            default:
                return -1;
        }
    }

    @Override
    public List<Card> getReceivedCards(int playerIndex) {
        for (int source = 0; source < PLAYERS; source++) {
            if (getPassTarget(source) == playerIndex) {
                return Collections.unmodifiableList(passed.get(source));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void passCards(int playerIndex, List<Card> cards) {
        passed.set(playerIndex, new ArrayList<>(cards));
    }

    @Override
    public boolean playCard(int playerIndex, Card card) {
        if (playerIndex != currentPlayerIndex || !hands.get(playerIndex).remove(card)) {
            return false;
        }
        // A finished trick stays visible until the next card starts a new one.
        if (trickCards.size() == PLAYERS) {
            trickCards.clear();
            trickPlayers.clear();
        }
        trickCards.add(card);
        trickPlayers.add(playerIndex);
        currentPlayerIndex = (currentPlayerIndex + 1) % PLAYERS;
        return true;
    }
}
