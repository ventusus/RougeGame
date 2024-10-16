package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ListenAction;
import game.actions.UpgradeAction;
import game.enums.Ability;
import game.enums.ItemCapability;
import game.enums.Status;
import game.items.Upgradable;
import game.utils.monologues.BlacksmithMonologue;
import game.utils.monologues.Monologue;
/**
 * Class representing the Blacksmith.
 */
public class Blacksmith extends Actor {

    private final Monologue monologue = new BlacksmithMonologue();

    /**
     * Constructor
     */
    public Blacksmith() {
        super("Blacksmith", 'B', 9999999);
        this.addCapability(Status.FRIENDLY);
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
//        display.println("Clang... Clank... Clang... Clank...");
        return (new DoNothingAction());
    }

    /**
     * The player can interact with the blacksmith by listening to them, and upgrade weapons.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return action list containing an AttackAction
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Ability.LISTEN)) {
            actions.add(new ListenAction(this, monologue));
        }
        if (otherActor.hasCapability(Ability.UPGRADE)){
            for (Item item : otherActor.getItemInventory()){
                if (item.hasCapability(ItemCapability.UPGRADABLE)){
                    actions.add(new UpgradeAction((Upgradable) item));
                }
            }
        }

        return actions;
    }
}
