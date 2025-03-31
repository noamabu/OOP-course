package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a ball GameObject in the game.
 * Extends the GameObject class.
 */
public class Ball extends GameObject {

    private final Counter collisionCounter;

    private final Sound collisionSound;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = new Counter();
    }

    /**
     * Handles collision events when this object collides with another GameObject.
     * @param other     the GameObject with which this object collided
     * @param collision the collision details
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionCounter.increment();
        collisionSound.play();
    }

    /**
     * Retrieves the current value of the collision counter.
     * @return the current value of the collision counter
     */
    public int getCollisionCounter() {
        return collisionCounter.value();
    }
}
