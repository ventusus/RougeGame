package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.enums.Ability;

/**
 * A class that represents the floor inside a building.
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 *
 */
public class Floor extends Ground {
    /**
     * Constructor
     */

    public Floor() {
        super('_');
    }


    /**
     * Limited to actors who can walk on floors.
     * @param actor the Actor to check
     * @return {@code true} if the actor can walk on floors, {@code false} otherwise
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Ability.WALK_ON_FLOORS);
    }
}