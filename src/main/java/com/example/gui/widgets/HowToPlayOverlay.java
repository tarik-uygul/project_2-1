package com.example.gui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.gui.skin.Palette;

/**
 * Scrollable rules panel over a dimmed backdrop. Text is ASCII-only on
 * purpose: suit symbols and emoji aren't in the FreeType glyph set, so
 * suits are written out as words.
 */
public class HowToPlayOverlay extends Table {

    private static final float PANEL_WIDTH = 940f;
    private static final float PANEL_HEIGHT = 620f;
    private static final float CONTENT_WIDTH = 840f;

    private final Skin skin;
    private final Table content = new Table();

    public HowToPlayOverlay(Skin skin) {
        this.skin = skin;

        setFillParent(true);
        setTouchable(Touchable.enabled);
        setBackground(skin.newDrawable("white", Palette.TABLE_BACKGROUND.cpy().mul(1f, 1f, 1f, 0.85f)));

        buildContent();

        ScrollPane scrollPane = new ScrollPane(content, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        TextButton closeButton = new TextButton("Got it", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false);
            }
        });

        Table panel = new Table();
        panel.setBackground(skin.newDrawable("white", Palette.TEXT_ON_DARK));
        panel.pad(24f);
        panel.add(scrollPane).grow().row();
        panel.add(closeButton).width(220f).height(50f).padTop(16f);

        add(panel).size(PANEL_WIDTH, PANEL_HEIGHT);
        setVisible(false);
    }

    public void show() {
        setVisible(true);
        toFront();
    }

    private void buildContent() {
        Label title = new Label("Hearts: How to Play", skin, "title-dark");
        content.add(title).left().padBottom(8f).row();

        heading("Overview");
        paragraph("Hearts is a trick-taking card game. The goal is to finish with as few points as possible. "
            + "Avoid taking hearts and the Queen of Spades.");

        heading("Setup");
        bullet("Players: 4 (optional rules for 3 and 5 players are at the end)");
        bullet("Deck: standard 52 cards, jokers removed");
        bullet("Deal: 13 cards each");
        bullet("Hands are private. Sort your cards by suit for a better overview.");

        heading("Scoring Cards");
        Table scoring = new Table();
        scoring.defaults().left().pad(4f).padRight(40f);
        scoring.add(new Label("Card", skin, "heading-dark"));
        scoring.add(new Label("Points", skin, "heading-dark")).row();
        scoring.add(new Label("Each heart", skin, "dark"));
        scoring.add(new Label("1", skin, "dark")).row();
        scoring.add(new Label("Queen of Spades", skin, "dark"));
        scoring.add(new Label("13", skin, "dark")).row();
        scoring.add(new Label("All other cards", skin, "dark"));
        scoring.add(new Label("0", skin, "dark")).row();
        content.add(scoring).left().padTop(4f).row();

        heading("Passing Cards");
        paragraph("Before each round, choose three cards you don't want (dodge high cards and dangerous ones) "
            + "and pass them face down. The direction changes every round:");
        bullet("Left");
        bullet("Right");
        bullet("Across");
        bullet("No passing");
        paragraph("Then the cycle repeats.");
        paragraph("Important: Don't look at the cards passed to you until you have passed your own.");

        heading("Playing a Round");
        paragraph("A round consists of tricks, one card from each player per trick.");
        bullet("The player holding the 2 of clubs leads the first trick.");
        bullet("Play proceeds clockwise.");
        bullet("Follow suit if you can. The ace is the highest card.");
        bullet("If you can't follow suit, you may play any card. This is a good moment to dump a high card "
            + "or a penalty card (a heart or the Queen of Spades).");
        bullet("The highest card of the led suit wins the trick. That player takes the cards and adds them "
            + "to their own pile, face down so nobody can see the numbers.");
        bullet("The winner of a trick leads the next one.");

        heading("First Trick");
        paragraph("No penalty cards may be played on the first trick. If you can't follow suit, "
            + "you still can't play a heart or the Queen of Spades.");

        heading("Breaking Hearts");
        paragraph("You can't lead with a heart until hearts have been \"broken\". "
            + "This happens in one of two ways:");
        bullet("A player can't follow suit and plays a heart.");
        bullet("A player leads a heart because they have only hearts in their hand.");

        heading("Misdeal");
        paragraph("If a player's starting hand contains only penalty cards, the cards are reshuffled and redealt.");

        heading("Scoring");
        paragraph("After each round, everyone counts the points in the tricks they took.");

        heading("Shooting the Moon");
        paragraph("If one player takes all the hearts and the Queen of Spades, they score 0 "
            + "and every other player gets 26 points.");

        heading("Winning");
        paragraph("The game ends when a player reaches 100 points. The player with the lowest score wins.");

        heading("Variants");
        subHeading("3 Players");
        bullet("Remove the 2 of diamonds.");
        bullet("Deal 17 cards each.");
        bullet("When passing, skip the \"across\" round.");
        subHeading("5 Players");
        bullet("Remove the 2 of clubs and the 2 of diamonds.");
        bullet("Deal 10 cards each.");
        bullet("Pass only two cards. The rotation is: left, right, second to the left, "
            + "second to the right, no pass.");
    }

    private void heading(String text) {
        content.add(new Label(text, skin, "heading-dark")).left().padTop(20f).padBottom(4f).row();
    }

    private void subHeading(String text) {
        content.add(new Label(text, skin, "heading-dark")).left().padTop(10f).padBottom(2f).row();
    }

    private void paragraph(String text) {
        content.add(wrapped(text)).width(CONTENT_WIDTH).left().padTop(4f).row();
    }

    private void bullet(String text) {
        content.add(wrapped("- " + text)).width(CONTENT_WIDTH).left().padTop(4f).padLeft(12f).row();
    }

    private Label wrapped(String text) {
        Label label = new Label(text, skin, "dark");
        label.setWrap(true);
        return label;
    }
}
