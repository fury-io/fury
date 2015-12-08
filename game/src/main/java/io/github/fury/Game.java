package io.github.fury;

import io.github.fury.entity.Entity;
import io.github.fury.entity.EntityMessage;

/**
 * @author Hadyn Fitzgerald
 */
public class Game {

  private World world = new World();

  /**
   * The root system which is the parent of all systems for the game.
   */
  private GameSystem root = new GameSystem();

  /**
   * Publishes a message for every active entity. The order in which the message is published for an
   * entity is not orderly.
   *
   * @param message the message to publish.
   */
  public void publish(EntityMessage message) {
    for (Entity entity : world.getEntities()) {
      publish(entity, message);
    }
  }

  /**
   * Publishes a message for an entity to the game instance. Publishes the message to every
   * registered system. The order in which the message is published to various systems depends on
   * the order in which they were registered to the game instance. Messages will not be published to
   * disabled systems.
   *
   * @param entity  the entity the message is to be published with.
   * @param message the message to publish.
   */
  public void publish(Entity entity, EntityMessage message) {
    root.publish(entity, message);
  }

  /**
   * Creates a new game system for the provided namespace. A namespace must be formatted in keys
   * which are separated by periods. These keys represent a tree relationship of systems with the
   * keys from the start to the end of the string being the bottom to the top level of the tree.
   * This namespace provided to this function is case insensitive.
   *
   * @param namespace the system namespace.
   * @return the system for the namespace if it already exists or a new {@link GameSystem} for the
   * provided namespace.
   */
  public GameSystem createSystem(String namespace) {
    return root.createSystem(namespace);
  }

  /**
   * Gets a system for a provided namespace. A namespace must be formatted in keys which are
   * separated by periods. These keys represent a tree relationship of systems with the keys from
   * the start to the end of the string being the bottom to the top level of the tree. This
   * namespace provided to this function is case insensitive.
   *
   * @param namespace the system namespace.
   * @return the system for the namespace or {@code null} if the system does not exist.
   */
  public GameSystem getSystem(String namespace) {
    return root.getSystem(namespace);
  }
}
