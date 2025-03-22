package io.waterDropClone;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends ScreenAdapter {
    private BitmapFont font;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    float dropTimer;//keeping track of firebolts
    Sprite bucketSprite;
    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Sound dropSound;
    Music music;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Vector2 touchPos;
    float dropHeight;
    Main game;
    GlyphLayout lives;

    String scoreText;
    Array<Sprite> dropSprites; //hado homa l firebolts li yti7o m sma
   // @Override

    public GameScreen(Main game) { // ✅ Constructor accepting Main
        this.game = game;
    }

    public void show() {

        font = new BitmapFont(); // Initialize the font
        font.setColor(Color.WHITE); // Optional: Set font color
        font.getData().setScale(0.5f); // Optional: Adjust size
        lives = new GlyphLayout(font, "3/3"); // Now font is initialized

        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();
        dropSprites = new Array<>(); //created firebolts
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("Criminal_Buckets_Says.png");
        dropTexture = new Texture("buck2.png");
        lives = new GlyphLayout(font, "3/3");
        touchPos = new Vector2();
        //for music and sound effects
        dropSound = Gdx.audio.newSound(Gdx.files.internal("Minecraft Item Drop Sound Effect!.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("maxb24 - shawty wanna fuck (instrumental).mp3"));
        //how the game looks and the window
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        bucketSprite = new Sprite(bucketTexture); // Initialize the sprite based on the texture
        bucketSprite.setSize(1, 1);
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }
    public void render(float delta) {
        // organize code into three methods
        input();
        logic();
        draw();


    }
    //THATS FOR INPUTS -----------------------------------------------------------------------------------------------------
    private void input() {
        float speed = .15f;
        float delta = Gdx.graphics.getDeltaTime();
//FOR KEYBOARD INPUT--------------------------------------------------------------------------------------------
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucketSprite.translateX(speed);
            bucketSprite.translateX(speed * delta); // Move the bucket right
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucketSprite.translateX(-speed);
            bucketSprite.translateX(-speed * delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            bucketSprite.translateX(speed);
            bucketSprite.translateX(speed * delta); // Move the bucket right
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            bucketSprite.translateX(-speed);
            bucketSprite.translateX(-speed * delta);
        }

        //FOR MOUSE INPUT
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            bucketSprite.setCenterX(touchPos.x); // Change the horizontally centered position of the bucket
        }
    }
    //GAME LOGIC==============================================================================================
    private void logic() {
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~hadi bech mayrohch off screen (BUCKET LOGIC)  ~~~~~~~~~~~~~~~~~~~~
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Store the bucket size for brevity
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        // Subtract the bucket width
        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));
// FIREBOLTS LOGIC

        float delta = Gdx.graphics.getDeltaTime();
        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);

        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i);
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);
            // Apply the drop position and size to the dropRectangle
            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
            else if (bucketRectangle.overlaps(dropRectangle)) { // Check if the bucket overlaps the drop
                dropSprites.removeIndex(i);
                dropSound.play(); // Remove the drop
            }


        }
        dropTimer += delta; // Adds the current delta to the timer
        if (dropTimer > 1f) { // Check if it has been more than a second
            dropTimer = 0; // Reset the timer
            createDroplet(); // Create the droplet
        }
    }
    //THATS FOR DRAWING-----------------------------------------------------------------------------------------
    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        bucketSprite.draw(spriteBatch);


        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(spriteBatch);
        }



        font.draw(spriteBatch, "3/3",
            viewport.getWorldWidth() - lives.width - 10, // 10px padding from the right
            viewport.getWorldHeight() - 10 // 10px padding from the top
        );

        spriteBatch.end();
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    private void createDroplet() {
        // create local variables for convenience
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // create the drop sprite
        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth)); // Randomize the drop's x position
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }
}

