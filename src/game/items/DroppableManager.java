package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Manager class to perform item drop.
 */
public class DroppableManager {

  /**
   * Drops an item on the location specified
   * @param item the item to drop
   * @param by the actor dropping the item
   * @param at the location of drop
   * @param dropRate rate of drop
   * @return String informing player of drop (if the item is dropped) or an empty string otherwise
   */
  public String dropItem(Item item, Actor by, Location at, int dropRate) {

    if (Math.random() <= (dropRate / 100.0)) {
      at.addItem(item);
      return String.format("\n%s left something behind.", by);
    } else {
      return "";
    }
  }

}
