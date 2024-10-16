package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import java.util.ArrayList;
import java.util.Random;

/**
 * Allows the actor to follow someone.
 */
public class FollowBehaviour implements Behaviour {

  private final Random random = new Random();
  private Actor target;


  /**
   * Constructor.
   * @param target the target actor to follow
   */
  public FollowBehaviour(Actor target) {
    this.target = target;
  }

  /**
   * Returns a MoveAction to move closer to the target.
   * If no movement is possible, or if the actor and target are not both on the same map, returns null.
   * @param actor the Actor following
   * @param map the GameMap containing the Actor
   * @return an Action, or null if no MoveAction is possible
   */
  @Override
  public Action getAction(Actor actor, GameMap map) {

    // if the player/target is not on the map, return null
    if(!map.contains(target) || !map.contains(actor))
      return null;

    ArrayList<Action> actions = new ArrayList<>();

    Location actorLocation = map.locationOf(actor);
    Location targetLocation = map.locationOf(target);

    int currDistance = getDistance(actorLocation,targetLocation);

    for (Exit exit : actorLocation.getExits()) {
      // obtain all potential exits for the current actor
      Location destination = exit.getDestination();

      // recalculate distance for any valid exit
      if (destination.canActorEnter(actor)) {
        int newDistance = getDistance(destination,targetLocation);

        // add a MoveActorAction for any exit that shortens the distance between both actors
        if (newDistance < currDistance) {
          String descString = String.format("closer to %s", target.toString());
          actions.add(new MoveActorAction(destination, descString));
        }
      }
    }

    if (!actions.isEmpty()) {
      // randomly select any of the viable exits to move to
      return actions.get(random.nextInt(actions.size()));
    } else {
      return null;
    }
  }

  /**
   * Calculates the Manhattan distance between two locations.
   * Equation: dist = |x1 - x2| + |y1 - y2|
   * @param a first location
   * @param b second location
   * @return number of steps between a and b by moving in Cartesian coordinates
   */
  public int getDistance(Location a, Location b) {
    int dist = Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    return dist;
  }

}