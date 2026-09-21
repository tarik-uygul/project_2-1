package com.example.gui.widgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.example.gui.skin.Palette;

/**
 * "Player X - tap to reveal" gate for passing the laptop between players.
 * Per Handoff Point #1 in hearts-gui-decisions.md this is an overlay added
 * directly to a screen's Stage, not a separate Screen. It is fully opaque:
 * the previous player's cards must not show through to the next player.
 */
public class TurnGateOverlay extends Table {

    private final Label messageLabel;

    public TurnGateOverlay(Skin skin) {
        setFillParent(true);
        setTouchable(Touchable.enabled);
        setBackground(skin.newDrawable("white", Palette.TABLE_BACKGROUND));

        messageLabel = new Label("", skin, "title");
        messageLabel.setColor(Palette.YOUR_TURN);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);

        add(messageLabel).width(1000f).padBottom(24f).row();
        add(new Label("Tap anywhere to reveal", skin));

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });

        setVisible(false);
    }

    public void show(String message) {
        messageLabel.setText(message);
        setVisible(true);
        toFront();
    }
}
