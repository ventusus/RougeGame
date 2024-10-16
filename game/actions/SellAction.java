package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Sellable;
/**
 * Class representing action to sell sellable.
 */
public class SellAction extends Action {

    /**
     * The item that can be sold
     */
    private final Sellable sellable;
    /**
     * Constructor.
     * @param sellable the item that can be sold
     */
    public SellAction(Sellable sellable) {
        this.sellable = sellable;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String ret = sellable.sold(actor);
        return ret;
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " sells " + sellable ;
    }
}