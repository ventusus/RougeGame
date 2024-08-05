package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
/**
 * Manager class to perform item selling.
 */
public class SellableManager {
    /**
     * Sell broadsword
     * @param item broadsword
     * @param by player
     * @param price broadsword's price
     * @return the string of selling
     */
    public String sellBroadsword(Item item, Actor by, int price){
        by.addBalance(price);
        by.removeItemFromInventory(item);
        return String.format("\n%s sell broadsword successfully.", by);
    }

    /**
     * Sell healing vial
     * @param item healing vial
     * @param by player
     * @param price healing vial's price
     * @param chanceRate the chance to pay double
     * @return the string of selling
     */

    public String sellHealingVial(Item item, Actor by, int price, int chanceRate){
        if (Math.random() <= chanceRate/100.0){
            price += price;
        }
        by.addBalance(price);
        by.removeItemFromInventory(item);
        return String.format("\n%s sell HealingVial successfully.", by);
    }

    /**
     * Sell refreshing flask
     * @param item refreshing flask
     * @param by player
     * @param price refreshing flask's price
     * @param chanceRate the chance of player will be robbed by traveller
     * @return  the string of selling
     */
    public String sellRefreshingFlask(Item item, Actor by, int price, int chanceRate) {
        if (Math.random() <= chanceRate / 100.0) {
            by.removeItemFromInventory(item);
            return String.format("\n%s s RefreshingFlask is robbed.", by);
        } else {
            by.addBalance(price);
            by.removeItemFromInventory(item);
            return String.format("\n%s sell RefreshingFlask successfully.", by);

        }
    }

    /**
     * Selling bloodberry
     * @param item bloodberry
     * @param by player
     * @param price bloodberry's price
     * @return the string of selling
     */

    public String sellBloodberry(Item item, Actor by, int price){
        by.addBalance(price);
        by.removeItemFromInventory(item);
        return String.format("\n%s sell bloodberry successfully.", by);
    }

    /**
     * Selling great knife
     * @param item great knife
     * @param by player
     * @param price great knife's price
     * @return the string of selling
     */

    public String sellGreatKnife(Item item, Actor by,int price){
        int chance = 10;
        by.removeItemFromInventory(item);
        if (Math.random() <= chance/100.0){
            if (by.getBalance()>=price){
                by.deductBalance(price);
            }else{
                by.deductBalance(by.getBalance());
            }
        }else{
            by.addBalance(price);
        }
        return String.format("\n%s sell %s successfully. Its price is %d", by,item,price);

    }

    /**
     * Selling giant hammer
     * @param item giant hammer
     * @param by player
     * @param price giant hammer's price
     * @return the string of selling
     */

    public String sellGiantHammer(Item item, Actor by, int price){
        by.addBalance(price);
        by.removeItemFromInventory(item);
        return String.format("\n%s sell %s successfully.", by,item);
    }

}
