package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.ResetAction;
import game.utils.FancyMessage;
import game.enums.Ability;
import game.enums.Status;

/**
 * Class representing the Player.
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Sandra Lo Yii Shin
 *
 */
public class Player extends Actor {
  /**
   * To store the player's born location
   */
  public static Location respawnLocation;
  /**
   * Constructor.
   *
   * @param name        Name to call the player in the UI
   * @param displayChar Character to represent the player in the UI
   * @param hitPoints   Player's starting number of hitpoints
   * @param stamina     Player's starting stamina
   * @param spawnLocation Player's starting location
   */
  public Player(String name, char displayChar, int hitPoints, int stamina, Location spawnLocation) {
    super(name, displayChar, hitPoints);
    this.addCapability(Status.HOSTILE_TO_ENEMY);
    this.addCapability(Ability.WALK_ON_FLOORS);
    this.addCapability(Ability.TRAVEL);
    this.addCapability(Ability.DRINKING);
    this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(stamina));
    this.addCapability(Ability.BUYING);
    this.addCapability(Ability.SELLING);
    this.addCapability(Ability.UPGRADE);
    this.addCapability(Ability.REVIVE);
    this.respawnLocation = spawnLocation;
    this.addCapability(Ability.LISTEN);

  }

  /**
   * Returns a string to display the current player's name, health and stamina.
   * @return the string of player's name and main stats
   */
  public String displayStatus(){
    String ret = "";
    ret += String.format("\n%s",this.name);
    ret += String.format("\nHP: %d/%d",this.getAttribute(BaseActorAttributes.HEALTH), this.getAttributeMaximum(BaseActorAttributes.HEALTH));
    ret += String.format("\nStamina: %d/%d",this.getAttribute(BaseActorAttributes.STAMINA), this.getAttributeMaximum(BaseActorAttributes.STAMINA));
    ret += String.format("\nRunes: %d",this.getBalance());

    return ret;
  }


  /**
   * Player can use limbs to deal 15 damage with 80% hit rate.
   * @return Player's intrinsic weapon
   */
  @Override
  public IntrinsicWeapon getIntrinsicWeapon(){
    int damage = 15;
    int hitRate = 80;
    return new IntrinsicWeapon(damage, "punches", hitRate);
  }


  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
      if (this.hasCapability(Status.DEAD)){
        playerDeadMessage();
        return new ResetAction();
      }

      // at the beginning of each turn, recover stamina and print the player's stats
      recoverStamina();
      display.println(displayStatus());

      // Handle multi-turn Actions
      if (lastAction.getNextAction() != null)
        return lastAction.getNextAction();

      // return/print the console menu
      Menu menu = new Menu(actions);
      return menu.showMenu(this, display);

  }

  /**
   * Recovers the player's stamina by 1% of max stamina at each turn.
   */
  public void recoverStamina() {

    int maxStamina = getAttributeMaximum(BaseActorAttributes.STAMINA);
    int currStamina = getAttribute(BaseActorAttributes.STAMINA);
    float staminaIncrement = 0.1f;
    if (currStamina < maxStamina) {
      currStamina += Math.round(maxStamina * staminaIncrement);
    }
    modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, currStamina);
  }


  /**
   * Prints a YOU DIED message for when the player dies of 'natural causes' or accidents.
   * @param map where the actor fell unconscious
   * @return string describing what happened when the actor is unconscious
   */
  @Override
  public String unconscious(GameMap map) {
    playerDeadMessage();
    // new ResetAction().execute(this,map);
    return super.unconscious(map);
  }

  /**
   * Prints a YOU DIED message for when the player is killed by someone else.
   * @param actor the perpetrator
   * @param map where the actor fell unconscious
   * @return string describing what happened when the actor is unconscious
   */
  @Override
  public String unconscious(Actor actor, GameMap map){

    playerDeadMessage();
    // new ResetAction().execute(this,map);
    //return new DoNothingAction().execute(this,map);
    //return new ResetAction().execute(this,map);
    return super.unconscious(actor, map);

  }

  /**
   * Displays a YOU DIED message when the player dies.
   */
  public void playerDeadMessage(){
    Display display = new Display();
    // empty line for tidiness
    display.println("\n");
    for (String line : FancyMessage.YOU_DIED.split("\n")) {
      display.println(line);
      try {
        Thread.sleep(200);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

}
