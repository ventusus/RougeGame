package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Interface representing a upgradable item.
 */
public interface Upgradable {
    /**
     * Upgradable Manager
     */
    UpgradableManager upgradableManager = new UpgradableManager();

    /**
     * Upgrade this item.
     * @param by the actor upgrading
     * @return a String description of effect of upgrading
     */
    String upgraded(Actor by);
}
