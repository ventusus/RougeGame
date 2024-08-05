package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface representing a drop.
 */
public interface Droppable {
  /**
   * Droppable Manager
   */
  DroppableManager droppableManager = new DroppableManager();

  /**
   * Drops the item at a specified rate.
   * @param by the actor dropping the item
   * @param at the location of the item to be dropped
   * @param dropRate item drop rate
   * @return String description of item drop
   */
  String dropped(Actor by, Location at, int dropRate);

}
