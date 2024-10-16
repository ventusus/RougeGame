package game.actions;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.ResetManager;
/**
 * Class representing an action to remove actor on the map.
 */
public class RemoveActorAction extends Action{

    /**
     * Perform the Remove Actor Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);

        ResetManager.getResetManager().removeFromResetList(actor);
        return actor + "has been removed.";
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + "has been removed from the map.";
    }
}
