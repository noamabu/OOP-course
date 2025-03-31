package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.gameobjects.CounterBallCamera;
import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * The CameraChangingStrategy class implements the CollisionStrategyDecorator interface, offering a unique
 * approach to handling
 * collisions within a game by dynamically altering the camera's perspective or behavior based on specific
 * collision events. This strategy
 * is designed to integrate closely with the game's management and control systems, enabling it to respond
 * to in-game actions by changing
 * how the game is displayed or experienced by the player. It builds upon a basic collision handling
 * foundation, adding a layer of
 * interaction that can significantly affect the game's visual presentation and the player's engagement
 * with the game environment.
 * <p>
 * Through the collaboration of game objects, the basic collision strategy, the game manager, and the
 * window controller, this strategy
 * facilitates a seamless transition between camera views, enhancing the gameplay with dynamic visual
 * effects. It is particularly focused
 * on responding to collisions involving specific game objects (e.g., Balls but not Pucks) and can initiate
 * a camera change to center
 * on an object of interest, providing a fresh perspective or focus that adds depth to the game's narrative
 * or visual storytelling.
 */
public class CameraChangingStrategy implements CollisionStrategyDecorator {

    private final GameObjectCollection gameObjects;
    private final CollisionStrategy basicCollisionStrategy;
    private final BrickerGameManager gameManager;
    private final WindowController windowController;
    private CounterBallCamera counterBallCamera;

    /**
     * Constructs a new CameraChangingStrategy instance.
     * This strategy is designed to alter the camera's view or behavior in response to certain game events,
     * potentially triggered by collisions. It integrates with the game's overall management and control
     * systems,
     * facilitating dynamic changes to how the game is displayed or progresses based on in-game actions.
     *
     * @param gameObjects            The collection of all game objects, allowing for interaction with
     *                               or modification of the game's current objects as necessary.
     * @param basicCollisionStrategy The basic collision handling strategy, serving as a foundation
     *                               upon which this camera-changing strategy builds.
     * @param gameManager            The game manager, providing access to game state management and
     *                               control functionalities.
     * @param windowController       The window controller, enabling direct manipulation of the game's
     *                               display window, such as changing camera perspectives or views.
     */
    public CameraChangingStrategy(GameObjectCollection gameObjects,
                                  CollisionStrategy basicCollisionStrategy,
                                  BrickerGameManager gameManager,
                                  WindowController windowController) {
        this.gameObjects = gameObjects;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.gameManager = gameManager;
        this.windowController = windowController;
    }

    /**
     * Handles the collision event between this object and another object, potentially initiating
     * a camera change. This method delegates to the basic collision strategy for initial collision
     * handling, then checks specific conditions to possibly activate a new camera focus.
     * If the conditions are met (collision with a Ball object, this object is not a Puck, and no
     * camera is currently set), it creates a new Camera centered on the other object (Ball), adjusting
     * the view to provide a different perspective or focus. Additionally, a CounterBallCamera object
     * is created and added to the game, tasked with monitoring or interacting with the camera-focused
     * ball.
     *
     * @param thisObj  The GameObject associated with this strategy that is involved in the collision.
     * @param otherObj The GameObject that collides with thisObj. The method specifically checks if
     *                 this object is a Ball and if certain conditions are met to initiate a camera change.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        if (otherObj instanceof Ball && !(otherObj instanceof Puck) && gameManager.camera() == null) {
            gameManager.setCamera(new Camera(otherObj, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()));
            this.counterBallCamera = new CounterBallCamera((Ball) otherObj, this);
            gameObjects.addGameObject(this.counterBallCamera, Layer.BACKGROUND);
        }
    }

    /**
     * Terminates the current camera change effect initiated by this strategy.
     * This method resets the game's camera to its default state (no specific focus) by setting it to null
     * and removes the CounterBallCamera object from the game. This effectively ends the dynamic camera
     * focus on a specific object (e.g., a Ball) and returns the game view to its standard configuration.
     * It is typically called when the conditions for the camera change no longer apply or when the game
     * state changes in a way that makes the focused camera view irrelevant or unnecessary.
     */
    public void endCameraChange() {
        this.gameManager.setCamera(null);
        gameObjects.removeGameObject(this.counterBallCamera, Layer.BACKGROUND);
    }
}
