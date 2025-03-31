package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static java.awt.Color.*;

/**
 * Represents a numeric life counter GameObject in the game.
 * Extends the GameObject class.
 */
public class NumericLifeCounter extends GameObject {
    private final Counter counter;
    private final GameObjectCollection gameObjectCollection;
    private final GameObject numericLife;
    private final TextRenderable TextRenderable;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     *
     */
    public NumericLifeCounter(Counter counter, Vector2 topLeftCorner,
                              Vector2 dimensions, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.counter = counter;
        this.gameObjectCollection = gameObjectCollection;
        this.TextRenderable = new TextRenderable(Integer.toString(counter.value()));
        this.numericLife = new GameObject(topLeftCorner, dimensions, TextRenderable);
        this.gameObjectCollection.addGameObject(numericLife, Layer.BACKGROUND);
    }

    /**
     * Updates the game state based on the elapsed time since the last update.
     * Updates the TextRenderable to display the current value of the counter and sets its
     * color based on the counter value.
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(float deltaTime) {
        if (this.counter.value() >= 3){
            TextRenderable.setString(Integer.toString(this.counter.value()));
            TextRenderable.setColor(green);
        }
        else if (this.counter.value() == 2) {
            TextRenderable.setString(Integer.toString(this.counter.value()));
            TextRenderable.setColor(yellow);
        }
        else {
            TextRenderable.setString(Integer.toString(this.counter.value()));
            TextRenderable.setColor(red);
        }
        super.update(deltaTime);
    }
}
