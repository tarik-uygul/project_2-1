package com.example.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.gui.HeartsGame;
import com.example.gui.widgets.HowToPlayOverlay;

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
        HowToPlayOverlay howToPlay = new HowToPlayOverlay(skin);
        howToPlayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                howToPlay.show();
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

        stage.addActor(howToPlay);
    }

}
