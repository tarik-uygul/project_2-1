package com.example.gui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.example.gui.HeartsGame;

public class SetupScreen extends AbstractScreen {

    public SetupScreen(HeartsGame game) {
        super(game);

        Label title = new Label("Setup", skin, "title");
        Label prompt = new Label("Enter your name", skin);

        TextField nameField = new TextField(game.getSession().getPlayerName(), skin);
        nameField.setMaxLength(20);
        nameField.setAlignment(Align.center);

        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.setDisabled(nameField.getText().trim().isEmpty());

        nameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                confirmButton.setDisabled(nameField.getText().trim().isEmpty());
            }
        });

        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (confirmButton.isDisabled()) {
                    return;
                }
                game.getSession().setPlayerName(nameField.getText().trim());
                game.showPassing();
            }
        });

        root.defaults().pad(10f);
        root.add(title).padBottom(30f).row();
        root.add(prompt).row();
        root.add(nameField).width(300f).height(48f).padBottom(20f).row();
        root.add(confirmButton).width(220f).height(60f);
    }
}
