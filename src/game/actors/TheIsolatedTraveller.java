package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ListenAction;
import game.actions.PurchaseAction;
import game.actions.SellAction;
import game.enums.Ability;
import game.enums.ItemCapability;
import game.enums.Status;
import game.items.*;
import game.items.weapons.Broadsword;
import game.items.weapons.GreatKnife;
import game.utils.monologues.Monologue;
import game.utils.monologues.IsolatedTravellerMonologue;

/**
 * The isolated traveller can sell item to player and also buy item from player.
 */
public class TheIsolatedTraveller extends Actor {

    HealingVial healingVial = new HealingVial();
    RefreshingFlask refreshingFlask = new RefreshingFlask();
    Broadsword broadsword = new Broadsword();
    GreatKnife greatKnife = new GreatKnife();
    private final Monologue monologue = new IsolatedTravellerMonologue();

    /**
     * Constructor of the isolated traveller
     */
    public TheIsolatedTraveller() {
        super("The Isolated Traveller", 'ඞ', 9999999);
        this.addItemToInventory(healingVial);
        this.addItemToInventory(refreshingFlask);
        this.addItemToInventory(broadsword);
        this.addItemToInventory(greatKnife);
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
        return (new DoNothingAction());
    }

    /**
     * The enemy can be attacked by any actor that has the HOSTILE_TO_ENEMY capability
     * If the enemy can follow the player, it starts following once player is in range
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
        if (otherActor.hasCapability(Ability.BUYING)){
            for (Item item : this.getItemInventory()){
                if (item.hasCapability(ItemCapability.PURCHASABLE)){
                actions.add(new PurchaseAction((Purchasable)item));
                }
            }
        }
        if (otherActor.hasCapability(Ability.SELLING)){
            for (Item item : otherActor.getItemInventory()){
                if (item.hasCapability(ItemCapability.SELLABLE)){
                    actions.add(new SellAction((Sellable) item));
                }
            }
        }
        return actions;
    }
}