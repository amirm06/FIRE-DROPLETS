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

public class GameScreen2 extends ScreenAdapter {
    private BitmapFont font;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    float dropTimer;//keeping track of firebolts
    Sprite bucketSprite;
    float dropInterval = 1f; // Initial interval (1 second)
    float minDropInterval = 0.2f; // Minimum interval limit
    float intervalDecreaseRate = 0.02f;
    //for backgrounds
    Texture backgroundTexture;
    Texture backgroundTexture2 , backgroundTexture3;
    Texture bucketTexture,bucketTexturehappy;
    Texture dropTexture;
    //audio shi
    Sound dropSound;
    Music music;
    Music music2,music3;
    Music celeb;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Vector2 touchPos;
    float dropHeight;
    Main game;
    GlyphLayout lives;
    int score=0;
    BitmapFont scoreFont;
    SpriteBatch fontBatch;
////

    private boolean collided = false;  // Collision flag
    private float changeTime = 0;
    String scoreText;
    Array<Sprite> dropSprites; //hado homa l firebolts li yti7o m sma
    // @Override

    public GameScreen2(Main game) { // ✅ Constructor accepting Main
        this.game = game;
    }

    public void show() {

        font = new BitmapFont(); // Initialize the font
        font.setColor(Color.WHITE); // Optional: Set font color
        font.getData().setScale(0.5f); // Optional: Adjust size
        lives = new GlyphLayout(font, "3/3"); // Now font is initialized

        fontBatch = new SpriteBatch();
        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.WHITE);
        scoreFont.getData().setScale(2);

        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();
        dropSprites = new Array<>(); //created firebolts
        backgroundTexture = new Texture("background2.png");

        celeb = Gdx.audio.newMusic(Gdx.files.internal("Celebration Sound Effect.mp3"));
        bucketTexture = new Texture("Criminal_Buckets_Says.png");
        bucketTexturehappy = new Texture("Criminal_Buckets_Says_Image_copy_2.png");
        dropTexture = new Texture("buck2.png");
        lives = new GlyphLayout(font, "3/3");
        touchPos = new Vector2();
        //for music and sound effects
        dropSound = Gdx.audio.newSound(Gdx.files.internal("Minecraft Item Drop Sound Effect!.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("maxb24 - shawty wanna fuck (instrumental).mp3"));
        music2 = Gdx.audio.newMusic(Gdx.files.internal("Shawty Wanna Fuck (Slowed  Reverb).mp3"));
        music3 = Gdx.audio.newMusic(Gdx.files.internal("Shawty Wanna Fuck (Nightcore).mp3"));
        //how the game looks and the window
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        bucketSprite = new Sprite(bucketTexture); // Initialize the sprite based on the texture
        bucketSprite.setSize(1, 1);

        if (!music.isPlaying()) {
            music.setLooping(true);
            music.setVolume(.5f);
            music2.play();
        }




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
            bucketSprite.setCenterX(touchPos.x);
            // Change the horizontally centered position of the bucket
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

            if (dropSprite.getY() < -dropHeight) {
                music2.stop();
                game.setScreen(new LoseScreen(game));
            }
            else if (bucketRectangle.overlaps(dropRectangle)) {
                bucketSprite.setTexture(bucketTexturehappy);// Check if the bucket overlaps the drop
                collided = true;
                changeTime = 1f;
                dropSprites.removeIndex(i);
                score=score+1;

                dropSound.play(); // Remove the drop
            }
            if (collided) {
                changeTime -= delta;
                if (changeTime <= 0) {
                    bucketSprite.setTexture(bucketTexture); // Revert to default sprite
                    collided = false; // Reset flag
                }
            }

        }
        dropTimer += delta; // Adds the current delta to the timer
        if (dropTimer > 0.6f) { // Check if it has been more than a second
            dropTimer = 0; // Reset the timer
            createDroplet(); // Create the droplet
            dropInterval = Math.max(minDropInterval, dropInterval - intervalDecreaseRate);
        }
    }
    //THATS FOR DRAWING-----------------------------------------------------------------------------------------
    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin(); ///beggining of drawing
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        if(score>=30){
            music2.stop();
            celeb.play();
           game.setScreen(new WinScreen2(game,this));
        }

        bucketSprite.draw(spriteBatch);
        scoreFont.draw(spriteBatch, "score"+score,100,450);


        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(spriteBatch);
        }



        font.draw(spriteBatch, "3/3",
            viewport.getWorldWidth() - lives.width - 10, // 10px padding from the right
            viewport.getWorldHeight() - 10 // 10px padding from the top
        );



        spriteBatch.end(); //end of draring
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

        System.out.println(score);
    }
}

