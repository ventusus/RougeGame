package game.items;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.ItemCapability;

/**
 * An old key item. Looks like it could unlock something.
 */
public class OldKey extends Item implements Droppable{

  /**
   * Constructor.
   */
  public OldKey() {
    super("Old Key", '-', true);
    this.addCapability(ItemCapability.UNLOCK_GATE);
  }
  /**
   * Drops the item at a specified rate.
   * @param by the actor dropping the item
   * @param at the location of the item to be dropped
   * @param dropRate item drop rate
   * @return String description of item drop
   */

  @Override
  public String dropped(Actor by, Location at, int dropRate) {
    // drop rate of 25% by wandering undead
    String ret = "";
    ret += droppableManager.dropItem(this,by,at,dropRate);

    return ret;

  }







}
