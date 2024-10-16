package game.actors.enemies;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Resettable;
import game.enums.Ability;
import game.enums.Status;
import game.items.HealingVial;
import game.items.RefreshingFlask;
import game.items.Runes;

/**
 * Class representing a HollowSoldier enemy.
 */
public class HollowSoldier extends Enemy implements Resettable {

  private int runesAmount;
  /**
   * Constructor.
   */
  public HollowSoldier(){
    super("Hollow Soldier", '&',200);
    this.runesAmount = 100;
    this.addCapability(Ability.WANDER);
  }


  /**
   * HollowSoldier can use limbs to deal 50 damage with 50% hit rate
   * @return modified intrinsic weapon object
   */
  @Override
  public IntrinsicWeapon getIntrinsicWeapon(){
    int damage = 50;
    int hitRate = 50;
    return new IntrinsicWeapon(damage,"punches", hitRate);
  }


  /**
   * Upon defeat, the HollowSoldier has a 20% chance to drop a HealingVial
   * and a 30% chance to drop a RefreshingFlask.
   * @param actor the perpetrator
   * @param map where the actor fell unconscious
   * @return string describing actor death
   */
  @Override
  public String unconscious(Actor actor, GameMap map) {

    HealingVial healingVial = new HealingVial();
    RefreshingFlask refreshingFlask = new RefreshingFlask();
    Runes runes = new Runes(this.runesAmount);
    String ret = "";

    ret += this + " met their demise in the hand of " + actor;
    ret += healingVial.dropped(this, map.locationOf(this), 20);
    ret += refreshingFlask.dropped(this, map.locationOf(this), 30);
    ret += runes.dropped(this,map.locationOf(this), this.runesDropRate);
    map.removeActor(this);
    return ret;

  }


  /**
   * Reset function for the related object.
   */
  @Override
  public void reset() {
    this.addCapability(Status.RESET);
  }
}
