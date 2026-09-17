package com.example.gui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.gui.HeartsGame;
import com.example.gui.state.GameStateView;

/**
 * Score summary after a round. The branch (continue vs. finish match) is
 * two buttons for now - once Person B's logic can say whether the match
 * is actually over (e.g. someone crossed 100 points), that replaces the
 * "Finish Match" button with a real check.
 */
public class RoundEndScreen extends AbstractScreen {

    public RoundEndScreen(HeartsGame game, GameStateView stateView) {
        super(game);

        Label title = new Label("Round End", skin, "title");

        Table scoreTable = new Table();
        scoreTable.defaults().pad(6f);
        for (int i = 0; i < stateView.getPlayerCount(); i++) {
            scoreTable.add(new Label(stateView.getPlayerName(i), skin)).left();
            scoreTable.add(new Label(String.valueOf(stateView.getPlayerScore(i)), skin)).right().row();
        }

        TextButton nextRoundButton = new TextButton("Next Round", skin);
        nextRoundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showGame();
            }
        });

        TextButton finishMatchButton = new TextButton("Finish Match", skin);
        finishMatchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showGameOver();
            }
        });

        root.defaults().pad(10f);
        root.add(title).colspan(2).padBottom(20f).row();
        root.add(scoreTable).colspan(2).padBottom(30f).row();
        root.add(nextRoundButton).width(220f).height(60f).padRight(10f);
        root.add(finishMatchButton).width(220f).height(60f);
    }
}
