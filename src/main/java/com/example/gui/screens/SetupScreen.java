package com.example.gui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.example.gui.HeartsGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Four players share the laptop, so this collects four distinct, non-empty names. */
public class SetupScreen extends AbstractScreen {

    private static final int PLAYERS = 4;

    private final TextField[] nameFields = new TextField[PLAYERS];
    private final TextButton confirmButton;

    public SetupScreen(HeartsGame game) {
        super(game);

        Label title = new Label("Setup", skin, "title");
        Label prompt = new Label("Enter the names of the four players", skin);

        confirmButton = new TextButton("Confirm", skin);
        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (confirmButton.isDisabled()) {
                    return;
                }
                game.getSession().setPlayerNames(currentNames());
                game.showPassing();
            }
        });

        Table fields = new Table();
        fields.defaults().pad(6f);
        List<String> saved = game.getSession().getPlayerNames();
        for (int i = 0; i < PLAYERS; i++) {
            TextField field = new TextField(saved.get(i), skin);
            field.setMaxLength(16);
            field.setAlignment(Align.center);
            field.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateConfirm();
                }
            });
            nameFields[i] = field;
            fields.add(new Label("Player " + (i + 1), skin)).right().padRight(16f);
            fields.add(field).width(300f).height(48f).row();
        }
        updateConfirm();

        root.defaults().pad(10f);
        root.add(title).padBottom(20f).row();
        root.add(prompt).padBottom(10f).row();
        root.add(fields).padBottom(20f).row();
        root.add(confirmButton).width(220f).height(60f);
    }

    private List<String> currentNames() {
        List<String> names = new ArrayList<>();
        for (TextField field : nameFields) {
            names.add(field.getText().trim());
        }
        return names;
    }

    /** Confirm needs four filled-in names that differ, since each one is shown on a "pass the laptop" gate. */
    private void updateConfirm() {
        Set<String> distinct = new HashSet<>();
        boolean valid = true;
        for (String name : currentNames()) {
            if (name.isEmpty() || !distinct.add(name.toLowerCase())) {
                valid = false;
            }
        }
        confirmButton.setDisabled(!valid);
    }
}
