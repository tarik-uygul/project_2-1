package com.example.gui.widgets;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
    private boolean selected = false;

    public CardActor(Card card, String displayLabel, Skin skin) {
        this.card = card;
        normalBackground = skin.newDrawable("white", Palette.TEXT_ON_DARK);
        selectedBackground = skin.newDrawable("white", Palette.SELECTED_CARD);

        setBackground(normalBackground);
        setTouchable(Touchable.enabled);

        Label label = new Label(displayLabel, skin);
        label.setColor(Palette.TABLE_BACKGROUND);
        add(label).pad(8f);
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
