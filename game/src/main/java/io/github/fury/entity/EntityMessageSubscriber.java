package io.github.fury.entity;

/**
 * @author fury-io
 */
public interface EntityMessageSubscriber<T extends EntityMessage> {
  void handle(Entity entity, T message);
}
