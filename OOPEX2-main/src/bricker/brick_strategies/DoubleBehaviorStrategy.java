package bricker.brick_strategies;

import danogl.GameObject;

/**
 * The DoubleBehaviorStrategy class implements the CollisionStrategyDecorator interface, encapsulating not
 * one, but three distinct
 * collision strategies to be applied in sequence or under specific conditions during a collision event.
 * This design allows for a
 * highly customizable and layered approach to handling collisions, enabling the combination of multiple
 * behaviors or effects into a
 * single, cohesive response mechanism. Such a setup is particularly useful for creating complex game
 * dynamics where the outcome of
 * collisions can vary significantly based on the context or the types of objects involved.
 * <p>
 * By aggregating a basic collision strategy with two additional strategies (and optionally a third), this
 * class offers a versatile
 * framework for defining nuanced collision interactions. Each strategy can introduce a separate layer of
 * behavior, such as altering
 * object trajectories, triggering special effects, or modifying game state, thereby enriching the overall
 * game experience with depth
 * and variety.
 */
public class DoubleBehaviorStrategy implements CollisionStrategyDecorator {

    private final BasicCollisionStrategy basicCollisionStrategy;
    private final CollisionStrategy first;
    private final CollisionStrategy second;
    private final CollisionStrategy third;

    /**
     * Constructs a new instance of DoubleBehaviorStrategy with three specified collision strategies.
     * This strategy allows for combining multiple behaviors into a single collision response,
     * enabling complex interactions between game objects. Each of the provided strategies
     * represents a different aspect of the collision behavior, allowing for layered or
     * sequential application of effects or actions upon a collision event.
     *
     * @param basicCollisionStrategy
     * @param first                  The first CollisionStrategy to apply. This could define the primary
     *                               behavior
     *                               upon collision, such as reflecting an object's movement or triggering
     *                               a game event.
     * @param second                 The second CollisionStrategy to apply. This strategy can complement or
     *                               extend
     *                               the behavior defined by the first, adding complexity or additional
     *                               effects.
     * @param third                  The third CollisionStrategy to apply. This further enhances the collision
     *                               response, offering a third layer of behavior or action following the
     *                               first
     *                               and second strategies.
     */
    public DoubleBehaviorStrategy(BasicCollisionStrategy basicCollisionStrategy, CollisionStrategy first,
                                  CollisionStrategy second,
                                  CollisionStrategy third) {
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Executes the collision behavior defined by the three aggregated collision strategies
     * when a collision occurs between two game objects. This method sequentially applies
     * the first and second strategies' collision responses, and conditionally applies the third strategy
     * if it is not null. This allows for a flexible and layered approach to collision handling,
     * enabling a combination of behaviors based on the specific game logic or requirements.
     * The method can be tailored to support a wide range of interactions, from simple physical
     * responses to complex game events or state changes.
     *
     * @param thisObj  The GameObject initiating the collision; this is the object on which
     *                 the collision method is invoked.
     * @param otherObj The GameObject with which thisObj has collided. The interaction between
     *                 thisObj and otherObj is determined by the implementation of the collision
     *                 strategies.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        first.onCollision(thisObj, otherObj);
        second.onCollision(thisObj, otherObj);
        if (this.third != null) {
            this.third.onCollision(thisObj, otherObj);
        }
    }
}
