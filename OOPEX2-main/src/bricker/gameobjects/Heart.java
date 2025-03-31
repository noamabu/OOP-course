package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a heart GameObject in the game.
 * Extends the GameObject class.
 */
public class Heart extends GameObject {
    private static final String PATH_HEART = "assets/heart.png";
    //    private final ImageRenderable image;
    private final Counter heartsCounter;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param heartsCounter
     * @param windowDimensions
     * @param gameObjects
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Counter heartsCounter,
                 Vector2 windowDimensions, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);

        this.heartsCounter = heartsCounter;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
    }

    /**
     * Handles collision events when this object collides with another GameObject.
     * If the collision counter is greater than or equal to 4, no action is taken.
     * Otherwise, increments the collision counter.
     *
     * @param other     the GameObject with which this object collided
     * @param collision the collision details
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.heartsCounter.value() >= 4) {
            return;
        }
        this.heartsCounter.increment();
        this.gameObjects.removeGameObject(this);
    }

    /**
     * Updates the game state based on the elapsed time since the last update.
     * Removes the heart GameObject from the game if it goes beyond the top boundary of the window.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.windowDimensions.y() < this.getCenter().y()) {
            this.gameObjects.removeGameObject(this);
        }
    }

    /**
     * Determines whether this object should collide with another GameObject.
     *
     * @param other the GameObject to check for collision with
     * @return true if this object should collide with the specified GameObject (if it's an instance of
     * Paddle), false otherwise
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other instanceof ImitationPaddle) {
            return false;
        }
        return other instanceof Paddle;
    }
}
