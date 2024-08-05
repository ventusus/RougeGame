package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;

/**
 * Manager class to perform item purchasing.
 */
public class PurchasableManager {
    /**
     * Purchase broadsword
     * @param item broadsword
     * @param by player
     * @param price broadsword's price
     * @param chanceRate the chance got cheated
     * @return the string of purchase
     */
    public String purchaseBroadsword(Item item, Actor by, int price, int chanceRate){
        if(by.getBalance() >= price){
            by.deductBalance(price);
            if (Math.random() > chanceRate/100.0){
                by.addItemToInventory(item);
                return String.format("\n%s purchased Broadsword successfully.", by);
            }
            else{
                return String.format("\n%s got cheated, purchased Broadsword unsuccessfully.", by);
            }
        }
        return String.format("\n%s didn't have enough balance.", by);
    }

    /**
     * Purchase item normally without be robbed
     * @param item healing vial or refreshing flask
     * @param by player
     * @param price item's price
     * @param chanceRate chance to apply price rate
     * @param priceRate the price rate
     * @return the string of purchase
     */
    public String purchaseItemNormally(Item item, Actor by, int price, int chanceRate,int priceRate){
        if (Math.random() <= chanceRate/100.0){
            price += price * (priceRate/100.0);
        }
        if(by.getBalance() >= price){
            by.deductBalance(price);
            by.addItemToInventory(item);
            return String.format("\n%s purchased %s successfully.", by,item);
        }
        return String.format("\n%s didn't have enough balance.", by);
    }

    /**
     * Purchase great knife
     * @param item great knife
     * @param by player
     * @param price great knife's price
     * @return the string of purchase
     */
    public String purchaseGreatKnife(Item item,Actor by,int price){
        int chance = 5;
        if (!by.getItemInventory().contains(item)){
            if(by.getBalance() >= price){
                by.deductBalance(price);
                by.addItemToInventory(item);
                return String.format("\n%s purchased %s successfully.", by,item);
            }
        }else{
            if (Math.random()<= chance/100.0){
                price = 3 * price;
                if(by.getBalance() >= price){
                    by.deductBalance(price);
                    by.addItemToInventory(item);
                    return String.format("\n%s purchased %s successfully with triple price.", by,item);
                }
            }

        }
        return String.format("\n%s didn't have enough balance.", by);
    }
}
