package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Interface representing a purchasable item.
 */

public interface Purchasable {
    /**
     * Purchasable Manager
     */
    PurchasableManager purchasableManager = new PurchasableManager();

    /**
     * Purchases this item.
     * @param by the actor purchasing
     * @return a String description of effect of purchasing
     */
    String purchased(Actor by);
}