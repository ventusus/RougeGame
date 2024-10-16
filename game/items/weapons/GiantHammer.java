package game.items.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.AttackAction;
import game.actions.Focus;
import game.actions.GreatSlam;
import game.actions.StabAndStep;
import game.enums.ItemCapability;
import game.enums.Status;
import game.items.Purchasable;
import game.items.Sellable;

/**
 * Weapon that can be picked up and used to attack enemies.
 * Deals 110 damage, with 80% attack accuracy
 * Special Skill: Great Slam
 * - Slams a specific target while doing splash damage to the enemy's surroundings
 */
public class GiantHammer extends WeaponItem implements Sellable {

    private final float SPLASH_DAMAGE_MULTIPLIER = 0.5f;
    private final int SKILL_STAMINA_COST = 5;


    /**
     * Constructor.
     */
    public GiantHammer() {
        super("Giant Hammer", 'P', 160, "slams", 90);

        // save default hit rate of sword
        this.addCapability(ItemCapability.SELLABLE);
        this.addCapability(Status.HAS_GIANT_HAMMER);
    }

    /**
     * Returns a list with an attack action and Great Slam action to be performed by the actor to another actor.
     * @param otherActor the other actor
     * @param location the location of the other actor
     * @return the list
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location){

        ActionList actions = new ActionList();
        // otherActor: target to attack
        if (!otherActor.hasCapability(Status.FRIENDLY)) {
            actions.add(new AttackAction(otherActor, location.toString(), this));
            actions.add(new GreatSlam(this, SKILL_STAMINA_COST, SPLASH_DAMAGE_MULTIPLIER, otherActor, location.toString()));
        }
        return actions;
    }


    /**
     * Sells this item.
     *
     * @param by the actor selling
     * @return a String description of effect of selling
     */
    @Override
    public String sold(Actor by) {
        String ret = "";
        ret += sellableManager.sellGiantHammer(this,by,250);
        return ret;
    }
}
