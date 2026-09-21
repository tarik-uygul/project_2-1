package com.example.gui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.gui.HeartsGame;
import com.example.gui.skin.Palette;
import com.example.gui.state.GameStateView;

/** Final score summary and winner - lowest score wins in Hearts. */
public class GameOverScreen extends AbstractScreen {

    public GameOverScreen(HeartsGame game, GameStateView stateView) {
        super(game);

        int winnerIndex = 0;
        for (int i = 1; i < stateView.getPlayerCount(); i++) {
            if (stateView.getPlayerScore(i) < stateView.getPlayerScore(winnerIndex)) {
                winnerIndex = i;
            }
        }

        Label title = new Label("Game Over", skin, "title");
        Label winnerLabel = new Label(stateView.getPlayerName(winnerIndex) + " wins!", skin);
        winnerLabel.setColor(Palette.YOUR_TURN);

        Table scoreTable = new Table();
        scoreTable.defaults().pad(6f);
        for (int i = 0; i < stateView.getPlayerCount(); i++) {
            Label nameLabel = new Label(stateView.getPlayerName(i), skin);
            Label scoreLabel = new Label(String.valueOf(stateView.getPlayerScore(i)), skin);
            if (i == winnerIndex) {
                nameLabel.setColor(Palette.YOUR_TURN);
                scoreLabel.setColor(Palette.YOUR_TURN);
            }
            scoreTable.add(nameLabel).left();
            scoreTable.add(scoreLabel).right().row();
        }

        TextButton playAgainButton = new TextButton("Play Again", skin);
        playAgainButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showWelcome();
            }
        });

        root.defaults().pad(10f);
        root.add(title).padBottom(10f).row();
        root.add(winnerLabel).padBottom(20f).row();
        root.add(scoreTable).padBottom(30f).row();
        root.add(playAgainButton).width(220f).height(60f);
    }

    @Override
    boolean slidesInFlow() {
        return false;
    }
}
