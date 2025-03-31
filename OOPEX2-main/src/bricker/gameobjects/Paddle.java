package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents a paddle GameObject in the game.
 * Extends the GameObject class.
 */
public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 200;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int MIN_DISTANCE_FROM_SCREEN_EDGE = 5;

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
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Updates the game state based on the elapsed time since the last update.
     * Adjusts the velocity of the GameObject based on the user input to move left or right.
     * Ensures the GameObject stays within the screen bounds by adjusting its position if it gets too close
     * to the screen edges.
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
        if (this.getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            float currYPosition = this.getTopLeftCorner().y();
            this.transform().setTopLeftCorner(5F,
                    currYPosition);
        }
        if (this.getTopLeftCorner().x() >
                windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x()) {
            float currYPosition = this.getTopLeftCorner().y();
            this.transform().setTopLeftCorner(windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE
                    - getDimensions().x(), currYPosition);
        }
    }
}
