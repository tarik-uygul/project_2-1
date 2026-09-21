package com.example.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;

/**
 * Transitions from one AbstractScreen to another, then hands off to the
 * target screen permanently. Every screen is already Scene2D, so this
 * renders both Stages in the same frame - no FBO pixel capture needed.
 * Either cross-fades (opposite alpha ramps) or slides (incoming screen
 * enters from the right while the outgoing one leaves to the left).
 */
class TransitionScreen implements Screen {

    private static final float FADE_DURATION = 0.35f;
    private static final float SLIDE_DURATION = 0.4f;

    private final AbstractScreen from;
    private final AbstractScreen to;
    private final boolean slide;
    private final float duration;
    private final Runnable onComplete;
    private float elapsed = 0f;

    TransitionScreen(AbstractScreen from, AbstractScreen to, boolean slide, Runnable onComplete) {
        this.from = from;
        this.to = to;
        this.slide = slide;
        this.duration = slide ? SLIDE_DURATION : FADE_DURATION;
        this.onComplete = onComplete;
        if (slide) {
            to.setContentOffsetX(AbstractScreen.VIRTUAL_WIDTH);
        } else {
            to.setContentAlpha(0f);
        }
    }

    @Override
    public void render(float delta) {
        elapsed += delta;
        float t = Math.min(elapsed / duration, 1f);

        if (slide) {
            float eased = Interpolation.smooth.apply(t);
            from.setContentOffsetX(-AbstractScreen.VIRTUAL_WIDTH * eased);
            to.setContentOffsetX(AbstractScreen.VIRTUAL_WIDTH * (1f - eased));
        } else {
            from.setContentAlpha(1f - t);
            to.setContentAlpha(t);
        }

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
