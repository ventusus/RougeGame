package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;

/**
 * Manager class to perform item consumption.
 */
public class ConsumableManager {


  /**
   * Consumes a healing item to replenish health.
   * @param item consumable item
   * @param by actor consuming the item
   * @param boostRate percentage of health increase
   * @return string info
   */
  public String consumeHealingItem(Item item, Actor by, float boostRate){
    int maxHealth = by.getAttributeMaximum(BaseActorAttributes.HEALTH);
    int currHealth = by.getAttribute(BaseActorAttributes.HEALTH);

    if (currHealth < maxHealth){
      currHealth += Math.round(maxHealth * boostRate);
    }
    by.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, currHealth);
    by.removeItemFromInventory(item);

    return String.format("\n%s's wounds begin to heal, bringing a glimmer of hope.", by);
  }

  /**
   * Consumes a stamina boosting item to replenish stamina.
   * @param item consumable item
   * @param by actor consuming the item
   * @param boostRate percentage of stamina increase
   * @return string info
   */
  public String consumeStaminaItem(Item item, Actor by, float boostRate) {
    int maxStamina = by.getAttributeMaximum(BaseActorAttributes.STAMINA);
    int currStamina = by.getAttribute(BaseActorAttributes.STAMINA);

    if (currStamina < maxStamina) {
      currStamina += Math.round(maxStamina * boostRate);
      by.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, currStamina);
    }
    by.removeItemFromInventory(item);
    return String.format("\n%s feels a surge of vitality as their stamina is replenished.", by);

  }

  /**
   * Consumes a runes item to increase money in wallet.
   * @param item consumable item
   * @param by actor consuming the item
   * @param amount the total money dropped by enemy
   * @return string info
   */
  public String consumeRunesItem(Item item, Actor by, int amount) {
    by.addBalance(amount);
    by.removeItemFromInventory(item);
    return String.format("\n%s 's wallet has increased %d.", by,amount);
  }

  /**
   * For actor can drink water on the puddle
   * @param by the actor can drink water
   * @return string info
   */
  public String consumeWater(Actor by){
    int maxHealth = by.getAttributeMaximum(BaseActorAttributes.HEALTH);
    int currHealth = by.getAttribute(BaseActorAttributes.HEALTH);
    int maxStamina = by.getAttributeMaximum(BaseActorAttributes.STAMINA);
    int currStamina = by.getAttribute(BaseActorAttributes.STAMINA);
    // restores actor by 1% max stamina
    float staminaBoostRate = 0.01f;
    // heals actor by 1 point
    int hpPoint = 1;

    if (currHealth < maxHealth){
      by.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, hpPoint);
    }
    if (currStamina < maxStamina) {
      currStamina += Math.round(maxStamina * staminaBoostRate);
      by.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, currStamina);
    }
    return String.format("\n%s drinks from the water puddle. They feel refreshed", by);
  }

  /**
   * The function to represent consume the item can change the max health
   * @param item the consumable item
   * @param by  the owner
   * @param healingPoint  the hp to be increase
   * @return  string info
   */
  public String consumeMaxHealthItem(Item item, Actor by, int healingPoint){
    int maxHealth = by.getAttributeMaximum(BaseActorAttributes.HEALTH);
    by.modifyAttributeMaximum(BaseActorAttributes.HEALTH,ActorAttributeOperations.INCREASE,healingPoint);
    by.removeItemFromInventory(item);
    return String.format("\n%s consumed a %s, maximum health is increased by %d.", by, item, healingPoint);
  }
}
