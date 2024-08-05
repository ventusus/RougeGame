package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Ability;
import game.enums.Status;

/**
 * Void that kills anyone that steps on it.
 */
public class Void extends Ground {
  /**
   * Constructor
   */
  public Void() {
    super('+');
  }

  /**
   * Kills any entity, except for those immune, that steps into the void at any given turn.
   * @param location The location of the void
   */
  @Override
  public void tick(Location location){

    if (location.containsAnActor()){
      Actor actor = location.getActor();
      if (actor.hasCapability(Ability.VOID_IMMUNE)) {
        super.tick(location);
      }
      else {
        new Display().println(actor + " is consumed by the unforgiving void.");
        // HP becomes 0
        actor.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, 0);
        if (actor.hasCapability(Ability.REVIVE)){
          actor.addCapability(Status.DEAD);
        }
        else {
          actor.unconscious(location.map());
        }


      }

    }
  }

}


