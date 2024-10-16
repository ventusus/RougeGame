package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.enums.ItemCapability;
/**
 * A Bloodberry item that modify max health upon consumption.
 */
public class Bloodberry extends Item implements Consumable,Sellable,Droppable {
    /***
     * Constructor.
     */
    public Bloodberry() {
        super("Bloodberry", '*', true);
        this.addCapability(ItemCapability.HEAL);
        this.addCapability(ItemCapability.SELLABLE);
    }

    /**
     * Returns a list with a consume action to let actor consume an item.
     *
     * @param owner the actor that owns the item
     * @return the list
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();
        actions.add(new ConsumeAction(this));
        return actions;
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

        // the player’s maximum health is increased by 5 points permanently.
        int maxHpIncrement = 5;
        ret += consumableManager.consumeMaxHealthItem(this, by, maxHpIncrement);

        return ret;
    }

    /**
     * Sells this item.
     *
     * @param by the actor selling
     * @return a String description of effect of selling
     */
    @Override
    public String sold(Actor by) {
        String ret = "";
        ret += sellableManager.sellBloodberry(this,by,10);
        return ret;
    }

    @Override
    public String dropped(Actor by, Location at, int dropRate) {
        String ret = "";
        ret += droppableManager.dropItem(this,by,at,dropRate);

        return ret;
    }
}
