package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Purchasable;
/**
 * Class representing action to purchase purchasable.
 */
public class PurchaseAction extends Action {

    /**
     * The item that can be purchased
     */
    private final Purchasable purchasable;
    /**
     * Constructor.
     * @param purchasable the item that can be purchased
     */
    public PurchaseAction(Purchasable purchasable) {
        this.purchasable = purchasable;
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
        String ret = purchasable.purchased(actor);
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
        return actor + " purchases " + purchasable ;
    }
}
