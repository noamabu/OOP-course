package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;


/**
 * The AddPuckCollisionStrategy class implements the CollisionStrategyDecorator interface to enrich the
 * collision handling
 * mechanism by adding pucks into the game environment upon specific collision events. It extends the
 * game's interaction
 * dynamics by integrating both visual and auditory feedback when collisions that trigger puck additions
 * occur.
 * This strategy encapsulates the logic for the initial collision handling through a basic collision
 * strategy and
 * further processes the event to introduce new pucks into the game, enhancing the gameplay experience with
 * additional
 * interactive elements.
 * <p>
 * The strategy uses predefined resources, such as images and sounds, to create a more immersive and
 * engaging game
 * environment. Each puck added to the game follows the collision's location, maintaining the continuity
 * and realism
 * of the game's physics. This approach not only diversifies the gameplay but also introduces new
 * challenges and
 * strategies into the game dynamics.
 */
public class AddPuckCollisionStrategy implements CollisionStrategyDecorator {
    private static final String PATH_PUCK = "assets/mockBall.png";
    private static final String PATH_SOUND = "assets/blop_cut_silenced.wav";
    private static final int NUM_OF_PUCK = 2;
    private static final float PUCK_LENGTH = 15;
    private final GameObjectCollection gameObjects;
    private final CollisionStrategy basicCollisionStrategy;
    private final ImageRenderable image;
    private final Sound sound;
    private final Vector2 windowDimensions;

    /**
     * Constructs a new instance of AddPuckCollisionStrategy.
     * This strategy is responsible for handling collisions with the intention of adding a puck to the game
     * scene upon certain collision events. It integrates basic collision handling with the addition of visual
     * and auditory feedback through an image and sound associated with the puck.
     *
     * @param gameObjects            The collection of game objects where the puck might be added.
     * @param basicCollisionStrategy The basic collision handling strategy to be used before any specific puck
     *                               logic.
     * @param imageReader            The utility to read image resources, used here to load the puck's image.
     * @param soundReader            The utility to read sound resources, used here to load the puck's
     *                               associated sound.
     * @param windowDimensions
     */
    public AddPuckCollisionStrategy(GameObjectCollection gameObjects,
                                    CollisionStrategy basicCollisionStrategy,
                                    ImageReader imageReader,
                                    SoundReader soundReader, Vector2 windowDimensions) {
        this.gameObjects = gameObjects;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.image = imageReader.readImage(PATH_PUCK, true);
        this.sound = soundReader.readSound(PATH_SOUND);
        this.windowDimensions = windowDimensions;
    }

    /**
     * Handles the collision event between two GameObjects in the context of adding pucks.
     * This method is invoked when a collision occurs between this object and another GameObject.
     * Initially, it delegates the collision handling to a predefined basic collision strategy.
     * Subsequently, it generates a specified number of pucks, placing each at the position of the
     * current object.
     * The newly created pucks are then added to the game's collection of objects.
     *
     * @param thisObj  The GameObject this method is a part of, involved in the collision.
     * @param otherObj The GameObject that thisObj collides with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        for (int i = 0; i < NUM_OF_PUCK; i++) {
            Vector2 topLeftCorner = new Vector2(thisObj.getTopLeftCorner());
            Vector2 dimensions = new Vector2(PUCK_LENGTH, PUCK_LENGTH);
            Puck puck = new Puck(topLeftCorner, dimensions, this.image, this.sound, this.windowDimensions,
                    this.gameObjects);
            this.gameObjects.addGameObject(puck);
        }
    }
}
