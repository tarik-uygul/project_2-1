package com.example.gui.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.List;

/**
 * The local player's hand: cards overlapped left to right so only a strip
 * of each shows (the last one in full), with selected cards raised. Cards
 * added later draw - and take clicks - on top, so each visible strip
 * belongs to the card it shows.
 */
public class OverlapHand extends Group {

    private final float selectionLift;

    public OverlapHand(List<CardActor> cards, float cardWidth, float cardHeight, float step, float selectionLift) {
        this.selectionLift = selectionLift;
        for (int i = 0; i < cards.size(); i++) {
            CardActor card = cards.get(i);
            card.setSize(cardWidth, cardHeight);
            card.setPosition(i * step, 0f);
            addActor(card);
        }
        float span = cards.isEmpty() ? 0f : step * (cards.size() - 1) + cardWidth;
        setSize(span, cardHeight + selectionLift);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (int i = 0; i < getChildren().size; i++) {
            CardActor card = (CardActor) getChildren().get(i);
            card.setY(card.isSelected() ? selectionLift : 0f);
        }
    }
}
