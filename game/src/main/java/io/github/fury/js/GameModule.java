package io.github.fury.js;

import io.github.fury.entity.Entity;
import io.github.fury.entity.EntityMessage;
import io.github.fury.entity.EntityMessageSubscriber;

import java.util.function.BiPredicate;

/**
 * @author fury-io
 */
public class GameModule implements GlobalModule {

  public <T extends EntityMessage> void subscribe(Class<T> messageClass,
                                                  EntityMessageSubscriber<T> handler) {

  }

  public <T extends EntityMessage> void subscribe(Class<T> messageClass,
                                                  BiPredicate<Entity, EntityMessage> predicate,
                                                  EntityMessageSubscriber<T> handler) {

  }

  public void publish(Entity entity, EntityMessage message) {}

  public void publish(EntityMessage message) {}
}