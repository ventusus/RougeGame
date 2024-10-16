package game.actors.enemies;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.ResetManager;
import game.Resettable;
import game.enums.Ability;
import game.enums.Status;
import game.items.HealingVial;
import game.items.RefreshingFlask;
import game.items.Runes;

/**
 * Class representing a Eldentree Guardian enemy.
 */
public class EldentreeGuardian extends Enemy implements Resettable {
    private int runesAmount;
    /**
     * Constructor.
     */
    public EldentreeGuardian(){
        super("Eldentree Guardian", 'e',250);
        this.runesAmount = 250;
        this.addCapability(Ability.VOID_IMMUNE);
        this.addCapability(Ability.FOLLOW);
        this.addCapability(Ability.WANDER);
    }


    /**
     * Eldentree Guardian can use limbs to deal 50 damage with 80% hit rate
     * @return modified intrinsic weapon object
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon(){
        int damage = 50;
        int hitRate = 80;
        return new IntrinsicWeapon(damage,"punches", hitRate);
    }


    /**
     * Upon defeat, the Eldentree Guardian has a 25% chance to drop a HealingVial
     * and a 15% chance to drop a RefreshingFlask.
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return string describing actor death
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {

        HealingVial healingVial = new HealingVial();
        RefreshingFlask refreshingFlask = new RefreshingFlask();
        Runes runes = new Runes(this.runesAmount);
        String ret = "";

        ret += this + " met their demise in the hand of " + actor;
        ret += healingVial.dropped(this, map.locationOf(this), 25);
        ret += refreshingFlask.dropped(this, map.locationOf(this), 15);
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
