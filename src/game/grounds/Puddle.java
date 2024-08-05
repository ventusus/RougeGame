package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.enums.Ability;
import game.items.Consumable;

/**
 * The puddle ground
 */
public class Puddle extends Ground implements Consumable {
    /**
     * Constructor
     */
    public Puddle() {
        super('~');
    }

    /**
     * Consumes this item.
     *
     * @param by the actor consuming
     * @return a String description of effect of consumption
     */
    @Override
    public String consumed(Actor by) {
        String ret = "";
        ret += consumableManager.consumeWater(by);
        return ret;
    }


    @Override
    public String toString() {
        String ret = "";
        ret += "water from puddle";
        return ret;
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (location.containsAnActor() && actor.hasCapability(Ability.DRINKING)) {
            actions.add(new ConsumeAction(this));
        }
        return actions;
    }
}
