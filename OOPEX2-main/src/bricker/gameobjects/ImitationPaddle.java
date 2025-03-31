package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents an imitation paddle GameObject in the game.
 * Extends the Paddle class.
 */
public class ImitationPaddle extends Paddle{

    private static final int NUM_COLLISION_FOR_DISAPPEAR  = 4;
    private int numCollision;
    private final GameObjectCollection gameObjects;

    /**
     * Indicates whether an imitation paddle exists in the game.
     * If true, there is an imitation paddle in the game; otherwise, there is none.
     */
    public static boolean imitationPaddleExs = false;



    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener
     * @param windowDimensions
     */
    public ImitationPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                           UserInputListener inputListener, Vector2 windowDimensions,
                           GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.gameObjects = gameObjects;
        imitationPaddleExs = true;
        numCollision = 0;
    }

    /**
     * Handles collision events when this object collides with another GameObject.
     * Increments the collision counter.
     * If the collision counter reaches the specified threshold, sets the imitationPaddleExs flag to false
     * and removes this object from the gameObjects collection.
     *
     * @param other     the GameObject with which this object collided
     * @param collision the collision details
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        ++numCollision;
        if(numCollision >= NUM_COLLISION_FOR_DISAPPEAR){
            imitationPaddleExs = false;
            gameObjects.removeGameObject(this);
        }
    }
}


