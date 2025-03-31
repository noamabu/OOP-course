package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * The NullCollisionStrategy class extends BasicCollisionStrategy, providing a no-operation (no-op)
 * implementation
 * of the collision handling process. This strategy is essentially a placeholder that performs no actions upon
 * collisions, serving as a default or "null" behavior in contexts where collision effects are intentionally
 * omitted or deferred. It's particularly useful in scenarios where collisions should be detected but not
 * immediately acted upon, allowing for flexible handling based on game state, conditions, or
 * developer-defined
 * logic.
 * <p>
 * By inheriting from BasicCollisionStrategy, NullCollisionStrategy can be seamlessly integrated into any
 * system
 * that expects a collision strategy but may not require an immediate or standard response to collisions. This
 * approach maintains compatibility with the broader collision handling framework while offering a simple
 * method
 * to bypass or postpone collision effects.
 */

public class NullCollisionStrategy extends BasicCollisionStrategy {
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
    public NullCollisionStrategy(GameObjectCollection gameObjects, Counter counter) {
        super(gameObjects, counter);
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
    }
}
