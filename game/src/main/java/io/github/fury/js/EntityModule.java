package io.github.fury.js;

import com.google.common.collect.Sets;

import io.github.fury.entity.Entity;
import io.github.fury.entity.EntityComponent;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author Hadyn Fitzgerald
 */
public class EntityModule implements GlobalModule {

  public Predicate<Entity> allComponents(
          Class<? extends EntityComponent>... componentTypes) {
    Set<Class<? extends EntityComponent>> classes = Sets.newHashSet(componentTypes);
    return (entity) -> classes.stream().allMatch(type -> entity.hasComponent(type));
  }
}
