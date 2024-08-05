package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.enums.ItemCapability;

import static game.enums.ItemCapability.UPGRADABLE;

/**
 * A HealingVial item that replenishes health upon consumption.
 */
public class HealingVial extends Item implements Consumable, Droppable, Purchasable, Sellable, Upgradable {

  private int upgradeLevel = 0;

  /**
   * Constructor.
   */
  public HealingVial(){
    super("Healing Vial", 'a', true);
    this.addCapability(ItemCapability.HEAL);
    this.addCapability(ItemCapability.PURCHASABLE);
    this.addCapability(ItemCapability.SELLABLE);
    this.addCapability(ItemCapability.UPGRADABLE);
  }

  /**
   * Get healing vial's level
   * @return level
   */
  public int getUpgradeLevel() {
    return upgradeLevel;
  }

  /**
   * Set healing vial's level
   * @param level level
   */
  public void setUpgradeLevel(int level) {
    upgradeLevel = level;
  }

  /**
   * Returns a list with a consume action to let actor consume an item.
   * @param actor the actor that owns the item
   * @return the list
   */
  @Override
  public ActionList allowableActions(Actor actor){
    ActionList actions = new ActionList();
    actions.add(new ConsumeAction(this));
    return actions;
  }

  /**
   * Consume function
   * @param by the actor consuming
   * @return the string of function
   */

  @Override
  public String consumed(Actor by){

    String ret = "";

    // heal actor by 10% max health
    // Calculate health increment based on the upgrade level
    float hpIncrement = (upgradeLevel == 1) ? 0.8f : 0.1f;
    ret += consumableManager.consumeHealingItem(this,by,hpIncrement);

    return ret;
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
    // drop rate of 20% by hollow soldier and wandering undead
    String ret = "";
    ret += droppableManager.dropItem(this,by,at,dropRate);

    return ret;
  }

  /**
   * Purchases this item.
   *
   * @param by the actor purchasing
   * @return a String description of effect of purchase
   */
  @Override
  public String purchased(Actor by) {
    String ret = "";
    ret += purchasableManager.purchaseItemNormally(this,by,100,25,50);
    return ret;
  }

  /**
   * Upgrades this item.
   *
   * @param by the actor upgrading
   * @return a String description of effect of upgrading
   */
  @Override
  public String upgraded(Actor by) {
    String ret = "";
    ret += upgradableManager.upgradeItem(this,by,250, getUpgradeLevel());
    setUpgradeLevel(1);
    removeCapability(UPGRADABLE);
    return ret;
  }

  /**
   * Sells this item.
   *
   * @param by the actor selling
   * @return a String description of effect of selling
   */
  @Override
  public String sold(Actor by) {
    String ret = "";
    ret += sellableManager.sellHealingVial(this,by,35,10);
    return ret;
  }
}
