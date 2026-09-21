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
import com.example.gui.skin.Palette;
import com.example.gui.state.GameActions;
import com.example.gui.state.GameStateView;
import com.example.gui.widgets.CardActor;
import com.example.gui.widgets.TableBoard;
import com.example.gui.widgets.TurnGateOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * The passing phase for four people on one laptop. Phase PASS: each player
 * in turn gets a gate ("pass the laptop"), then picks exactly 3 cards and
 * confirms. Phase RECEIVE: once everyone has passed, each player in turn
 * gets a gate and sees the 3 cards they were passed. Then the game starts.
 * Which cards go where is the game logic's job (GameActions/GameStateView);
 * this screen only sequences the players and owns the selection UI.
 */
public class PassingScreen extends AbstractScreen {

    private static final int REQUIRED_SELECTION = 3;

    private enum Phase { PASS, RECEIVE }

    private final GameStateView stateView;
    private final GameActions actions;
    private final TurnGateOverlay gate;
    private Phase phase = Phase.PASS;
    private int seat = 0;

    public PassingScreen(HeartsGame game, GameStateView stateView, GameActions actions) {
        super(game);
        this.stateView = stateView;
        this.actions = actions;

        gate = new TurnGateOverlay(skin);
        stage.addActor(gate);

        showSeat();
    }

    /** Builds the table from the current player's point of view and gates it until they tap. */
    private void showSeat() {
        root.clear();
        TableBoard board = new TableBoard(skin, stateView, seat);
        if (phase == Phase.PASS) {
            buildPassCenter(board);
        } else {
            buildReceiveCenter(board.getCenter());
        }
        root.add(board).grow().pad(8f);

        String name = stateView.getPlayerName(seat);
        gate.show(phase == Phase.PASS ? name + ": choose your cards to pass" : name + ": see your new cards");
    }

    private void buildPassCenter(TableBoard board) {
        List<CardActor> cards = board.getHandCards();

        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.setDisabled(true);
        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (confirmButton.isDisabled()) {
                    return;
                }
                actions.passCards(seat, selectedCards(cards));
                advance();
            }
        });

        for (CardActor card : cards) {
            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onCardClicked(card, cards, confirmButton);
                }
            });
        }

        String target = stateView.getPlayerName(stateView.getPassTarget(seat));
        Table center = board.getCenter();
        center.add(banner("Select 3 cards to pass to " + target + " and then click Confirm.")).padBottom(24f).row();
        center.add(confirmButton).width(220f).height(60f);
    }

    private void buildReceiveCenter(Table center) {
        int source = -1;
        for (int i = 0; i < stateView.getPlayerCount(); i++) {
            if (stateView.getPassTarget(i) == seat) {
                source = i;
            }
        }
        String sourceName = stateView.getPlayerName(source);

        Table received = new Table();
        received.defaults().pad(6f);
        for (Card card : stateView.getReceivedCards(seat)) {
            received.add(new CardActor(card, sourceName, skin)).size(100f, 126f);
        }

        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                advance();
            }
        });

        center.add(banner("You received these cards from " + sourceName + ".")).padBottom(16f).row();
        center.add(received).padBottom(20f).row();
        center.add(continueButton).width(220f).height(60f);
    }

    /** Moves to the next player; after the last one, PASS becomes RECEIVE and RECEIVE starts the game. */
    private void advance() {
        seat++;
        if (seat == stateView.getPlayerCount()) {
            seat = 0;
            if (phase == Phase.PASS) {
                phase = Phase.RECEIVE;
            } else {
                game.showGame();
                return;
            }
        }
        showSeat();
    }

    private Table banner(String text) {
        Label label = new Label(text, skin, "dark");
        label.setWrap(true);
        Table banner = new Table();
        banner.setBackground(skin.newDrawable("white", Palette.TEXT_ON_DARK));
        banner.pad(14f);
        banner.add(label).width(420f);
        return banner;
    }

    private void onCardClicked(CardActor clicked, List<CardActor> all, TextButton confirmButton) {
        if (!clicked.isSelected() && selectedCards(all).size() >= REQUIRED_SELECTION) {
            return;
        }
        clicked.setSelected(!clicked.isSelected());
        confirmButton.setDisabled(selectedCards(all).size() != REQUIRED_SELECTION);
    }

    private List<Card> selectedCards(List<CardActor> all) {
        List<Card> selected = new ArrayList<>();
        for (CardActor cardActor : all) {
            if (cardActor.isSelected()) {
                selected.add(cardActor.getCard());
            }
        }
        return selected;
    }
}
