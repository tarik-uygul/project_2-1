package com.example.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.example.gui.screens.AbstractScreen;
import com.example.gui.screens.GameOverScreen;
import com.example.gui.screens.GameScreen;
import com.example.gui.screens.PassingScreen;
import com.example.gui.screens.RoundEndScreen;
import com.example.gui.screens.SetupScreen;
import com.example.gui.screens.WelcomeScreen;
import com.example.gui.skin.SkinFactory;
import com.example.gui.state.DemoGameStateView;
import com.example.gui.state.GuiSession;

/**
 * Owns the shared SpriteBatch/Skin and the screen-order state machine:
 * Welcome -> Setup -> Passing -> Game -> RoundEnd -> (loop to Game, or
 * GameOver) -> Play Again -> Welcome. Screens call the show*() methods
 * here rather than knowing about each other directly.
 */
public class HeartsGame extends Game {

    private SpriteBatch batch;
    private Skin skin;
    private GuiSession session;

    /**
     * Placeholder GameStateView shared across Passing/Game/RoundEnd/GameOver
     * for one demo run, until com.example.game.GameState implements the
     * real thing. Created fresh each time Passing is entered.
     */
    private DemoGameStateView demoState;

    @Override
    public void create() {
        batch = new SpriteBatch();
        skin = SkinFactory.createSkin();
        session = new GuiSession();
        showWelcome();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Skin getSkin() {
        return skin;
    }

    public GuiSession getSession() {
        return session;
    }

    public void showWelcome() {
        setScreen(new WelcomeScreen(this));
    }

    public void showSetup() {
        setScreen(new SetupScreen(this));
    }

    public void showPassing() {
        demoState = new DemoGameStateView(session.getPlayerName());
        setScreen(new PassingScreen(this, demoState));
    }

    public void showGame() {
        setScreen(new GameScreen(this, demoState, demoState::advanceTurn));
    }

    public void showRoundEnd() {
        setScreen(new RoundEndScreen(this, demoState));
    }

    public void showGameOver() {
        setScreen(new GameOverScreen(this, demoState));
    }

    /**
     * Cross-fades between screens (Phase 3: §"Screen transition animations"
     * in hearts-gui-decisions.md) whenever both the outgoing and incoming
     * screen are AbstractScreens; otherwise swaps immediately (e.g. the
     * very first screen at startup, where there's nothing to fade from).
     */
    @Override
    public void setScreen(Screen screen) {
        Screen previous = getScreen();
        if (previous instanceof AbstractScreen && screen instanceof AbstractScreen) {
            AbstractScreen from = (AbstractScreen) previous;
            AbstractScreen to = (AbstractScreen) screen;
            super.setScreen(AbstractScreen.transitionTo(from, to, () -> super.setScreen(to)));
        } else {
            super.setScreen(screen);
            if (previous != null) {
                previous.dispose();
            }
        }
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        batch.dispose();
        skin.dispose();
    }
}
