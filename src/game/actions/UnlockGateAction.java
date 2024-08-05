package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.GroundCapability;
import game.enums.ItemCapability;

/**
 * Class representing action to unlock a gate.
 */
public class UnlockGateAction extends Action {

  private ItemCapability itemCapability;

  private final Location gateLocation;

  /**
   * Constructor.
   * @param gateLocation the location of the gate to be unlocked
   */
  public UnlockGateAction(Location gateLocation){
    this.gateLocation = gateLocation;
    this.itemCapability = ItemCapability.UNLOCK_GATE;
  }

  @Override
  public String execute(Actor actor, GameMap gameMap) {

    Ground ground = gateLocation.getGround();
    if (ground.hasCapability(GroundCapability.BLOCKED)) {

      // if the actor has an item that can UNLOCK GATES
      if (actor.hasCapability(itemCapability)) {
        // unblock the ground and make available for travel
        ground.removeCapability(GroundCapability.BLOCKED);
        ground.addCapability(GroundCapability.TRANSPORTABLE);
        return "The gate is now unlocked.";
      } else {
        return "The gate is locked. Looks like it needs a key...";
      }
    }
    else {
      return "Gate is unlocked.";
    }
  }

  @Override
  public String menuDescription(Actor actor){
    return actor + " observes the nearby gate.";
  }

}
