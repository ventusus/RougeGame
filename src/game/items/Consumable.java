package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Interface representing a consumable item.
 */
public interface Consumable {
  /**
   * Consumable Manager
   */

  ConsumableManager consumableManager = new ConsumableManager();

  /**
   * Consumes this item.
   * @param by the actor consuming
   * @return a String description of effect of consumption
   */
  String consumed(Actor by);
}
