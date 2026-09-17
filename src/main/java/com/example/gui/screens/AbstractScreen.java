package com.example.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.example.gui.HeartsGame;
import com.example.gui.skin.Palette;

/**
 * Base for every screen: owns the Stage/viewport/root Table boilerplate
 * (§4 of hearts-gui-decisions.md — everything built against a fixed
 * 1280x720 virtual resolution via FitViewport) so subclasses only add
 * their own widgets to `root`.
 */
public abstract class AbstractScreen implements Screen {

    public static final float VIRTUAL_WIDTH = 1280f;
    public static final float VIRTUAL_HEIGHT = 720f;

    protected final HeartsGame game;
    protected final Stage stage;
    protected final Skin skin;
    protected final Table root;

    protected AbstractScreen(HeartsGame game) {
        this.game = game;
        this.skin = game.getSkin();
        this.stage = new Stage(new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT), game.getBatch());
        this.root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        clear();
        act(delta);
        draw();
    }

    /**
     * Split out of render() so TransitionScreen can clear once and then
     * act/draw the outgoing and incoming screens back to back in the same
     * frame, cross-fading via setContentAlpha() instead of each screen
     * clearing and overwriting the other.
     */
    void clear() {
        Gdx.gl.glClearColor(Palette.TABLE_BACKGROUND.r, Palette.TABLE_BACKGROUND.g,
            Palette.TABLE_BACKGROUND.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    void act(float delta) {
        stage.act(Math.min(delta, 1 / 30f));
    }

    void draw() {
        stage.draw();
    }

    /** Alpha applied to the whole screen's content, used by TransitionScreen for fading. */
    void setContentAlpha(float alpha) {
        root.getColor().a = alpha;
    }

    /** Entry point for HeartsGame.setScreen() to cross-fade between two screens. */
    public static Screen transitionTo(AbstractScreen from, AbstractScreen to, Runnable onComplete) {
        return new TransitionScreen(from, to, onComplete);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
