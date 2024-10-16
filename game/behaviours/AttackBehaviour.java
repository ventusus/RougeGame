package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.enums.Status;
import java.util.ArrayList;
import java.util.Random;

/**
 * Allows the actor to attack someone.
 */
public class AttackBehaviour implements Behaviour {

  private final Random random = new Random();
  private Status targetCapability;


  /**
   * Constructor to make an AttackBehaviour based on specified capabilities of the target Actor
   * to determine if the target can be attacked.
   * @param targetCapability Capability required by the Actor to generate an AttackAction towards its target
   */
  public AttackBehaviour(Status targetCapability){
    this.targetCapability = targetCapability;
  }

  /**
   * Returns an AttackAction to attack an entity, if possible.
   * If no targets nearby that can be attacked, returns null.
   *
   * @param actor the Actor acting
   * @param map the GameMap containing the Actor
   * @return  an Action, or null if no AttackAction is possible
   */
  @Override
  public Action getAction(Actor actor, GameMap map) {
    ArrayList<Action> actions = new ArrayList<>();

    for (Exit exit : map.locationOf(actor).getExits()) {
      // obtain all potential exits
      Location destination = exit.getDestination();

      // if the exit contains an actor,
      if (destination.containsAnActor()) {
        Actor target = destination.getActor();
        // if the target has a required capability to allow this actor to attack them
        if (target.hasCapability(targetCapability)) {
          // attack the target with intrinsic weapon
          actions.add(new AttackAction(target, destination.toString()));
        }
      }
    }

    if (!actions.isEmpty()) {
      // if there were an 'attack with weapon' option, randomly selects either to use
      // weapon or intrinsic weapon to attack
      return actions.get(random.nextInt(actions.size()));
    }
    else {
      return null;
    }

  }
}
