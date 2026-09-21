package com.example.gui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.example.card.Card;
import com.example.gui.HeartsGame;
import com.example.gui.state.GameActions;
import com.example.gui.state.GameStateView;
import com.example.gui.widgets.CardActor;
import com.example.gui.widgets.TableBoard;
import com.example.gui.widgets.TurnGateOverlay;

import java.util.List;

/**
 * Four people on one laptop: the table is always drawn from the point of
 * view of whoever's turn it is, and every turn starts behind a gate so the
 * previous player's hand isn't visible. Clicking a card asks the game
 * logic to play it (GameActions.playCard); the screen then re-reads
 * GameStateView. Handoff Points #1 and #2 in hearts-gui-decisions.md.
 */
public class GameScreen extends AbstractScreen {

    private final GameStateView stateView;
    private final GameActions actions;
    private final TurnGateOverlay turnGate;
    private Label invalidMoveLabel;

    public GameScreen(HeartsGame game, GameStateView stateView, GameActions actions) {
        super(game);
        this.stateView = stateView;
        this.actions = actions;

        turnGate = new TurnGateOverlay(skin);
        stage.addActor(turnGate);

        showTurn();
    }

    /** Rebuilds the table for whoever's turn it is now and gates it until they tap. */
    private void showTurn() {
        int current = stateView.getCurrentPlayerIndex();
        String name = stateView.getPlayerName(current);

        root.clear();
        TableBoard board = new TableBoard(skin, stateView, current);
        board.setHighlighted(current);

        for (CardActor card : board.getHandCards()) {
            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onCardClicked(card, current);
                }
            });
        }

        Label turnLabel = new Label(name + ", play a card", skin, "gold");
        invalidMoveLabel = new Label("", skin, "invalid");

        TextButton endRoundButton = new TextButton("End Round (demo)", skin);
        endRoundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showRoundEnd();
            }
        });

        Table center = board.getCenter();
        center.add(turnLabel).padBottom(14f).row();
        center.add(buildTrick()).padBottom(8f).row();
        center.add(invalidMoveLabel).height(30f).padBottom(8f).row();
        center.add(endRoundButton).width(240f).height(50f);

        root.add(board).grow().pad(8f);
        turnGate.show(name + "'s turn");
    }

    private Table buildTrick() {
        Table trick = new Table();
        trick.defaults().pad(6f);
        List<Card> cards = stateView.getCurrentTrickCards();
        List<Integer> players = stateView.getCurrentTrickPlayers();
        if (cards.isEmpty()) {
            trick.add(new Label("No cards played yet", skin)).height(126f);
        } else {
            for (int i = 0; i < cards.size(); i++) {
                String playedBy = stateView.getPlayerName(players.get(i));
                trick.add(new CardActor(cards.get(i), playedBy, skin)).size(100f, 126f);
            }
        }
        return trick;
    }

    private void onCardClicked(CardActor clicked, int player) {
        if (actions.playCard(player, clicked.getCard())) {
            showTurn();
        } else {
            invalidMoveLabel.setText("That card can't be played.");
        }
    }
}
