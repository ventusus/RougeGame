package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Upgradable;
/**
 * Class representing action to upgrade item or weapon.
 */
public class UpgradeAction extends Action {
    /**
     * The item that can be upgraded
     */
    private final Upgradable upgradable;
    /**
     * Constructor.
     * @param upgradable the item that can be upgraded
     */
    public UpgradeAction(Upgradable upgradable) {
        this.upgradable = upgradable;
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
        String ret = upgradable.upgraded(actor);
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
        return actor + " upgrades " + upgradable;
    }
}
