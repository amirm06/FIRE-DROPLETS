package io.waterDropClone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.ScreenAdapter;

public class LoseScreen extends ScreenAdapter {
    private Main game;
    Texture dropTexture;
    private Texture background;
    private Texture youlost;
    private Viewport viewport;
    private OrthographicCamera camera;;
    private Texture sadcriminalBuck;
    private BitmapFont font;
    private Music music;
    private SpriteBatch batch;
    Array<Sprite> dropSprites; //hado homa l firebolts li yti7o m sma
    // @Override
//constructor

    public LoseScreen(Main game) { // ✅ Constructor accepting Main
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        music = Gdx.audio.newMusic(Gdx.files.internal("Surfs Up! The Video Game - 28 - Losing Theme (Game Audio).mp3"));
        viewport.apply();
        batch = new SpriteBatch();
        font = new BitmapFont();
        background = new Texture("Screenshot 2025-01-27 033917.png");
        youlost = new Texture("image_Calque 1.png");
        sadcriminalBuck = new Texture("Criminal_Buckets_sad_Image.png");
        dropSprites = new Array<>(); //created firebolts
    }

    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        music.setVolume(.5f);
        music.play();



        //for the swarming firebolts
        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(batch);

        }

        //for criminal buck
        float buckX = (viewport.getWorldHeight() - sadcriminalBuck.getWidth()) / 2;
        float buckY =(Gdx.graphics.getHeight() / 4) ;
        batch.draw(sadcriminalBuck, 100, 100,sadcriminalBuck.getWidth() / 2f, sadcriminalBuck.getHeight() / 2f);



        //for CALCQUE
        float calX = (viewport.getWorldWidth() - youlost.getWidth()) / 4;
        float calY =((viewport.getWorldHeight() + 80 )/ 2) ;
        batch.draw(youlost, calX, calY);
        batch.end();



        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("switching to game");
            game.setScreen(new GameScreen(game)); // Switch to GameScreen

        }
        try {
            background = new Texture("Screenshot 2025-01-27 033917.png");
            System.out.println("Background image loaded.");
        } catch (Exception e) {
            System.out.println("Failed to load background image.");
        }




    } @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    public void dispose() {
        batch.dispose();
        background.dispose();
        font.dispose();
        sadcriminalBuck.dispose();
        youlost.dispose();
        music.dispose();
    }


}

