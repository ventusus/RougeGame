package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.ResetManager;
import game.Resettable;
import game.actions.ConsumeAction;
import game.enums.ItemCapability;

/**
 * A class represents runes which is the main currency in the game.
 */
public class Runes extends Item implements Consumable,Droppable, Resettable {
    private int amount;

    /***
     * Constructor.
     * @param amount the amount of rune
     */
    public Runes(int amount) {
        super("Runes", '$', true);
        this.addCapability(ItemCapability.RUNES_BOOST);
        this.amount = amount;
        ResetManager.getResetManager().addToResetList(this);
    }
    /**
     * Returns a list with a consume action to let actor consume an item.
     * @param owner the actor that owns the item
     * @return the list
     */
    @Override
    public ActionList allowableActions(Actor owner){
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
        ret += consumableManager.consumeRunesItem(this,by,this.amount);
        return ret;

    }


    /**
     * Drops the item at a specified rate.
     *
     * @param by       the actor dropping the item
     * @param at       the location of the item to be dropped
     * @param dropRate item drop rate
     * @return String description of item drop
     */
    @Override
    public String dropped(Actor by, Location at, int dropRate) {
        String ret = "";
        ret += droppableManager.dropItem(this,by,at,dropRate);
        return ret;
    }

    /**
     * Reset function for the related object.
     */
    @Override
    public void reset() {

        this.addCapability(ItemCapability.RESETTABLE);
    }

    @Override
    public void tick(Location currentLocation) {
        if (this.hasCapability(ItemCapability.RESETTABLE)){
            currentLocation.removeItem(this);
            ResetManager.getResetManager().removeFromResetList(this);
        }
        super.tick(currentLocation);
    }

}
