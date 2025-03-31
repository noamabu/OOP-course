package bricker.main;

import bricker.brick_strategies.FactoryCollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;
import java.util.Random;

/**
 * Manages the game logic for a Bricker game.
 */
public class BrickerGameManager extends GameManager {
    private static final float BALL_SPEED = 150;
    private static final int X = 500;
    private static final int Y = 300;
    private static final int ZERO = 0;
    private static final int NUM_OF_HEARTS = 3;
    private static final int NUM_ROW_BRICK = 8;
    private static final int BRICK_IN_ROW = 7;
    private static final int BETWEEN_BRICKS = 2;
    private static final int BRICK_WIDTH = 5;
    private static final int UNDER_BRICK = 7;
    private static final String PATH_OF_PADDLE = "assets/paddle.png";
    private static final String PATH_OF_HEART = "assets/heart.png";
    private static final String PATH_OF_BRICK = "assets/brick.png";
    private static final String PATH_OF_BACKGROUND = "assets/DARK_BG2_small.jpeg";
    private static final String PATH_OF_BALL = "assets/ball.png";
    private static final String PATH_OF_SOUND = "assets/blop_cut_silenced.wav";
    private static final int NUM_OF_STRATEGY = 5;
    private static final float BALL_DIMENSION = 20;
    private final Vector2 windowDimensions;
    private Ball ball;
    private static final int HEART_LOCATION_X = 20;
    private static final int HEART_LOCATION_Y = 20;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private final int BORDER_WIDTH = 5;
    private final Counter brickCounter;
    private final Counter heartsCounter;
    private static final int PADDLE_HEIGHT = 30;
    private static final int PADDLE_WIDTH = 100;
    private static final Vector2 numericHeartsDimensions = new Vector2(15, 15);

    /**
     * Represents the dimensions of a heart object.
     */
    public static final Vector2 HeartDimensions = new Vector2(20, 20);

    /**
     * Constructs a new BrickerGameManager object with the specified window title and dimensions.
     *
     * @param windowTitle      the title of the game window
     * @param windowDimensions the dimensions (width and height) of the game window
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.windowDimensions = windowDimensions;
        this.heartsCounter = new Counter(NUM_OF_HEARTS);
        this.brickCounter = new Counter();
    }


    /**
     * Initializes the game with the provided resources and components.
     *
     * @param imageReader     the image reader for loading images
     * @param soundReader     the sound reader for loading sounds
     * @param inputListener   the user input listener for capturing user input
     * @param windowController the window controller for managing the game window
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        super.initializeGame(imageReader, this.soundReader, this.inputListener, windowController);
        Renderable paddleImage = imageReader.readImage(PATH_OF_PADDLE, false);

        createBall(imageReader, this.soundReader);

        //create UserPaddle
        createUserPaddle(this.inputListener, paddleImage, windowDimensions);

        //create limits of board
        createLimits();

        //create Background
        createBackground(imageReader, windowController);

        //create Bricks
        createBricks(imageReader, windowDimensions, this.gameObjects());

        createGraphicHearts(imageReader);

        createNumericHearts();
    }
    /**
     * Updates the game state based on the elapsed time since the last update.
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    private void createNumericHearts() {
        NumericLifeCounter numericLifecounter = new NumericLifeCounter(heartsCounter,
                new Vector2(HEART_LOCATION_X + 20, windowDimensions.y() - HEART_LOCATION_Y - 20),
                numericHeartsDimensions, gameObjects());
        this.gameObjects().addGameObject(numericLifecounter, Layer.BACKGROUND);
    }

    private void createGraphicHearts(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage(PATH_OF_HEART, true);
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter
                (new Vector2(HEART_LOCATION_X, windowDimensions.y() - HEART_LOCATION_Y)
                , HeartDimensions, heartsCounter, heartImage, gameObjects()
                , NUM_OF_HEARTS);
        gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND);
    }


    private void createBricks(ImageReader imageReader, Vector2 windowDimensions,
                              GameObjectCollection gameObjects) {
        Renderable brickImage = imageReader.readImage(PATH_OF_BRICK, false);
        int space = BORDER_WIDTH*4 + BETWEEN_BRICKS*(BRICK_IN_ROW-1);
        FactoryCollisionStrategy brickFactory = new FactoryCollisionStrategy(gameObjects,
                this.heartsCounter, this.brickCounter,
                imageReader, this.inputListener, windowDimensions, soundReader,
                this, windowController);
        int brickLen = (((int) windowDimensions.x()) - space)/BRICK_IN_ROW;
        for (int row = 0; row < NUM_ROW_BRICK; row++) {
            for (int col = 0; col < BRICK_IN_ROW; col++) {
                Brick brick = new Brick(new Vector2(BORDER_WIDTH*2 + col*brickLen + BETWEEN_BRICKS,
                        BORDER_WIDTH + (BRICK_WIDTH+UNDER_BRICK)*row),
                        new Vector2(58, 10), brickImage,
                        brickFactory.createNewCollisionStrategy(NUM_OF_STRATEGY, false));
                brickCounter.increaseBy(1);
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private void createBackground(ImageReader imageReader, WindowController windowController) {
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowController.getWindowDimensions(),
                imageReader.readImage(PATH_OF_BACKGROUND, false));
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createLimits() {
        //left boarder
        this.gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()), null));
        //right boarder
        this.gameObjects().addGameObject(new GameObject
                (new Vector2(windowDimensions.x() - BORDER_WIDTH, ZERO),
                new Vector2(BORDER_WIDTH, windowDimensions.y()), null));
        //upper boarder
        this.gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH), null));
    }

    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";

        if (brickCounter.value() <= 0) {

            prompt = "you win";
        }
        if (ballHeight > windowDimensions.y()) {
            heartsCounter.decrement();
            if (heartsCounter.value() > 0) {
                positionBall();
            } else
                prompt = "you lose";
        }
        if (!prompt.isEmpty()) {
            prompt += " play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
                this.brickCounter.reset();
                this.heartsCounter.reset();
                this.heartsCounter.increaseBy(NUM_OF_HEARTS);
            }
            else
                windowController.closeWindow();
        }
    }

    private void positionBall() {
        float ballVelX = BALL_SPEED;
        float ballVely = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean())
            ballVelX *= -1;
        if (random.nextBoolean())
            ballVely *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVely));
        ball.setCenter(windowDimensions.mult(0.5F));
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(PATH_OF_BALL, true);
        Sound collisionSound = soundReader.readSound(PATH_OF_SOUND);
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_DIMENSION, BALL_DIMENSION), ballImage, collisionSound);
        positionBall();
        gameObjects().addGameObject(ball);
    }

    private void createUserPaddle(UserInputListener inputListener, Renderable paddleImage,
                                  Vector2 windowDimensions) {
        GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() - PADDLE_HEIGHT));
        gameObjects().addGameObject(userPaddle);
    }

    /**
     * The entry point for the program.
     * Creates an instance of BrickerGameManager with the specified window title and dimensions,
     * and starts the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new BrickerGameManager("ball", new Vector2(X, Y)).run();
    }
}
