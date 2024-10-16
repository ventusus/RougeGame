package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;

/**
 * Manager class to perform item upgrading.
 */
public class UpgradableManager {
    /**
     * Upgrade weapons
     * @param weapon broadsword or great knife
     * @param by player
     * @param price weapon's price
     * @return the string of upgrade
     */
    public String upgradeWeapon(Upgradable weapon, Actor by, int price){
        if(by.getBalance() >= price){
            by.deductBalance(price);
//            System.out.println(String.format("%s will upgrade %s for %d credits.", by, weapon, price));
            return String.format("\n%s upgraded %s successfully.", by, weapon);
        }
        return String.format("\n%s didn't have enough balance.", by);
    }

    /**
     * Upgrade items
     * @param item healing vial or refreshing flask
     * @param by player
     * @param price item's price
     * @param currentUpgradeLevel  item's current upgrade level
     * @return the string of upgrade
     */
    public String upgradeItem(Upgradable item, Actor by, int price, int currentUpgradeLevel){
        if (currentUpgradeLevel == 0) {
            if (by.getBalance() >= price) {
                by.deductBalance(price);
//                System.out.println(String.format("%s will upgrade %s for %d credits.", by, item, price));
                return String.format("\n%s upgraded %s successfully.", by, item);
            }
            return String.format("\n%s didn't have enough balance.", by);
        } else {
            return String.format("\n%s has already been upgraded.", by);
        }
    }
}
