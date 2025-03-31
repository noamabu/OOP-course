package bricker.brick_strategies;


/**
 * The CollisionStrategyDecorator interface extends the CollisionStrategy interface, following the
 * Decorator pattern.
 * This pattern allows for the dynamic addition of responsibilities to objects without modifying their
 * structure.
 * Classes implementing this interface are expected to enhance or modify the behavior of collision handling
 * defined in the CollisionStrategy interface. This could involve adding new behaviors or effects in response
 * to collisions, such as sound effects, visual effects, or alternative collision responses, while still
 * maintaining
 * the core collision handling logic.
 * <p>
 * Implementers of this interface should ensure that they forward calls to the decorated CollisionStrategy
 * object,
 * thereby allowing multiple decorators to be stacked, each adding its layer of functionality. This approach
 * promotes a highly flexible and extensible design for managing collision behaviors in a game environment.
 */

public interface CollisionStrategyDecorator extends CollisionStrategy {
};
