package com.example.gui.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.example.card.Card;
import com.example.gui.skin.Palette;

/**
 * Placeholder for Person A's real card-rendering component (Handoff Point
 * #3 in hearts-gui-decisions.md): a selectable card slot showing a text
 * label instead of real card art, since Card has no rank/suit accessors
 * yet either. Meant to be swapped for the real component without
 * PassingScreen/GameScreen's selection logic changing - keep the public
 * surface (getCard/isSelected/setSelected) stable when that happens.
 */
public class CardActor extends Table {

    private final Card card;
    private final Drawable normalBackground;
    private final Drawable selectedBackground;
    private final Drawable border;
    private boolean selected = false;

    public CardActor(Card card, String displayLabel, Skin skin) {
        this.card = card;
        normalBackground = skin.newDrawable("white", Palette.TEXT_ON_DARK);
        selectedBackground = skin.newDrawable("white", Palette.SELECTED_CARD);
        border = skin.newDrawable("white", Palette.TABLE_BACKGROUND);

        setBackground(normalBackground);
        setTouchable(Touchable.enabled);
        align(Align.topLeft);

        add(new Label(displayLabel, skin, "dark")).pad(6f);
    }

    /** Outline so overlapped cards in a hand stay distinguishable from each other. */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(1f, 1f, 1f, getColor().a * parentAlpha);
        float x = getX();
        float y = getY();
        float w = getWidth();
        float h = getHeight();
        border.draw(batch, x, y, w, 2f);
        border.draw(batch, x, y + h - 2f, w, 2f);
        border.draw(batch, x, y, 2f, h);
        border.draw(batch, x + w - 2f, y, 2f, h);
    }

    public Card getCard() {
        return card;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        setBackground(selected ? selectedBackground : normalBackground);
    }
}
