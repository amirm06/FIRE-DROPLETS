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

public class WinScreen2 extends ScreenAdapter {
    private Main game;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont font;
    private GlyphLayout layout;
    private Texture gameTitle;
    private Texture criminalBuck;
    private Viewport viewport;
    private OrthographicCamera camera;
    private GameScreen3 gameScreen3;
    private GameScreen2 gameScreen2;
    //constructor for WinScreen1
    public WinScreen2(Main game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(800, 600, camera);
        this.viewport.apply();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.background = new Texture("background3.png");
        this.layout = new GlyphLayout(font, "NEXT STAGE : 3");
        this.criminalBuck = new Texture("Criminal_Bucket.png");
        this.gameTitle = new Texture("NEXTSTAGE2_Calque 1.png");
    }

    // Constructor for GameScreen2
    public WinScreen2(Main game, GameScreen2 gameScreen2) {
        this(game); // Calls the main constructor
        this.gameScreen2 = gameScreen2;
    }

    // Constructor for GameScreen3
    public WinScreen2(Main game, GameScreen3 gameScreen3) {
        this(game); // Calls the main constructor
        this.gameScreen3 = gameScreen3;
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
            System.out.println("Switching back to GameScreen...");
            game.setScreen(new GameScreen3(game));
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

