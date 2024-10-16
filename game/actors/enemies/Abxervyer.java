package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.ResetManager;
import game.Resettable;
import game.enums.Status;
import game.enums.Weather;
import game.grounds.LockedGate;
import game.items.Runes;
import game.utils.weathers.WeatherControllable;
import game.enums.Ability;
import game.utils.FancyMessage;

/**
 * Class representing a Abxervyer boss enemy.
 */
public class Abxervyer extends Enemy implements WeatherControllable, Resettable {

  private LockedGate lockedGate;
  private int runesAmount;

  /**
   * After the Abxervyer defeated, a locked gate will be appear.
   * @param lockedGate a locked gate will be appeared
   */
  public Abxervyer(LockedGate lockedGate){
    super("Abxervyer, The Forest Watcher", 'Y',2000);
    this.lockedGate = lockedGate;
    this.runesAmount = 5000;
    this.addCapability(Ability.FOLLOW);
    this.addCapability(Ability.VOID_IMMUNE);
    this.addCapability(Ability.WANDER);
  }


  /**
   * Abxervyer can change the weather.
   * @param weather weather to change to
   */
  @Override
  public void weatherEffect(Weather weather) {
    weatherManager.changeWeather(weather);
  }

  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    // enable weather control
    weatherManager.displayWeather();
    weatherEffect(weatherManager.controlWeather());
    // call to overridden method
    return super.playTurn(actions, lastAction,map,display);
  }

  /**
   * Abxervyer can use limbs to deal 80 damage with 25% hit rate.
   * @return intrinsic weapon item
   */
  @Override
  public IntrinsicWeapon getIntrinsicWeapon(){
    int damage = 80;
    int hitRate = 25;
    return new IntrinsicWeapon(damage, "mutilates", hitRate);
  }


  @Override
  public String unconscious(Actor actor, GameMap map) {

    Runes runes = new Runes(this.runesAmount);

    String ret = "";
    ret += this + " has been slain by " + actor;
    ret += runes.dropped(this,map.locationOf(this),this.runesDropRate);

    actor.addCapability(Status.ABXERVYER_DEFEATED);

    bossDeadMessage();
    weatherManager.resetWeather();
    map.locationOf(this).setGround(lockedGate);
    map.removeActor(this);
    return ret;
  }


  /**
   * Displays BOSS DEFEATED when boss enemy is slain.
   */
  public void bossDeadMessage(){
    Display display = new Display();
    // empty line for tidiness
    display.println("\n");
    for (String line : FancyMessage.BOSS_DEFEATED.split("\n")) {
      display.println(line);
      try {
        Thread.sleep(200);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  /**
   * Reset function for the related object.
   */
  @Override
  public void reset() {
    if (this.isConscious()){
      this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE,getAttributeMaximum(BaseActorAttributes.HEALTH));
    }
  }
}
