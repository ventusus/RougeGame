package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.enums.Ability;
import game.enums.Status;
import game.enums.Weather;
import game.items.HealingVial;
import game.items.Runes;
import game.utils.weathers.WeatherControllable;


/**
 * Class representing a HollowSoldier enemy.
 */
public class RedWolf extends Enemy implements WeatherControllable {

  private Display display = new Display();
  private final float SUNNY_DAMAGE_MULTIPLIER = 3.0f;
  private final float DEFAULT_DAMAGE_MULTIPLIER = 1.0f;
  private float damageMultiplier;
  private int runesAmount;

  /**
   * Constructor.
   */
  public RedWolf(){
    super("Red Wolf", 'r',25);
    this.addCapability(Ability.FOLLOW);
    this.damageMultiplier = DEFAULT_DAMAGE_MULTIPLIER;
    this.runesAmount = 25;
    this.addCapability(Ability.WANDER);
  }


  /**
   * RedWolf can bite player to deal 15 damage with 80% hit rate.
   * @return intrinsic weapon item
   */
  @Override
  public IntrinsicWeapon getIntrinsicWeapon(){
    int damage = Math.round(15 * damageMultiplier);
    int hitRate = 80;
    return new IntrinsicWeapon(damage,"bites", hitRate);
  }

  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    weatherEffect(weatherManager.getCurrWeather());
    return super.playTurn(actions,lastAction,map,display);
  }


  /**
   * Upon defeat, the RedWolf has a 10% chance to drop a HealingVial
   * @param actor the perpetrator
   * @param map where the actor fell unconscious
   * @return string describing drop
   */
  @Override
  public String unconscious(Actor actor, GameMap map) {
    int healingVialDropRate = 10;
    HealingVial healingVial = new HealingVial();
    Runes runes = new Runes(this.runesAmount);
    String ret = "";

    ret += this + " met their demise in the hand of " + actor;
    ret += healingVial.dropped(this, map.locationOf(this), healingVialDropRate);
    ret += runes.dropped(this,map.locationOf(this), this.runesDropRate);

    map.removeActor(this);
    return ret;
  }

  @Override
  public void weatherEffect(Weather weather) {
    // Effect: when weather sunny, red wolf deals 3x original damage
    if (weather == Weather.SUNNY){
      this.updateDamageMultiplier(SUNNY_DAMAGE_MULTIPLIER);
      display.println("The Red Wolves are becoming more aggressive...");
    }
    else {
      this.updateDamageMultiplier(DEFAULT_DAMAGE_MULTIPLIER);
      display.println("The Red Wolves are becoming less aggressive...");
    }
  }

  @Override
  public void updateDamageMultiplier(float newMultiplier){
    this.damageMultiplier = newMultiplier;
  }

  /**
   * Reset function for the related object.
   */
  @Override
  public void reset() {
    this.addCapability(Status.RESET);
  }
}
