package bricker.gameobjects;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a brick GameObject in the game.
 * Extends the GameObject class.
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy basicCollisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = basicCollisionStrategy;
    }
    /**
     * Handles collision events when this object collides with another GameObject.
     * @param other     the GameObject with which this object collided
     * @param collision the collision details
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }
}
