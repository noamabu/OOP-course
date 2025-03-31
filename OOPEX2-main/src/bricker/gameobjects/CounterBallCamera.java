package bricker.gameobjects;

import bricker.brick_strategies.CameraChangingStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;

/**
 * Represents a GameObject that monitors collisions of a Ball and triggers camera changes accordingly.
 * Extends the GameObject class.
 */
public class CounterBallCamera extends GameObject {
    private static final int ERASE_CAMERA_CHANGE = 4;
    private final Ball ball;
    private final CameraChangingStrategy cameraChangingStrategy;
    private boolean needToDelete;
    private int numCollision;


    /**
     * Construct a new GameObject instance.
     *
     */
    public CounterBallCamera(Ball ball, CameraChangingStrategy cameraChangingStrategy) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.cameraChangingStrategy = cameraChangingStrategy;
        this.needToDelete = true;
        this.numCollision = ball.getCollisionCounter() + ERASE_CAMERA_CHANGE;
    }

    /**
     * Updates the game state based on the elapsed time since the last update.
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.ball.getCollisionCounter() >= this.numCollision && this.needToDelete){
            cameraChangingStrategy.endCameraChange();
            this.needToDelete = false;
        }
    }
}
