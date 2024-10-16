package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.enums.ItemCapability;

import static game.enums.ItemCapability.UPGRADABLE;

/**
 * A RefreshingFlask item that replenishes stamina upon consumption.
 */
public class RefreshingFlask extends Item implements Consumable, Droppable, Purchasable, Sellable, Upgradable {

  private int upgradeLevel = 0;

  /**
   * Constructor.
   */
  public RefreshingFlask() {
    super("Refreshing Flask", 'u', true);
    this.addCapability(ItemCapability.STAMINA_BOOST);
    this.addCapability(ItemCapability.PURCHASABLE);
    this.addCapability(ItemCapability.SELLABLE);
    this.addCapability(ItemCapability.UPGRADABLE);
  }

  /**
   * Get upgrade level
   * @return level
   */
  public int getUpgradeLevel() {
    return upgradeLevel;
  }

  /**
   * Set upgrade level
   * @param level level
   */
  public void setUpgradeLevel(int level) {
    upgradeLevel = level;
  }

  /**
   * Returns a list with a consume action to let actor consume an item.
   * @param owner the actor that owns the item
   * @return the list
   */
  @Override
  public ActionList allowableActions(Actor owner){
    ActionList actions = new ActionList();
    actions.add(new ConsumeAction(this));
    return actions;
  }

  @Override
  public String consumed(Actor by) {
    String ret = "";

    // recover actor stamina by 20% max stamina
    // Calculate stamina increment based on the upgrade level
    float staminaIncrement = (upgradeLevel == 1) ? 1f : 0.2f;
    ret += consumableManager.consumeStaminaItem(this,by,staminaIncrement);

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
    ret += purchasableManager.purchaseItemNormally(this,by,75,10,-20);
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
    ret += upgradableManager.upgradeItem(this,by,175, getUpgradeLevel());
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
    ret += sellableManager.sellRefreshingFlask(this,by,25,50);
    return ret;
  }
}
