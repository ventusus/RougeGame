package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;


/**
 * Weapon skill action. Increases hit rate and boosts damage of weapon.
 */
public class Focus extends Action {

  private final WeaponItem weapon;
  private final float dmgMultiplierPercentage;
  private final int hitRatePercentage;
  private final int staminaConsumptionPercentage;
  private final int numOfTurns;
  private int turnsRemaining;
  private boolean isActive;


  /**
   * Constructor.
   * @param weapon The weapon this skill takes effect on.
   * @param dmgMultiplierPercentage Percentage to increase the weapon's damage multiplier by.
   * @param hitRatePercentage Percentage to set the weapon's hit rate to.
   * @param staminaConsumptionPercentage Percentage to consume the actor's stamina by.
   * @param numOfTurns Number of turns the skills takes effect for.
   */
  public Focus(WeaponItem weapon, float dmgMultiplierPercentage, int hitRatePercentage, int staminaConsumptionPercentage, int numOfTurns) {
    this.weapon = weapon;
    this.dmgMultiplierPercentage = dmgMultiplierPercentage;
    this.hitRatePercentage = hitRatePercentage;
    this.staminaConsumptionPercentage = staminaConsumptionPercentage;
    this.numOfTurns = numOfTurns;
    this.isActive = false;
    this.turnsRemaining = 0;

  }

  /**
   * Checks if the skill is active.
   * @return {@code true} if skill is active, {@code false} otherwise
   */
  public boolean isActive() {
    return isActive;
  }

  /**
   * Returns the turns remaining for the skill.
   * @return {@code true} if turn is remaining, {@code false} otherwise
   */
  public int getTurnsRemaining() {
    return turnsRemaining;
  }

  /**
   * Reset the skill's activity.
   * @param active {@code true} if skill is active, {@code false} otherwise
   */
  public void setActive(boolean active) {
    isActive = active;
  }

  /**
   * Reset the skill's remaining turns.
   * @param turnsRemaining number of turns left for the skill to remain active
   */
  public void setTurnsRemaining(int turnsRemaining) {
    this.turnsRemaining = turnsRemaining;
  }

  /**
   * Decreases a turn left for the skill to stay active.
   */
  public void decrementTurnsRemaining(){
    turnsRemaining--;
  }

  /**
   * Once skill is deactivated, remove effect from skill.
   * @param defaultHitRate the default HitRate of the weapon
   */
  public void resetWeaponStats(int defaultHitRate){
    float defaultDamageMultiplier = 1.0f;

    weapon.updateDamageMultiplier(defaultDamageMultiplier);
    weapon.updateHitRate(defaultHitRate);
  }

  @Override
  public String execute(Actor actor, GameMap gameMap) {

    // initialise number of turns each time execute is called
    setTurnsRemaining(numOfTurns);

    // make the skill active
    setActive(true);
    // calculate stamina consumption
    int staminaConsumption;
    int maxStamina = actor.getAttributeMaximum(BaseActorAttributes.STAMINA);
    staminaConsumption = Math.round(maxStamina * staminaConsumptionPercentage / 100.0f);

    // modify actor and weapon stats
    actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE,
        staminaConsumption);
    weapon.updateDamageMultiplier(dmgMultiplierPercentage);
    weapon.updateHitRate(hitRatePercentage);

    // this method itself counts as 1 turn
    turnsRemaining--;

    return actor + " focuses all their might!";

  }

  @Override
  public String menuDescription(Actor actor){
    if (isActive) {
      return actor + " continues to focus.";
    } else {
      return actor + " activates skill of Broadsword.";
    }
  }

}
