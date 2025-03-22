package io.waterDropClone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TitleScreen extends ScreenAdapter {
    private Main game;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont font;
    private GlyphLayout layout;
    private Texture gameTitle;
private Texture criminalBuck;
    private Viewport viewport;
    private OrthographicCamera camera;

//constructor for titlescreen
    public TitleScreen(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        viewport.apply();
        batch = new SpriteBatch();
        font = new BitmapFont();

        background = new Texture("title background.png");
        layout = new GlyphLayout(font, "Press ENTER !");
        criminalBuck = new Texture("Criminal_Bucket.png");
        gameTitle = new Texture("dessinDROPLET_Calque 1.png");
    }









    public void render(float delta){
            ScreenUtils.clear(0, 0, 0, 1);

            batch.begin();
            batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            font.draw(batch, layout,
                (Gdx.graphics.getWidth() - layout.width) / 2,
                Gdx.graphics.getHeight() / 4);


            //for criminal buck
            float buckX = (viewport.getWorldHeight() - criminalBuck.getWidth()) / 2;
            float buckY =(Gdx.graphics.getHeight() / 4) ;
        batch.draw(criminalBuck, 100, 100,criminalBuck.getWidth() / 2f, criminalBuck.getHeight() / 2f);



        //for CALCQUE
        float calX = (viewport.getWorldWidth() - gameTitle.getWidth()) / 4;
        float calY =((viewport.getWorldHeight() + 80 )/ 2) ;
        batch.draw(gameTitle, calX, calY);
        batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                System.out.println("switching to game");
                game.setScreen(new GameScreen(game)); // Switch to GameScreen

            }


        } @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    public void dispose() {
        batch.dispose();
        background.dispose();
        font.dispose();
        criminalBuck.dispose();
        gameTitle.dispose();
    }
    }

