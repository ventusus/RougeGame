package game.actors.enemies;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.enums.Ability;
import game.enums.Status;
import game.items.HealingVial;
import game.items.OldKey;
import game.items.Runes;


/**
 * Class representing a WanderingUndead enemy.
 */
public class WanderingUndead extends Enemy {

    private int runesAmount;

    public WanderingUndead() {
        super("Wandering Undead", 't', 100);
        this.runesAmount = 50;
        this.addCapability(Ability.WANDER);
    }


    /**
     * WanderingUndead can use limbs to deal 30 damage with 50% hit rate.
     * @return WanderingUndead's intrinsic weapon
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        int damage = 30;
        int hitRate = 50;
        return new IntrinsicWeapon(damage, "punches", hitRate);
    }


    /**
     * Upon defeat, the WanderingUndead has a 20% chance to drop a HealingVial
     * and a 25% chance to drop an OldKey.
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return string description of the actor's death
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        OldKey oldKey = new OldKey();
        HealingVial healingVial = new HealingVial();
        Runes runes = new Runes(this.runesAmount);
        String ret = "";

        ret += this + " met their demise in the hand of " + actor;
        ret += oldKey.dropped(this, map.locationOf(this), 25);
        ret += healingVial.dropped(this, map.locationOf(this), 20);
        ret += runes.dropped(this,map.locationOf(this), this.runesDropRate);
        map.removeActor(this);
        return ret;
    }

    /**
     * Reset function for the related object.
     */
    @Override
    public void reset() {
        this.addCapability(Status.RESET);
    }
}
