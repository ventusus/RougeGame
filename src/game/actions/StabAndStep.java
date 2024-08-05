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
 * Weapon skill action. Stabs enemy and steps away to safety
 */
public class StabAndStep extends Action {
    private final WeaponItem weapon;
    private final Actor target;

    private Random random = new Random();
    private final String direction;

    private final int STAMINA_CONSUMPTION_PERCENTAGE;


    /**
     * Constructor.
     * @param weapon The weapon this skill takes effect on.
     * @param target The other actor this skill affects.
     * @param direction direction to use the ability at.
     * @param STAMINA_CONSUMPTION_PERCENTAGE Percentage to consume the actor's stamina by.
     */
    public StabAndStep(WeaponItem weapon, int STAMINA_CONSUMPTION_PERCENTAGE, Actor target, String direction) {
        this.weapon = weapon;
        this.target = target;
        this.direction = direction;
        this.STAMINA_CONSUMPTION_PERCENTAGE = STAMINA_CONSUMPTION_PERCENTAGE;
    }


    /**
     * Attacks an enemy and takes a step away to a safe spot.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A message displayed in the UI of what the actor is doing
     *
     *  Calls the attack action using the weapon Great Knife at the target
     *  Searches for possible surrounding exits to step away to after attacking the enemy
     *      if there are multiple exits, randomly select one to go to
     *      if there's none, attack the enemy but doesn't move away
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        int damage = weapon.damage();
        // calculate stamina consumption
        int maxStamina = actor.getAttributeMaximum(BaseActorAttributes.STAMINA);
        int staminaConsumption = Math.round(maxStamina * STAMINA_CONSUMPTION_PERCENTAGE / 100.0f);

        AttackAction attackAction = new AttackAction(target, direction, weapon);
        attackAction.execute(actor, map);

        // List to store actions to move to locations available
        ArrayList<Action> locationsAvailable = new ArrayList<>();

        // List to store those locations for UI clarity purposes
        ArrayList<Location> locationsUI = new ArrayList<>();

        // gets the possible exits
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();

            // check if the actor can enter this area, if yes store it into the lists
            if (destination.canActorEnter(actor)) {
                locationsAvailable.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
                locationsUI.add(exit.getDestination());
            }
        }
        // If there are locations unoccupied
        if (!locationsAvailable.isEmpty()) {
            // choose a random location from the list
            int chosen = random.nextInt(locationsAvailable.size());

            // execute a random action
            locationsAvailable.get(chosen).execute(actor,map);

            // return where the actor moves to
            return actor + " " + weapon.verb() + " " + target + " for " + damage + " damage." + actor + " moves to ( " + locationsUI.get(chosen).x() + " , " + locationsUI.get(chosen).y() + " )";
        }
        else {
           return actor + " " + weapon.verb() + " " + target + " for " + damage + " damage."+ actor + " unable to step away";
        }

    }

    /**
     * Description of ability in the UI
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses Stab and Step and attacks " + target + " at " + direction + " with " + weapon;
    }
}
