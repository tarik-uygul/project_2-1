package com.example.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.example.gui.GameMain;

public class StartScreen implements Screen {

    private final GameMain game;
    private FitViewport fitViewport;
    private SpriteBatch batch;
    private Texture bgTexture;
    private Stage stage;

    public StartScreen(GameMain game){
        this.game = game;
    }

    @Override
    public void show() {
        fitViewport = new FitViewport(1280, 720);

        batch = new SpriteBatch();

        bgTexture = new Texture("assets/backgrounds/pinkBG.jpg");

        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("assets/skins/rainbow/rainbow-ui.json"));

        TextButton playButton = new TextButton("PLAY", skin);
        playButton.setPosition(256, 150);
        playButton.setSize(220, 75);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(playButton);

        TextButton rulesButton = new TextButton("RULES", skin);
        rulesButton.setPosition(512, 150);
        rulesButton.setSize(220, 75);
        rulesButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(rulesButton);

        TextButton quitButton = new TextButton("QUIT", skin);
        quitButton.setPosition(768, 150);
        quitButton.setSize(220, 75);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(quitButton);

        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(
                        Gdx.files.internal("assets/fonts/HEARTS.ttf")
                );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 120;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 4;
        parameter.borderColor = Color.BLACK;

        BitmapFont titleFont = generator.generateFont(parameter);

        generator.dispose();

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;

        Label title = new Label("HEARTS", titleStyle);

        title.setPosition(330, 400);

        stage.addActor(title);
    }

    public void draw(){
        ScreenUtils.clear(Color.BLACK);
        fitViewport.apply();
        batch.setProjectionMatrix(fitViewport.getCamera().combined);

        float worldWidth = fitViewport.getWorldWidth();
        float worldHeight = fitViewport.getWorldHeight();

        batch.begin();
        batch.draw(bgTexture, 0, 0, worldWidth, worldHeight);
        batch.end();
    }

    @Override
    public void render(float delta) {
        draw();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
