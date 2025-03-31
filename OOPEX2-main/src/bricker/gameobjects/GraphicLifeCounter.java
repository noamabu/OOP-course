package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a GameObject that visually displays the remaining lives in the game.
 * Extends the GameObject class.
 */
public class GraphicLifeCounter extends GameObject {

    private final Counter heartsCounter;
    private final GameObjectCollection gameObjectCollection;
    private final int numOfLives;
    private final GameObject[] hearts;
    private final Vector2[] heartLocation;
    private final Vector2 dimensions;
    private final Renderable heartImage;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param heartsCounter Counter of number of hearts remain.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Counter heartsCounter,
                              Renderable renderable, GameObjectCollection gameObjectCollection,
                              int numOfLives) {
        super(topLeftCorner, dimensions, renderable);
        this.heartsCounter = heartsCounter;
        this.dimensions = dimensions;
        this.heartImage = renderable;
        this.gameObjectCollection = gameObjectCollection;
        this.numOfLives = numOfLives;
        this.hearts = new GameObject[numOfLives + 2];
        this.heartLocation = new Vector2[numOfLives + 2];
        for (int i = 1; i < numOfLives + 2; ++i) {
            this.heartLocation[i] = new
                    Vector2(topLeftCorner.add(new Vector2(dimensions.x() * i + 5 * i, 0)));
        }
        for (int i = 1; i < numOfLives + 1; ++i) {
            this.hearts[i] = new GameObject(this.heartLocation[i],
                    dimensions, renderable);
            this.gameObjectCollection.addGameObject(this.hearts[i], Layer.BACKGROUND);
        }
        this.hearts[4] = null;
    }

    /**
     * Updates the game state based on the elapsed time since the last update.
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (int i = this.heartsCounter.value() - 1; i > 0; --i) {
            if (this.hearts[i] == null) {
                this.hearts[i] = new GameObject(this.heartLocation[i], dimensions, heartImage);
                this.gameObjectCollection.addGameObject(this.hearts[i], Layer.BACKGROUND);
                break;
            }
        }
        for (int i = this.heartsCounter.value(); i < numOfLives + 1; i++) {
            if (this.hearts[i] != null) {
                this.gameObjectCollection.removeGameObject(this.hearts[i], Layer.BACKGROUND);
                this.hearts[i] = null;
            }

        }
    }
}
