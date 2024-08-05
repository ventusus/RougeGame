package game.items.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.AttackAction;
import game.actions.StabAndStep;
import game.enums.ItemCapability;
import game.enums.Status;
import game.items.Purchasable;
import game.items.Sellable;
import game.items.Upgradable;

import java.util.Stack;


/**
 * Weapon that can be bought by the traveller and used to attack enemies.
 * Deals 75 damage, with 70% attack accuracy
 * Special Skill: Stab And Step
 * - Attacks an enemy and steps away to a safe place in a single turn
 */
public class GreatKnife extends WeaponItem implements Purchasable, Sellable, Upgradable {


    private final int SKILL_STAMINA_COST = 25;
//    private final StabAndStep STAB_AND_STEP_ACTION;
    private int upgradeLevel = 0;


    /**
     * Constructor for Great Knife.
     */
    public GreatKnife() {
        super("Great Knife", '>', 75, "stabs", 70);
        this.addCapability(ItemCapability.PURCHASABLE);
        this.addCapability(ItemCapability.SELLABLE);
        this.addCapability(ItemCapability.UPGRADABLE);
        this.addCapability(Status.HAS_GREAT_KNIFE);
    }

    @Override
    public int chanceToHit() {
        // Calculate hit rate including upgrades
        int baseHitRate = super.chanceToHit();
        return Math.round(baseHitRate * (1.0f + upgradeLevel * 0.01f));
    }

    public void upgradeLvl() {
        upgradeLevel++;
    }

    /**
     * Returns a list with an attack action and stab and step action to be performed by the actor to another actor.
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
            actions.add(new StabAndStep(this, SKILL_STAMINA_COST, otherActor, location.toString()));
        }
        return actions;
    }

    /**
     * Purchases this item.
     *
     * @param by the actor purchasing
     * @return a String description of effect of purchasing
     */
    @Override
    public String purchased(Actor by) {
        String ret = "";
        ret += purchasableManager.purchaseGreatKnife(this,by,300);
        return ret;
    }

    /**
     * Upgrades this item.
     *
     * @param by the actor upgrading
     * @return a String description of effect of upgrading
     */
    @Override
    public String upgraded(Actor by) {
        String ret = "";
        upgradeLvl();
        ret += upgradableManager.upgradeWeapon(this,by,2000);
        return ret;
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
        ret += sellableManager.sellGreatKnife(this,by,175);
        return ret;
    }
}