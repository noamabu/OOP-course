package bricker.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Random;

/**
 * Represents a puck GameObject in the game.
 * Extends the Ball class.
 */
public class Puck extends Ball{

    private static final int BALL_SPEED = 150;
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
     * @param collisionSound   the sunde create in collision
     * @param windowDimensions
     * @param gameObjects
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                Vector2 windowDimensions, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.setVelocity(setRandomVelocity());
    }

    /**
     * Generates a random velocity vector for the ball.
     * The magnitude of the velocity is constant (BALL_SPEED), and the direction is random.
     *
     * @return a Vector2 representing the random velocity of the ball
     */
    private Vector2 setRandomVelocity(){
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * BALL_SPEED;
        return new Vector2(velocityX, velocityY);
    }

    /**
     * Updates the game state based on the elapsed time since the last update.
     * Calls the superclass update method and removes the puck from the game objects collection
     * if it goes below the bottom edge of the game window.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(this.windowDimensions.y() < this.getCenter().y()){
            this.gameObjects.removeGameObject(this);
        }
    }
}
