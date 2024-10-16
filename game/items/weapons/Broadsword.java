package game.items.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.AttackAction;
import game.actions.Focus;
import game.enums.ItemCapability;
import game.enums.Status;
import game.items.Purchasable;
import game.items.Sellable;
import game.items.Upgradable;

/**
 * Starting weapon that can be picked up and used to attack enemies.
 * Deals 110 damage, with 80% attack accuracy
 * Special Skill: FOCUS
 * - Increases damage by 10% max damage, with 90% attack accuracy.
 * - Lasts 5 turns.
 */
public class Broadsword extends WeaponItem implements Purchasable, Sellable, Upgradable {

  private final float skillDmgMultiplier = 1.1f;
  private final int skillHitRate = 90;
  private final int numOfTurns = 5;
  private final int skillStaminaCost = 20;
  private final Focus focusAction;
  private final int defaultHitRate;
  private int upgradeLevel = 0;

  /**
   * Constructor.
   */
  public Broadsword() {
    super("Broadsword", '1', 110, "slashes", 80);
    this.focusAction = new Focus(this, skillDmgMultiplier, skillHitRate, skillStaminaCost,
        numOfTurns);
    // save default hit rate of sword
    this.defaultHitRate = super.chanceToHit();
    this.addCapability(ItemCapability.PURCHASABLE);
    this.addCapability(ItemCapability.SELLABLE);
    this.addCapability(ItemCapability.UPGRADABLE);
  }

  @Override
  public int damage() {
    int baseDamage = super.damage();
    // Calculate damage including upgrades
      if (focusAction.isActive()) {
        return Math.round((baseDamage+ (upgradeLevel * 10)));
      } else {
        return baseDamage + (upgradeLevel * 10);
      }
  }

  public void upgradeLvl() {
    upgradeLevel++;
  }

  /**
   * Returns a list with the broadsword's special skill, Focus.
   * @param owner the actor that owns the item
   * @return the list
   */
  @Override
  public ActionList allowableActions(Actor owner){
    ActionList actions = new ActionList();
    actions.add(focusAction);
    return actions;
  }

  /**
   * Returns a list with an attack action to be performed by the actor to another actor.
   * @param otherActor the other actor
   * @param location the location of the other actor
   * @return the list
   */
  @Override
  public ActionList allowableActions(Actor otherActor, Location location){

    ActionList actions = new ActionList();
      // otherActor: target to attack
    if (!otherActor.hasCapability(Status.FRIENDLY)) {
      actions.add(new AttackAction(otherActor, location.toString(), this));
    }
    return actions;
  }

  /**
   * Keeps track of special skill usage.
   * @param currentLocation The location of the actor carrying this Item.
   * @param actor The actor carrying this Item.
   */
  @Override
  public void tick(Location currentLocation, Actor actor){

    if (focusAction.isActive()){
      // if there are turns left of the skill
      if (focusAction.getTurnsRemaining() != 0){
        // decrement remaining turns
        focusAction.decrementTurnsRemaining();
      }
      // if skill was previously active but no more turns remain
      else {
        // deactivate skill and reset weapon stats
        focusAction.setActive(false);
        focusAction.setTurnsRemaining(0);
        focusAction.resetWeaponStats(defaultHitRate);
      }
    }
  }


  /**
   * Purchases this item.
   *
   * @param by the actor purchasing
   * @return a String description of effect of purchasing
   */
  @Override
  public String purchased(Actor by) {
    String ret = "";
    ret += purchasableManager.purchaseBroadsword(this,by,250,5);
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
    upgradeLvl();
    ret += upgradableManager.upgradeWeapon(this,by,1000);
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
    ret += sellableManager.sellBroadsword(this,by,100);
    return ret;
  }
}
