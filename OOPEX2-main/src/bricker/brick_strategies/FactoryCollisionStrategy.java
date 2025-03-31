package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

/**
 * The FactoryCollisionStrategy class is responsible for dynamically generating and managing collision
 * strategies
 * based on game events and conditions. It acts as a factory for creating a variety of collision strategy
 * instances,
 * ranging from basic behaviors to more complex, specialized strategies such as adding game objects (e.g.,
 * paddles, pucks),
 * changing camera views, or introducing double behaviors that combine multiple strategies for rich
 * gameplay interactions.
 * This flexibility allows for a dynamic and engaging game experience, where the consequences of collisions
 * can vary
 * significantly, adding depth and variety to the game.
 * <p>
 * Through the use of randomized selection and conditionally applied logic, this factory supports the
 * introduction of
 * unexpected elements and challenges, encouraging players to adapt their strategies and promoting a more
 * interactive
 * and immersive gameplay environment.
 */

public class FactoryCollisionStrategy {
    private static final int NUMBER_OF_OPTION = 5;
    private static final int SPACIAL_COLLISION_STRATEGY = 5;
    private static final int ADD_PADDLE = 0;
    private static final int ADD_PUCK = 1;
    private static final int ADD_CAMERA_CHANGING = 2;
    private static final int ADD_DROPPING_OBJECT = 3;
    private static final int ADD_DOUBLE_BEHAVIOR = 4;
    private final GameObjectCollection gameObject;
    private final Counter heartsCounter;
    private final Counter brickCounter;
    private final ImageReader imageReader;
    private final UserInputListener userInputListener;
    private final Vector2 windowDimensions;
    private final SoundReader soundReader;
    private final BrickerGameManager gameManager;
    private final WindowController windowController;

    /**
     * Constructs a new FactoryCollisionStrategy instance, responsible for handling collision events
     * and generating game objects dynamically. This strategy encapsulates the logic required to
     * create and manage new game elements as a result of collisions, including but not limited to
     * power-ups, obstacles, or any other game-relevant entities.
     *
     * @param gameObject        A collection of game objects where new objects will be added.
     * @param heartsCounter     A counter to track or influence game state changes (e.g., score, lives).
     * @param brickCounter
     * @param imageReader       Utility to read and provide images for newly created objects.
     * @param userInputListener Listener for user input, allowing for interactive object creation or
     *                          manipulation.
     * @param windowDimensions  The dimensions of the game window, used to place new objects within visible
     *                          bounds.
     * @param soundReader       Utility to read and assign sounds to newly created objects, enhancing game
     *                          immersion.
     * @param gameManager       The central game management unit, coordinating game state and interactions.
     * @param windowController  Controller for the game window, enabling manipulation of the game's visual
     *                          context.
     */
    public FactoryCollisionStrategy(GameObjectCollection gameObject, Counter heartsCounter,
                                    Counter brickCounter, ImageReader imageReader,
                                    UserInputListener userInputListener,
                                    Vector2 windowDimensions, SoundReader soundReader,
                                    BrickerGameManager gameManager, WindowController windowController) {
        this.gameObject = gameObject;
        this.heartsCounter = heartsCounter;
        this.brickCounter = brickCounter;
        this.imageReader = imageReader;
        this.userInputListener = userInputListener;
        this.windowDimensions = windowDimensions;
        this.soundReader = soundReader;
        this.gameManager = gameManager;
        this.windowController = windowController;
    }

    /**
     * Creates and returns a new CollisionStrategy instance based on random selection and specified
     * conditions.
     * This method decides between a basic collision strategy and a variety of specialized strategies,
     * potentially
     * including double behavior strategies, based on a random choice and the provided parameter that
     * influences
     * strategy complexity.
     *
     * @param haveDoubleBehavior An integer parameter that affects the likelihood and type of complex
     *                           collision
     *                           strategy creation. Higher values may increase the variety of strategies
     *                           returned.
     * @return A CollisionStrategy instance, which could be a basic strategy or one of several specialized
     * strategies, depending on random selection and the value of haveDoubleBehavior.
     */
    public CollisionStrategy createNewCollisionStrategy(int haveDoubleBehavior, boolean ifInDoubleBehavior) {
        Random random = new Random();
        boolean toDoBasic = random.nextBoolean();
        BasicCollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(this.gameObject,
                this.brickCounter);
        if (toDoBasic && !ifInDoubleBehavior) {
            return basicCollisionStrategy;
        }
        if (ifInDoubleBehavior) {
            basicCollisionStrategy = new NullCollisionStrategy(this.gameObject,
                    this.brickCounter);
        }
        int randNum = random.nextInt(haveDoubleBehavior);
        return switch (randNum) {
            case ADD_PADDLE -> new AddPaddleCollisionStrategy(basicCollisionStrategy, imageReader,
                    userInputListener, windowDimensions);
            case ADD_PUCK -> new AddPuckCollisionStrategy(gameObject, basicCollisionStrategy, imageReader,
                    soundReader, windowDimensions);
            case ADD_CAMERA_CHANGING ->
                    new CameraChangingStrategy(gameObject, basicCollisionStrategy, gameManager,
                            windowController);
            case ADD_DROPPING_OBJECT -> new DroppingObjectCollisionStrategy(gameObject,
                    basicCollisionStrategy, imageReader, heartsCounter, windowDimensions);
            case ADD_DOUBLE_BEHAVIOR -> doubleBehaviorChoice();
            default -> basicCollisionStrategy;
        };
    }


    /**
     * Chooses and returns a collision strategy with double behavior, optionally including a third behavior.
     * This method generates two or three collision strategies at random and combines them into a single
     * DoubleBehaviorStrategy instance. The inclusion of a third behavior is based on a random chance.
     *
     * @return A DoubleBehaviorStrategy instance composed of two or three randomly selected collision
     * strategies.
     * The third strategy is included based on a random determination.
     */
    private CollisionStrategy doubleBehaviorChoice() {
        BasicCollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(this.gameObject,
                this.brickCounter);
        Random random = new Random();
        boolean haveThirdBehavior = random.nextInt(NUMBER_OF_OPTION) < 2;

        CollisionStrategy firstStrategy = createNewCollisionStrategy
                (SPACIAL_COLLISION_STRATEGY - 1, true);
        CollisionStrategy secondStrategy = createNewCollisionStrategy
                (SPACIAL_COLLISION_STRATEGY - 1, true);
        if (haveThirdBehavior) {
            CollisionStrategy thirdStrategy = createNewCollisionStrategy
                    (SPACIAL_COLLISION_STRATEGY - 1, true);
            return new DoubleBehaviorStrategy(basicCollisionStrategy, firstStrategy, secondStrategy,
                    thirdStrategy);
        }
        return new DoubleBehaviorStrategy(basicCollisionStrategy, firstStrategy, secondStrategy, null);

    }
}
