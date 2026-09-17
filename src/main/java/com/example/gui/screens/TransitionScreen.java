package com.example.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Cross-fades from one AbstractScreen to another over `duration` seconds,
 * then hands off to the target screen permanently. Every screen is
 * already Scene2D, so this just renders both Stages in the same frame
 * with opposite alpha ramps (via AbstractScreen.setContentAlpha) rather
 * than capturing pixels to an FBO.
 */
class TransitionScreen implements Screen {

    private static final float DEFAULT_DURATION = 0.35f;

    private final AbstractScreen from;
    private final AbstractScreen to;
    private final float duration;
    private final Runnable onComplete;
    private float elapsed = 0f;

    TransitionScreen(AbstractScreen from, AbstractScreen to, Runnable onComplete) {
        this(from, to, DEFAULT_DURATION, onComplete);
    }

    TransitionScreen(AbstractScreen from, AbstractScreen to, float duration, Runnable onComplete) {
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.onComplete = onComplete;
        to.setContentAlpha(0f);
    }

    @Override
    public void render(float delta) {
        elapsed += delta;
        float t = Math.min(elapsed / duration, 1f);

        from.setContentAlpha(1f - t);
        to.setContentAlpha(t);

        from.clear();
        from.act(delta);
        from.draw();
        to.act(delta);
        to.draw();

        if (t >= 1f) {
            from.dispose();
            onComplete.run();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        from.resize(width, height);
        to.resize(width, height);
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
    }
}
