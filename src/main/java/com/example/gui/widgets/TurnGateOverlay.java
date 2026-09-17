package com.example.gui.widgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.example.gui.skin.Palette;

/**
 * "Player X's turn - tap to reveal" gate. Per Handoff Point #1 in
 * hearts-gui-decisions.md this is an overlay added directly to
 * GameScreen's Stage, not a separate Screen - GameScreen keeps running
 * underneath and this just blocks the view (and, once visible is false,
 * blocks nothing) until tapped.
 */
public class TurnGateOverlay extends Table {

    private final Label messageLabel;

    public TurnGateOverlay(Skin skin) {
        setFillParent(true);
        setBackground(skin.newDrawable("white", Palette.TABLE_BACKGROUND.cpy().mul(1f, 1f, 1f, 0.92f)));

        messageLabel = new Label("", skin, "title");
        messageLabel.setColor(Palette.YOUR_TURN);
        add(messageLabel);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });

        setVisible(false);
    }

    public void showFor(String playerName) {
        messageLabel.setText(playerName + "'s turn - tap to reveal");
        setVisible(true);
        toFront();
    }
}
