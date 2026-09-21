package com.example.gui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.example.card.Card;
import com.example.gui.skin.Palette;
import com.example.gui.state.GameStateView;

import java.util.ArrayList;
import java.util.List;

/**
 * The shared table layout for PassingScreen and GameScreen, drawn from the
 * point of view of `viewer` - whoever is currently sitting at the laptop.
 * The viewer's own hand is overlapped at the bottom; the next players
 * clockwise appear as hidden hands on the left, top and right. The center
 * is left empty for each screen to fill. Assumes a 4-player game.
 */
public class TableBoard extends Table {

    private static final int PLAYERS = 4;
    private static final int BOTTOM = 0;
    private static final int LEFT = 1;
    private static final int TOP = 2;
    private static final int RIGHT = 3;

    private static final float HAND_CARD_WIDTH = 90f;
    private static final float HAND_CARD_HEIGHT = 126f;
    private static final float HAND_STEP = 46f;
    private static final float SELECTION_LIFT = 24f;

    private final Skin skin;
    private final GameStateView state;
    private final int viewer;
    private final Table center = new Table();
    private final List<CardActor> handCards = new ArrayList<>();
    private final CardStack[] stacks = new CardStack[PLAYERS];
    private final Label[] nameLabels = new Label[PLAYERS];
    private final Label[] scoreLabels = new Label[PLAYERS];
    private final Label ownScoreLabel;
    private int highlighted = -1;

    public TableBoard(Skin skin, GameStateView state, int viewer) {
        this.skin = skin;
        this.state = state;
        this.viewer = viewer;

        List<Card> hand = state.getPlayerHand(viewer);
        for (int i = 0; i < hand.size(); i++) {
            handCards.add(new CardActor(hand.get(i), String.valueOf(i + 1), skin));
        }
        OverlapHand overlapHand = new OverlapHand(handCards, HAND_CARD_WIDTH, HAND_CARD_HEIGHT,
            HAND_STEP, SELECTION_LIFT);

        for (int i = 0; i < PLAYERS; i++) {
            nameLabels[i] = new Label("", skin);
            scoreLabels[i] = new Label("", skin, "dark");
        }
        ownScoreLabel = new Label("", skin);
        stacks[TOP] = new CardStack(skin, true, 64f, 90f, 26f);
        stacks[LEFT] = new CardStack(skin, false, 90f, 64f, 22f);
        stacks[RIGHT] = new CardStack(skin, false, 90f, 64f, 22f);

        pad(8f);
        add(seat(TOP)).colspan(3).top().row();
        add(seat(LEFT)).width(150f).top().padTop(20f);
        add(center).grow();
        add(seat(RIGHT)).width(150f).top().padTop(20f).row();

        Table ownInfo = new Table();
        ownInfo.add(nameLabels[BOTTOM]).left().row();
        ownInfo.add(ownScoreLabel).left();

        Table bottom = new Table();
        bottom.add(ownInfo).width(180f).left().bottom();
        bottom.add(overlapHand).expandX().bottom();
        bottom.add(buildScoreboard()).right().bottom();
        add(bottom).colspan(3).growX().bottom();

        refresh();
    }

    /** Container each screen fills with its own content (instructions, current trick, buttons). */
    public Table getCenter() {
        return center;
    }

    /** The viewer's card widgets in hand order, for screens that add selection or play behavior. */
    public List<CardActor> getHandCards() {
        return handCards;
    }

    /** Marks one player's name in gold (whose turn it is); -1 for nobody. */
    public void setHighlighted(int playerIndex) {
        highlighted = playerIndex;
        refresh();
    }

    /** Re-reads names, hand sizes and scores from the GameStateView. */
    public void refresh() {
        for (int position = 0; position < PLAYERS; position++) {
            int player = playerAt(position);
            nameLabels[position].setText(state.getPlayerName(player));
            nameLabels[position].setStyle(
                skin.get(player == highlighted ? "gold" : "default", Label.LabelStyle.class));
            if (position != BOTTOM) {
                stacks[position].setCount(state.getHandSize(player));
            }
        }
        for (int player = 0; player < PLAYERS; player++) {
            scoreLabels[player].setText(String.valueOf(state.getPlayerScore(player)));
        }
        ownScoreLabel.setText("Score: " + state.getPlayerScore(viewer));
    }

    /** Who sits at a seat position: bottom is the viewer, then clockwise left, top, right. */
    private int playerAt(int position) {
        return (viewer + position) % PLAYERS;
    }

    private Table seat(int position) {
        Table seat = new Table();
        seat.add(nameLabels[position]).padBottom(6f).row();
        seat.add(stacks[position]);
        return seat;
    }

    private Table buildScoreboard() {
        Table scoreboard = new Table();
        scoreboard.setBackground(skin.newDrawable("white", Palette.TEXT_ON_DARK));
        scoreboard.defaults().pad(6f).padLeft(10f).padRight(10f);
        for (int i = 0; i < PLAYERS; i++) {
            scoreboard.add(new Label(state.getPlayerName(i), skin, "dark"));
        }
        scoreboard.row();
        for (int i = 0; i < PLAYERS; i++) {
            scoreboard.add(scoreLabels[i]);
        }
        return scoreboard;
    }
}
