package bricker.brick_strategies;

import danogl.GameObject;

/**
 * The CollisionStrategy interface defines a contract for handling collisions between game objects within a
 * game environment.
 * It specifies a single method, onCollision, which must be implemented by any class that manages the
 * interactions and outcomes
 * of collisions between game objects. The implementation of this method allows for a wide range of
 * behaviors in response to
 * collisions, tailored to the specific mechanics and requirements of the game.
 * <p>
 * Implementations of this interface can significantly vary, providing flexibility in how collisions affect
 * the game state,
 * including but not limited to, modifying the properties or states of the involved objects, triggering
 * various game events,
 * or implementing physical reactions such as bouncing, breaking, or other forms of response. This method
 * provides the necessary
 * parameters to identify the objects involved in a collision, enabling customized logic based on the
 * characteristics or types
 * of those objects.
 */

public interface CollisionStrategy {
    /**
     * Defines the behavior when a collision occurs between two game objects.
     * This method is meant to be implemented by classes that handle the interactions
     * between game objects upon collision. The specific actions taken can vary widely,
     * depending on the nature of the objects involved and the desired game mechanics.
     * Implementations might include changing object states, triggering events, or
     * applying physical responses like bouncing or breaking.
     *
     * @param thisObj  The first GameObject involved in the collision. This is typically
     *                 the object on which the collision method is being called.
     * @param otherObj The second GameObject involved in the collision. This object is
     *                 the one that thisObj has collided with.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj);
}
