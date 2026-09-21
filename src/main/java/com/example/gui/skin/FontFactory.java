package com.example.gui.skin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Generates the title/body BitmapFonts from assets/fonts/*.ttf via
 * gdx-freetype (§3 of hearts-gui-decisions.md). Falls back to libGDX's
 * built-in default font, scaled to roughly the right size, when a .ttf
 * hasn't been delivered yet, so the build keeps running while the real
 * fonts are still in progress.
 */
public final class FontFactory {

    private static final String TITLE_FONT_PATH = "fonts/title.ttf";
    private static final String BODY_FONT_PATH = "fonts/body.ttf";
    private static final int TITLE_SIZE_PX = 48;
    private static final int HEADING_SIZE_PX = 28;
    private static final int BODY_SIZE_PX = 22;

    private FontFactory() {}

    public static BitmapFont createTitleFont() {
        return generate(TITLE_FONT_PATH, TITLE_SIZE_PX);
    }

    /** Smaller size of the display face, for section headings inside panels. */
    public static BitmapFont createHeadingFont() {
        return generate(TITLE_FONT_PATH, HEADING_SIZE_PX);
    }

    public static BitmapFont createBodyFont() {
        return generate(BODY_FONT_PATH, BODY_SIZE_PX);
    }

    private static BitmapFont generate(String path, int sizePx) {
        FileHandle file = Gdx.files.internal(path);
        if (!file.exists()) {
            Gdx.app.error("FontFactory", path + " not found - using fallback font");
            BitmapFont fallback = new BitmapFont();
            fallback.getData().setScale(sizePx / 16f);
            fallback.setColor(Palette.TEXT_ON_DARK);
            return fallback;
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = sizePx;
        parameter.color = Palette.TEXT_ON_DARK;
        parameter.minFilter = TextureFilter.Linear;
        parameter.magFilter = TextureFilter.Linear;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
}
