package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Interface representing a sellable item.
 */

public interface Sellable {
    /**
     * Sellable Manager
     */
    SellableManager sellableManager = new SellableManager();

    /**
     * Sells this item.
     * @param by the actor selling
     * @return a String description of effect of selling
     */
    String sold(Actor by);
}