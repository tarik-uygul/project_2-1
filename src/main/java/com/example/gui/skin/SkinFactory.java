package com.example.gui.skin;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Builds the Skin every screen shares, styled to the felt-table palette
 * in hearts-gui-decisions.md (§2, §3, §6). Team decision: stay
 * code-generated (solid-color drawables) rather than importing a premade
 * atlas, so there's no external asset pack to license/maintain.
 */
public final class SkinFactory {

    private SkinFactory() {}

    public static Skin createSkin() {
        Skin skin = new Skin();

        skin.add("white", solidPixmapTexture());

        BitmapFont titleFont = FontFactory.createTitleFont();
        BitmapFont bodyFont = FontFactory.createBodyFont();
        skin.add("title-font", titleFont);
        skin.add("body-font", bodyFont);

        BitmapFont headingFont = FontFactory.createHeadingFont();
        skin.add("heading-font", headingFont);

        skin.add("title", new Label.LabelStyle(titleFont, Palette.TEXT_ON_DARK));
        skin.add("default", new Label.LabelStyle(bodyFont, Palette.TEXT_ON_DARK));

        // Highlight for whoever's turn it is (the palette's "your turn" gold).
        skin.add("gold", new Label.LabelStyle(bodyFont, Palette.YOUR_TURN));

        skin.add("invalid", new Label.LabelStyle(bodyFont, Palette.INVALID_MOVE));

        // Dark variants for text sitting on the light (TEXT_ON_DARK) panels,
        // where the default light label color would be invisible.
        skin.add("title-dark", new Label.LabelStyle(titleFont, Palette.TABLE_BACKGROUND));
        skin.add("heading-dark", new Label.LabelStyle(headingFont, Palette.TABLE_BACKGROUND));
        skin.add("dark", new Label.LabelStyle(bodyFont, Palette.TABLE_BACKGROUND));

        // Primary buttons use gold ("your turn" in the palette) as the
        // call-to-action accent - there's no separate "primary action" color,
        // and gold already reads as emphasis, which fits Confirm/Start buttons.
        // The disabled look reuses "invalid move" red, so a blocked Confirm
        // (e.g. PassingScreen before exactly 3 cards are picked) reads the
        // same as an invalid move anywhere else in the game.
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("white", Palette.TEXT_ON_DARK.cpy().mul(0.85f, 0.85f, 0.8f, 1f));
        buttonStyle.down = skin.newDrawable("white", Palette.YOUR_TURN);
        buttonStyle.over = skin.newDrawable("white", Palette.YOUR_TURN.cpy().lerp(Color.WHITE, 0.3f));
        buttonStyle.disabled = skin.newDrawable("white", Palette.INVALID_MOVE.cpy().mul(1f, 1f, 1f, 0.5f));
        buttonStyle.font = bodyFont;
        buttonStyle.fontColor = Palette.TABLE_BACKGROUND;
        buttonStyle.disabledFontColor = Palette.TEXT_ON_DARK.cpy().mul(1f, 1f, 1f, 0.7f);
        skin.add("default", buttonStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bodyFont;
        textFieldStyle.fontColor = Palette.TABLE_BACKGROUND;
        textFieldStyle.background = skin.newDrawable("white", Palette.TEXT_ON_DARK);
        textFieldStyle.cursor = skin.newDrawable("white", Palette.TABLE_BACKGROUND);
        textFieldStyle.selection = skin.newDrawable("white", Palette.SELECTED_CARD.cpy().mul(1f, 1f, 1f, 0.6f));
        skin.add("default", textFieldStyle);

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.vScroll = skin.newDrawable("white", Palette.TEXT_ON_DARK.cpy().mul(0.85f, 0.85f, 0.8f, 1f));
        scrollPaneStyle.vScrollKnob = skin.newDrawable("white", Palette.TABLE_BACKGROUND);
        scrollPaneStyle.vScroll.setMinWidth(10f);
        scrollPaneStyle.vScrollKnob.setMinWidth(10f);
        skin.add("default", scrollPaneStyle);

        return skin;
    }

    private static Texture solidPixmapTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
