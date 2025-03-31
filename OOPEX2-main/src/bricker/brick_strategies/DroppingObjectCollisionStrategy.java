package bricker.brick_strategies;

import bricker.gameobjects.Heart;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The DroppingObjectCollisionStrategy class implements the CollisionStrategyDecorator interface, offering
 * a specialized collision
 * handling strategy that involves dropping heart objects upon certain collisions. This strategy is
 * designed to enrich the gameplay
 * by introducing a visual and interactive element—heart objects—that can have positive effects on the
 * game's state, such as increasing
 * the player's health or lives. It seamlessly integrates with a basic collision handling framework, adding
 * a layer of functionality that
 * can significantly impact player experience and game dynamics.
 * <p>
 * This strategy utilizes an underlying basic collision strategy for initial collision processing, ensuring
 * that collisions are handled
 * efficiently before applying any specialized behavior. The addition of heart objects as a result of
 * collisions introduces a new
 * gameplay mechanic, where players can potentially recover health or gain additional lives through in-game
 * actions.
 */
public class DroppingObjectCollisionStrategy implements CollisionStrategyDecorator {
    private static final String PATH_HEART = "assets/heart.png";
    private final GameObjectCollection gameObjects;
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final ImageRenderable heartImage;
    private final Counter heartsCounter;
    private final Vector2 windowDimensions;

    /**
     * Constructs a new DroppingObjectCollisionStrategy instance, integrating a basic collision strategy
     * with the functionality to drop heart objects upon certain collisions. This strategy enhances
     * the game by adding a visual and interactive element that can affect the game's state, such as
     * increasing the player's health or lives.
     *
     * @param gameObjects            The collection of game objects within the current game context. This
     *                               collection
     *                               is manipulated to add heart objects upon collision.
     * @param basicCollisionStrategy The underlying basic collision strategy that defines how collisions
     *                               are initially handled before any special dropping behavior is applied.
     * @param imageReader            An image reader utility to load the graphical representation of the
     *                               heart object.
     *                               This allows the strategy to create visual elements dynamically based
     *                               on game events.
     * @param heartsCounter          A counter that tracks the number of hearts or similar beneficial items
     *                               distributed or affected by collisions. This can be used to monitor game
     *                               progress, player health, or other relevant metrics.
     * @param windowDimensions
     */
    public DroppingObjectCollisionStrategy(GameObjectCollection gameObjects, BasicCollisionStrategy
            basicCollisionStrategy,
                                           ImageReader imageReader, Counter heartsCounter,
                                           Vector2 windowDimensions) {
        this.gameObjects = gameObjects;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.heartImage = imageReader.readImage(PATH_HEART, true);
        this.heartsCounter = heartsCounter;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Handles the collision event between two game objects by first applying the basic collision strategy
     * and then creating a new heart object at the collision location. This method adds a gameplay
     * element where hearts (or similar beneficial items) can be spawned as a result of certain collisions,
     * potentially providing the player with health boosts or other advantages.
     *
     * @param thisObj  The GameObject involved in the collision that triggers the heart drop. This object's
     *                 position is used as a reference for where to spawn the heart object.
     * @param otherObj The GameObject that collided with thisObj. The specifics of this object are not
     *                 directly used in the dropping mechanism but are required for the interface's
     *                 general contract and might be used in the basic collision handling.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.basicCollisionStrategy.onCollision(thisObj, otherObj);

        Heart heart = new Heart(thisObj.getTopLeftCorner(), BrickerGameManager.HeartDimensions,
                this.heartImage,
                this.heartsCounter, this.windowDimensions, this.gameObjects);
        positionHeart(heart, thisObj.getCenter());
    }

    /**
     * Positions and activates a heart object within the game world. This method sets the initial
     * velocity and center position of the heart, effectively "dropping" it from the location of
     * a collision event or any specified location. The heart then becomes a collectible or interactable
     * object within the game environment.
     *
     * @param heart    The Heart object to be positioned and activated. This object is assumed to
     *                 have been newly created as a result of a collision or other game event.
     * @param location The Vector2 location at which the heart will be positioned. This is typically
     *                 the center point of the collision event, serving as the starting point for the
     *                 heart's movement.
     */
    private void positionHeart(Heart heart, Vector2 location) {
        float ballVelX = 0;
        float ballVely = 100;
        heart.setVelocity(new Vector2(ballVelX, ballVely));
        heart.setCenter(location);
        this.gameObjects.addGameObject(heart);
    }
}
