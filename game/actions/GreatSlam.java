package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Weapon skill action. Slams an enemy target and does surrounding splash damage
 */
public class GreatSlam extends Action {
    private final WeaponItem weapon;
    private final Actor target;

    private Random random = new Random();
    private final String direction;

    private final float SPLASH_DAMAGE_MULTIPLIER;
    private final int STAMINA_CONSUMPTION_PERCENTAGE;



    /**
     * Constructor.
     *
     * @param weapon                         The weapon this skill takes effect on.
     * @param target                         The other actor this skill affects.
     * @param direction                      direction to use the ability at.
     * @param STAMINA_CONSUMPTION_PERCENTAGE Percentage to consume the actor's stamina by.
     * @param SPLASH_DAMAGE_MULTIPLIER Multiplier of the damage done to surrounding enemies
     */
    public GreatSlam(WeaponItem weapon, int STAMINA_CONSUMPTION_PERCENTAGE, float SPLASH_DAMAGE_MULTIPLIER, Actor target, String direction) {
        this.weapon = weapon;
        this.target = target;
        this.direction = direction;
        this.STAMINA_CONSUMPTION_PERCENTAGE = STAMINA_CONSUMPTION_PERCENTAGE;
        this.SPLASH_DAMAGE_MULTIPLIER = SPLASH_DAMAGE_MULTIPLIER;
    }


    /**
     * Slams an enemy target and does splash damage to surrounding actors
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return A message displayed in the UI of what the actor is doing
     * <p>
     * Calls the attack action using the weapon Giant Hammer at the target
     * Searches for any other actors surrounding the target to deal splash damage to
     * if there exist other surrounding actors, do half of the weapon damage to them
     * There will always be at least 1 surrounding actor since it damages the player itself too
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        Location targetLocation = map.locationOf(target);


        if (targetLocation != null) {
            String retUI = "Great Slam is used! ";
            int splashDamage = Math.round(weapon.damage() * SPLASH_DAMAGE_MULTIPLIER);
            // calculate stamina consumption
            int maxStamina = actor.getAttributeMaximum(BaseActorAttributes.STAMINA);
            int staminaConsumption = Math.round(maxStamina * STAMINA_CONSUMPTION_PERCENTAGE / 100.0f);

            // Does 100% damage to the main target
            AttackAction attackAction = new AttackAction(target, direction, weapon);
            retUI += (attackAction.execute(actor, map) + "\n" + "While damaging surrounding targets at the same time where:" + "\n");


            // Does 50% damage to splash target
            // Get target location and see if there are other enemies at its exits
            // if yes do damage with a 50% reduction


            for (Exit exit : targetLocation.getExits()) {
                Location surrounding = exit.getDestination();
                if (surrounding.getActor() != null) {
                    AttackAction attackActionSurrounding = new AttackAction(surrounding.getActor(), direction, weapon, splashDamage);
                    retUI += (attackActionSurrounding.execute(actor, map) + "\n");
                }
            }
            return retUI;
        }
        return "";
    }

    /**
     * Description of ability in the UI
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses Great Slam and slams " + target + " at " + direction + " with" + weapon;
    }
}
