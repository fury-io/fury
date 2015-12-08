package io.github.fury;

import io.github.fury.entity.Entity;

/**
 * @author fury-io
 */
public interface Archetype {

  /**
   * Builds a new entity for the given archetype. All entities produced by this function
   * must be constructed in the same manner.
   *
   * @return the newly created entity instance.
   */
  Entity build();
}
