package com.example.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.gui.HeartsGame;

/**
 * Structure/logic per hearts-gui-decisions.md; real title art is Person
 * A's asset (placeholder text label until it lands).
 */
public class WelcomeScreen extends AbstractScreen {

    public WelcomeScreen(HeartsGame game) {
        super(game);

        Label title = new Label("Hearts", skin, "title");

        TextButton startButton = new TextButton("Start", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showSetup();
            }
        });

        TextButton howToPlayButton = new TextButton("How to Play", skin);
        howToPlayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHowToPlay();
            }
        });

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        root.defaults().pad(10f);
        root.add(title).padBottom(40f).row();
        root.add(startButton).width(220f).height(60f).row();
        root.add(howToPlayButton).width(220f).height(60f).row();
        root.add(exitButton).width(220f).height(60f);
    }

    private void showHowToPlay() {
        Dialog dialog = new Dialog("How to Play", skin) {
            @Override
            protected void result(Object object) {
                hide();
            }
        };
        dialog.text(
            "Hearts is played in tricks. Follow suit if you can.\n"
                + "Avoid taking Hearts and the Queen of Spades -\n"
                + "each Heart is 1 point, the Queen is 13.\n"
                + "Lowest score when someone reaches 100 wins.");
        dialog.button("Got it", true);
        dialog.show(stage);
    }
}
