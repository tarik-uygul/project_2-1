package com.example.gui.state;

import com.example.card.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Static placeholder GameStateView so GameScreen/RoundEndScreen/
 * GameOverScreen have real data to render against (Handoff Point #2 in
 * hearts-gui-decisions.md) while com.example.game.GameState is still an
 * empty stub. Card itself has no rank/suit accessors yet either, so hands
 * are just anonymous Card instances - real card faces are Person A's art
 * plus Person B's data, not something the GUI can fabricate correctly.
 * Delete this class once GameState implements GameStateView for real.
 */
public class DemoGameStateView implements GameStateView {

    private final List<String> names;
    private final List<Card> localHand;
    private final int[] handSizes;
    private final int[] scores;
    private final List<Card> trickCards;
    private final List<Integer> trickPlayers;
    private int currentPlayerIndex = 0;

    public DemoGameStateView(String localPlayerName) {
        names = Arrays.asList(localPlayerName, "Alex", "Sam", "Robin");

        localHand = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            localHand.add(new Card());
        }

        handSizes = new int[] {13, 13, 13, 13};
        scores = new int[] {12, 20, 5, 30};

        trickCards = Arrays.asList(new Card(), new Card());
        trickPlayers = Arrays.asList(1, 2);
    }

    public void advanceTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % getPlayerCount();
    }

    @Override
    public int getPlayerCount() {
        return names.size();
    }

    @Override
    public String getPlayerName(int playerIndex) {
        return names.get(playerIndex);
    }

    @Override
    public List<Card> getPlayerHand(int playerIndex) {
        return playerIndex == 0 ? localHand : Collections.emptyList();
    }

    @Override
    public int getHandSize(int playerIndex) {
        return handSizes[playerIndex];
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
}
