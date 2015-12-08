package io.github.fury.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fury-io
 */
public class Entity {

  /**
   * The components that comprise this entity. Only one component of each type is allowed to be
   * registered at any given time.
   */
  private Map<Class<? extends EntityComponent>, EntityComponent> components = new HashMap<>();

  public EntityComponent addComponent(EntityComponent component) {
    return components.put(component.getClass(), component);
  }

  /**
   * Gets if the entity has a component given its provided type.
   *
   * @param componentClass the component class.
   * @param <T>            the generic component type.
   * @return if the entity has a component of the provided type.
   */
  public <T extends EntityComponent> boolean hasComponent(Class<T> componentClass) {
    return components.containsKey(componentClass);
  }
}
