package com.example.gui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.example.card.Card;
import com.example.gui.HeartsGame;
import com.example.gui.state.GameStateView;
import com.example.gui.widgets.CardActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Card-selection UI reusing the shared CardActor component (placeholder
 * for Person A's real one, Handoff Point #3) - this screen only owns the
 * selection logic: tap to select/deselect, enforce exactly 3, confirm.
 */
public class PassingScreen extends AbstractScreen {

    private static final int REQUIRED_SELECTION = 3;

    public PassingScreen(HeartsGame game, GameStateView stateView) {
        super(game);

        Label title = new Label("Choose 3 Cards to Pass", skin, "title");

        List<CardActor> cardActors = new ArrayList<>();
        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.setDisabled(true);

        HorizontalGroup handGroup = new HorizontalGroup();
        handGroup.space(8f);
        handGroup.wrap(true);
        handGroup.rowAlign(Align.center);

        List<Card> hand = stateView.getPlayerHand(0);
        for (int i = 0; i < hand.size(); i++) {
            CardActor cardActor = new CardActor(hand.get(i), "Card " + (i + 1), skin);
            cardActor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onCardClicked(cardActor, cardActors, confirmButton);
                }
            });
            cardActors.add(cardActor);
            handGroup.addActor(cardActor);
        }

        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (confirmButton.isDisabled()) {
                    return;
                }
                game.showGame();
            }
        });

        root.defaults().pad(10f);
        root.add(title).padBottom(20f).row();
        root.add(handGroup).width(VIRTUAL_WIDTH - 100f).row();
        root.add(confirmButton).width(220f).height(60f).padTop(20f);
    }

    private void onCardClicked(CardActor clicked, List<CardActor> all, TextButton confirmButton) {
        if (!clicked.isSelected() && countSelected(all) >= REQUIRED_SELECTION) {
            return;
        }
        clicked.setSelected(!clicked.isSelected());
        confirmButton.setDisabled(countSelected(all) != REQUIRED_SELECTION);
    }

    private int countSelected(List<CardActor> all) {
        int count = 0;
        for (CardActor cardActor : all) {
            if (cardActor.isSelected()) {
                count++;
            }
        }
        return count;
    }
}
