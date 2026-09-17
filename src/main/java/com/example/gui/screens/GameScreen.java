package com.example.gui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.example.card.Card;
import com.example.gui.HeartsGame;
import com.example.gui.skin.Palette;
import com.example.gui.state.GameStateView;
import com.example.gui.widgets.CardActor;
import com.example.gui.widgets.TurnGateOverlay;

import java.util.List;

/**
 * Board rendering built only against GameStateView (Handoff Point #2) -
 * never touches com.example.game.GameState directly, so this keeps
 * working once Person B's real implementation replaces DemoGameStateView.
 * Hosts the TurnGateOverlay per Handoff Point #1.
 */
public class GameScreen extends AbstractScreen {

    private final GameStateView stateView;
    private final Runnable advanceTurnDemo;
    private final TurnGateOverlay turnGate;
    private final Label turnLabel;
    private final Label[] opponentLabels;
    private final Label[] scoreLabels;

    public GameScreen(HeartsGame game, GameStateView stateView, Runnable advanceTurnDemo) {
        super(game);
        this.stateView = stateView;
        this.advanceTurnDemo = advanceTurnDemo;

        Label title = new Label("Hearts", skin, "title");
        turnLabel = new Label("", skin);

        Table opponentsRow = new Table();
        opponentsRow.defaults().pad(6f).width(160f);
        opponentLabels = new Label[stateView.getPlayerCount()];
        for (int i = 1; i < stateView.getPlayerCount(); i++) {
            Label label = new Label("", skin);
            opponentLabels[i] = label;
            opponentsRow.add(label);
        }

        Table trickRow = new Table();
        trickRow.defaults().pad(6f);
        List<Card> trickCards = stateView.getCurrentTrickCards();
        List<Integer> trickPlayers = stateView.getCurrentTrickPlayers();
        if (trickCards.isEmpty()) {
            trickRow.add(new Label("No cards played yet", skin));
        } else {
            for (int i = 0; i < trickCards.size(); i++) {
                String cardLabel = stateView.getPlayerName(trickPlayers.get(i)) + "'s card";
                trickRow.add(new CardActor(trickCards.get(i), cardLabel, skin)).size(100f, 60f);
            }
        }

        HorizontalGroup handGroup = new HorizontalGroup();
        handGroup.space(6f);
        handGroup.wrap(true);
        handGroup.rowAlign(Align.center);
        List<Card> hand = stateView.getPlayerHand(0);
        for (int i = 0; i < hand.size(); i++) {
            handGroup.addActor(new CardActor(hand.get(i), "Card " + (i + 1), skin));
        }

        Table scoresRow = new Table();
        scoresRow.defaults().pad(6f).width(160f);
        scoreLabels = new Label[stateView.getPlayerCount()];
        for (int i = 0; i < stateView.getPlayerCount(); i++) {
            Label label = new Label("", skin);
            scoreLabels[i] = label;
            scoresRow.add(label);
        }

        TextButton nextTurnButton = new TextButton("Next Turn (demo)", skin);
        nextTurnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                advanceTurnDemo.run();
                refreshTurnState();
            }
        });

        TextButton endRoundButton = new TextButton("End Round", skin);
        endRoundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showRoundEnd();
            }
        });

        Table buttonRow = new Table();
        buttonRow.defaults().pad(6f);
        buttonRow.add(nextTurnButton).width(220f).height(50f);
        buttonRow.add(endRoundButton).width(220f).height(50f);

        root.defaults().pad(8f);
        root.add(title).padBottom(6f).row();
        root.add(opponentsRow).row();
        root.add(turnLabel).padTop(6f).row();
        root.add(trickRow).padTop(6f).row();
        root.add(handGroup).width(VIRTUAL_WIDTH - 100f).padTop(6f).row();
        root.add(scoresRow).padTop(6f).row();
        root.add(buttonRow).padTop(6f);

        turnGate = new TurnGateOverlay(skin);
        stage.addActor(turnGate);

        refreshTurnState();
    }

    private void refreshTurnState() {
        int current = stateView.getCurrentPlayerIndex();
        boolean localTurn = current == 0;
        turnLabel.setText(localTurn ? "Your turn!" : stateView.getPlayerName(current) + "'s turn");
        turnLabel.setColor(localTurn ? Palette.YOUR_TURN : Palette.TEXT_ON_DARK);

        for (int i = 1; i < stateView.getPlayerCount(); i++) {
            opponentLabels[i].setText(stateView.getPlayerName(i) + ": " + stateView.getHandSize(i) + " cards");
        }
        for (int i = 0; i < stateView.getPlayerCount(); i++) {
            scoreLabels[i].setText(stateView.getPlayerName(i) + ": " + stateView.getPlayerScore(i));
        }

        turnGate.showFor(stateView.getPlayerName(current));
    }
}
