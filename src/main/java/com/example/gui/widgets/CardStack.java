package com.example.gui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.example.gui.skin.Palette;

/**
 * An opponent's hidden hand: `count` overlapped card backs in a row or
 * column. Placeholder look until Person A's card_back.png exists.
 */
public class CardStack extends Actor {

    private static final float INSET = 4f;

    private final boolean horizontal;
    private final float cardWidth;
    private final float cardHeight;
    private final float step;
    private final Drawable outer;
    private final Drawable inner;
    private int count;

    public CardStack(Skin skin, boolean horizontal, float cardWidth, float cardHeight, float step) {
        this.horizontal = horizontal;
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.step = step;
        outer = skin.newDrawable("white", Palette.TEXT_ON_DARK);
        inner = skin.newDrawable("white", Palette.TABLE_BACKGROUND.cpy().lerp(Palette.TEXT_ON_DARK, 0.2f));
        setCount(0);
    }

    public void setCount(int count) {
        this.count = count;
        float span = count > 0 ? step * (count - 1) : 0f;
        if (horizontal) {
            setSize(count > 0 ? span + cardWidth : 0f, cardHeight);
        } else {
            setSize(cardWidth, count > 0 ? span + cardHeight : 0f);
        }
        if (getParent() instanceof Layout) {
            ((Layout) getParent()).invalidateHierarchy();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        for (int i = 0; i < count; i++) {
            float x = horizontal ? getX() + i * step : getX();
            float y = horizontal ? getY() : getY() + getHeight() - cardHeight - i * step;
            outer.draw(batch, x, y, cardWidth, cardHeight);
            inner.draw(batch, x + INSET, y + INSET, cardWidth - 2 * INSET, cardHeight - 2 * INSET);
        }
    }
}
