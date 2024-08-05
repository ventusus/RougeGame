package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Resettable;
import game.enums.Ability;
import game.enums.Status;
import game.enums.Weather;
import game.items.HealingVial;
import game.items.Runes;
import game.utils.weathers.WeatherControllable;
/**
 * Class representing a ForestKeeper enemy.
 */
public class ForestKeeper extends Enemy implements WeatherControllable, Resettable {

  private Display display = new Display();

  private int runesAmount;

  /**
   * Constructor
   */
  public ForestKeeper(){
    super("Forest Keeper", '8',125);
    this.addCapability(Ability.FOLLOW);
    this.runesAmount = 50;
    this.addCapability(Ability.WANDER);
  }

  /**
   * ForestKeeper can use limbs to deal 25 damage with 75% hit rate.
   * @return intrinsic weapon item
   */
  @Override
  public IntrinsicWeapon getIntrinsicWeapon(){
    int damage = 25;
    int hitRate = 75;
    return new IntrinsicWeapon(damage, "punches", hitRate);
  }

  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    weatherEffect(weatherManager.getCurrWeather());
    return super.playTurn(actions,lastAction,map,display);
  }

  /**
   * Upon defeat, the ForestKeeper has a 10% chance to drop a HealingVial.
   * @param actor the perpetrator
   * @param map where the actor fell unconscious
   * @return string describing item drop
   */
  @Override
  public String unconscious(Actor actor, GameMap map){

    HealingVial healingVial = new HealingVial();
    Runes runes = new Runes(this.runesAmount);

    int healingVialDropRate = 20;
    String ret = "";

    ret += this + " met their demise in the hand of " + actor;
    ret += healingVial.dropped(this, map.locationOf(this), healingVialDropRate);
    ret += runes.dropped(this,map.locationOf(this),this.runesDropRate);

    map.removeActor(this);
    return ret;
  }

  @Override
  public void weatherEffect(Weather weather) {
    // if the weather allows and the actor doesn't have max health, heal
    if (weather == Weather.RAINY && (this.getAttribute(BaseActorAttributes.HEALTH) < this.getAttributeMaximum(BaseActorAttributes.HEALTH))){
      int healPoints = 10;
      this.heal(healPoints);
      display.println(String.format("%s feels rejuvenated", this));
    }
  }


  /**
   * Reset function for the related object.
   */
  @Override
  public void reset() {
    this.addCapability(Status.RESET);
  }
}
