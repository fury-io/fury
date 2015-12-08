package io.github.fury;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import io.github.fury.entity.Entity;
import io.github.fury.entity.EntityMessage;
import io.github.fury.entity.EntityMessageSubscriber;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A game system is a organization of logic and subsystems that can be enabled and disabled.
 *
 * @author fury-io
 */
public class GameSystem {

  private Map<String, GameSystem> systems = new LinkedHashMap<>();

  /**
   * The message subscribers for this system. One to many relationship. Each subscriber
   * must receive a published message to this system.
   */
  private Multimap<Class<? extends EntityMessage>, EntityMessageSubscriber> messageSubscribers
          = HashMultimap.create();

  private boolean disabled;

  /**
   *
   * @param messageClass
   * @param subscriber
   * @param <T>
   */
  public <T extends EntityMessage> void subscribe(Class<T> messageClass,
                                                  EntityMessageSubscriber<T> subscriber) {
    subscribe(messageClass, (entity) -> true, subscriber);
  }

  /**
   *
   * @param messageClass
   * @param predicate
   * @param subscriber
   * @param <T>
   */
  public <T extends EntityMessage> void subscribe(Class<T> messageClass,
                                                  Predicate<Entity> predicate,
                                                  EntityMessageSubscriber<T> subscriber) {
    EntityMessageSubscriber<T> wrapper = (entity, message) -> {
      if(predicate.test(entity)) {
        subscriber.handle(entity, message);
      }
    };
    messageSubscribers.put(messageClass, wrapper);
  }

  /**
   * Publishes a message for an entity first to each registered {@link EntityMessageSubscriber} for
   * the given type if any exist, and then to each subsystem. If a subsystem is disabled, the
   * message will not be published to that subsystem. The order that the messages are published
   * to subsystems is dependant on the order to which they were registered to this system.
   *
   * @param entity the entity to publish the message for.
   * @param message the message to publish.
   */
  public void publish(Entity entity, EntityMessage message) {
    for(EntityMessageSubscriber handler : messageSubscribers.get(message.getClass())) {
      handler.handle(entity, message);
    }

    for(GameSystem subsystem : systems.values()) {
      if(subsystem.isDisabled()) {
        subsystem.publish(entity, message);
      }
    }
  }

  /**
   * Gets if the system is currently disabled.
   *
   * @return if the system is disabled.
   */
  public boolean isDisabled() {
    return disabled;
  }

  /**
   * Creates a new game system for the provided namespace. A namespace must be formatted in keys
   * which are separated by periods. These keys represent a tree relationship of systems with the keys
   * from the start to the end of the string being the bottom to the top level of the tree.
   * This namespace provided to this function is case insensitive.
   *
   * @param namespace the system namespace.
   * @return the system for the namespace or a new {@link GameSystem} for the provided namespace.
   */
  public GameSystem createSystem(String namespace) {
    namespace = namespace.toLowerCase();
    int index = namespace.indexOf('.');
    if(index == -1) {
      return systems.putIfAbsent(namespace, new GameSystem());
    }

    return systems.putIfAbsent(namespace.substring(0, index), new GameSystem())
            .createSystem(namespace.substring(index + 1));
  }

  /**
   * Gets a system for a provided namespace. A namespace must be formatted in keys which are
   * separated by periods. These keys represent a tree relationship of systems with the keys
   * from the start to the end of the string being the bottom to the top level of the tree.
   * This namespace provided to this function is case insensitive.
   *
   *
   * @param namespace the system namespace.
   * @return the system for the namespace or {@code null} if the system does not exist.
   */
  public GameSystem getSystem(String namespace) {
    namespace = namespace.toLowerCase();
    int index = namespace.indexOf('.');
    if(index == -1) {
      return systems.get(namespace);
    }

    String bottom = namespace.substring(0, index);
    if(!systems.containsKey(bottom)) {
      return null;
    }
    return systems.get(bottom).getSystem(namespace.substring(index + 1));
  }
}
