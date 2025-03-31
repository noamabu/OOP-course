package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * The BasicCollisionStrategy class implements the CollisionStrategy interface, providing a foundational
 * approach
 * to handling collisions within a game environment. It is designed to serve as the base logic for collision
 * events, offering a straightforward mechanism for responding to collisions by removing the colliding object
 * from the game scene and adjusting a counter, which could represent scores, lives, or any other
 * game-relevant
 * metric. This strategy encapsulates the essential collision handling that can be extended or customized by
 * more specific strategies for different game scenarios.
 * <p>
 * The class is built around the concept of simplicity and modifiability, allowing it to be a part of a larger
 * collision handling system where different behaviors are required for different types of collisions. It uses
 * a GameObjectCollection to manage the objects involved in collisions and a Counter to track the numerical
 * impacts of these events, such as decrementing a score or a lives counter.
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjects;
    private final Counter counter;
    private boolean isNotCollision;

    /**
     * Constructs a new BasicCollisionStrategy instance.
     * This strategy serves as a fundamental approach to handling collisions within the game,
     * potentially involving modifications to the game's state or the behavior of game objects
     * upon collisions. It provides a framework for more specific collision handling strategies.
     *
     * @param gameObjects The collection of all game objects, allowing the strategy to interact with
     *                    or modify the game's current objects as necessary upon collisions.
     * @param counter     A counter object that may be used to track occurrences, scores, or other
     *                    numeric values affected by collisions.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter counter) {

        this.gameObjects = gameObjects;
        this.counter = counter;
        this.isNotCollision = true;
    }

    /**
     * Handles the collision event between two GameObjects by removing the colliding object and
     * decrementing a counter.
     * This method is invoked upon a collision between this object and another object. It removes the
     * colliding object from the game and adjusts a counter, typically used to track the number of
     * objects or score within the game, by decreasing it by one.
     *
     * @param thisObj  The GameObject associated with this strategy that is involved in the collision.
     * @param otherObj The GameObject that collides with thisObj. This parameter is present for compatibility
     *                 with collision handling interfaces but is not used in this method.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        if (this.isNotCollision) {
            counter.increaseBy(-1);
            this.isNotCollision = false;
        }

    }

    /**
     * Retrieves the collection of game objects associated with this strategy.
     * This method allows access to the internal collection of GameObjects that this strategy
     * may interact with or modify during its operation. It provides a way to obtain a reference
     * to all current game objects for inspection, modification, or other interactions.
     *
     * @return The current collection of GameObjects.
     */
    public GameObjectCollection getGameObject() {
        return this.gameObjects;
    }
}
