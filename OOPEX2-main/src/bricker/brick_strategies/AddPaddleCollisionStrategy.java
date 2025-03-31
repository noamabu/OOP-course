package bricker.brick_strategies;

import bricker.gameobjects.ImitationPaddle;
import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The AddPaddleCollisionStrategy class implements the CollisionStrategyDecorator interface to enhance
 * collision handling
 * with the capability to add an imitation paddle into the game. This class primarily acts as a decorator
 * for the
 * BasicCollisionStrategy, adding a specific reaction to collisions that meet certain criteria. It is
 * designed to
 * work within a game environment where objects interact through collisions, and new gameplay elements can
 * be introduced
 * dynamically in response to these events.
 * <p>
 * Upon detecting a collision, this strategy first delegates to the BasicCollisionStrategy to handle the
 * initial collision
 * logic. If the conditions are met (specifically, if an imitation paddle does not already exist), it then
 * proceeds to
 * add an imitation paddle to the game. This paddle is added at the center of the game window and is
 * intended to serve
 * as an additional interactive element within the game.
 */

public class AddPaddleCollisionStrategy implements CollisionStrategyDecorator {

    private final BasicCollisionStrategy basicCollisionStrategy;
    private static final int PADDLE_HEIGHT = 30;
    private static final int PADDLE_WIDTH = 100;
    private static final String PADDLE_IMAGE = "assets/paddle.png";

    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    /**
     * Handles the collision event for a GameObject.
     * This method is called when a collision is detected between this object and another GameObject.
     * It first delegates the collision handling to a basic collision strategy. If an ImitationPaddle
     * already exists, it immediately returns without further action. Otherwise, it creates a new
     * ImitationPaddle object at the center of the window with predefined dimensions and adds it to the game.
     *
     * @param thisObj  The GameObject this method is a part of, involved in the collision.
     * @param otherObj The other GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.basicCollisionStrategy.onCollision(thisObj, otherObj);

        if (ImitationPaddle.imitationPaddleExs) {
            return;
        }

        Renderable paddleIcon = imageReader.readImage(PADDLE_IMAGE, false);

        Vector2 paddleDimensions = new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT);
        GameObject imitationPaddle = new ImitationPaddle(Vector2.ZERO, paddleDimensions,
                paddleIcon, inputListener, windowDimensions, this.basicCollisionStrategy.getGameObject());
        imitationPaddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2));
        this.basicCollisionStrategy.getGameObject().addGameObject(imitationPaddle);


    }

    /**
     * Constructs a new instance of AddPaddleCollisionStrategy.
     * This strategy is used for handling collisions in a way that potentially adds an imitation paddle to the
     * game scene upon certain collision events. It encapsulates basic collision logic along with the logic
     * for adding an imitation paddle under specific conditions.
     *
     * @param basicCollisionStrategy The underlying basic collision strategy used for initial collision
     *                               handling.
     * @param imageReader            The utility to read images from resources, used for creating the paddle
     *                               icon.
     * @param inputListener          The listener for user input, which will be passed to the imitation paddle
     * @param windowDimensions       The dimensions of the game window, used to position the imitation paddle
     *                               appropriately.
     */
    public AddPaddleCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy, ImageReader imageReader,
                                      UserInputListener inputListener, Vector2 windowDimensions) {
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }
}
