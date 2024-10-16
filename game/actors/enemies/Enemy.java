package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.ResetManager;
import game.Resettable;
import game.actions.AttackAction;
import game.actions.RemoveActorAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.enums.Ability;
import game.enums.Status;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class representing enemies in the game.
 */
public abstract class Enemy extends Actor implements Resettable{


  private Map<Integer, Behaviour> behaviours = new HashMap<>();
  /**
   * The rune's drop rate
   */
  public int runesDropRate;
  /**
   * Constructor.
   * @param name name of enemy
   * @param displayChar display character of enemy
   * @param hitPoints HP of enemy
   */
  public Enemy(String name, char displayChar, int hitPoints) {
    super(name, displayChar, hitPoints);
    this.behaviours.put(997, new AttackBehaviour(Status.HOSTILE_TO_ENEMY));
    this.behaviours.put(999, new WanderBehaviour());
    this.runesDropRate = 100;
    ResetManager.getResetManager().addToResetList(this);

  }

  /**
   * Retrieve the available behaviours of the enemy
   * @return Map of behaviours
   */
  public Map<Integer, Behaviour> getBehaviours() {
    return behaviours;
  }


  /**
   * At each turn, select a valid action to perform.
   *
   * @param actions    collection of possible Actions for this Actor
   * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
   * @param map        the map containing the Actor
   * @param display    the I/O object to which messages may be written
   * @return the valid action that can be performed in that iteration or null if no valid action is found
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    if (this.hasCapability(Status.RESET)){
      return new RemoveActorAction();
    }
    for (Behaviour behaviour : behaviours.values()) {
      Action action = behaviour.getAction(this, map);
      if(action != null)
        return action;
    }
    return new DoNothingAction();
  }

  /**
   * The enemy can be attacked by any actor that has the HOSTILE_TO_ENEMY capability
   * If the enemy can follow the player, it starts following once player is in range
   *
   * @param otherActor the Actor that might be performing attack
   * @param direction  String representing the direction of the other Actor
   * @param map        current GameMap
   * @return action list containing an AttackAction
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    ActionList actions = new ActionList();
    if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){

      // if this enemy can be provoked to follow the target, start following
      if((!this.hasCapability(Status.PROVOKED)) && (this.hasCapability(Ability.FOLLOW))) {
        follow(otherActor);
      }
      // player can attack with intrinsic weapon
      actions.add(new AttackAction(this, direction));
    }
    return actions;
  }

  /**
   * Trigger this actor (if they can follow) to follow another actor if they come too close.
   * @param otherActor the actor to be followed
   */
  public void follow(Actor otherActor) {
    this.addCapability(Status.PROVOKED);
    this.behaviours.put(998, new FollowBehaviour(otherActor));
  }



}
