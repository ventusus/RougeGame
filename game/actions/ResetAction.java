package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.ResetManager;
import game.enums.Status;
import game.items.Runes;

import static game.actors.Player.respawnLocation;
/**
 * Class representing an action to present reset action after player dies.
 */
public class ResetAction extends Action {
    /**
     * Perform the Reset Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ResetManager.getResetManager().runReset();
        ResetManager.getResetManager().getResettableList().clear();
        Runes playerDroppedRune = new Runes(actor.getBalance());
//      playerDroppedRune.dropped(this,map.locationOf(this),100);
        map.locationOf(actor).addItem(playerDroppedRune);
        actor.deductBalance(actor.getBalance());
        //DroppableManager.getDroppableManager().addRunesToList(playerDroppedRune,map.locationOf(this));
        if(!respawnLocation.containsAnActor()){
            respawnLocation.map().moveActor(actor,respawnLocation);
        }
        actor.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE,actor.getAttributeMaximum(BaseActorAttributes.HEALTH));
        actor.modifyAttribute(BaseActorAttributes.STAMINA,ActorAttributeOperations.UPDATE,actor.getAttributeMaximum(BaseActorAttributes.STAMINA));
        actor.removeCapability(Status.DEAD);
        //respawnLocation.map().draw(new Display());
        return "It was a dream.....";
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
