package game.actors.enemies;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Resettable;
import game.behaviours.WanderBehaviour;
import game.enums.Ability;
import game.enums.Status;
import game.items.Bloodberry;
import game.items.HealingVial;
import game.items.RefreshingFlask;
import game.items.Runes;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a Living Branch enemy.
 */
public class LivingBranch extends Enemy implements Resettable {

    private Map<Integer, Behaviour> behaviours = new HashMap<>();
    private int runesAmount;
    /**
     * Constructor.
     */
    public LivingBranch(){
        super("Living Branch", '?',75);
        this.runesAmount = 500;
        this.addCapability(Ability.VOID_IMMUNE);

    }


    /**
     * Eldentree Guardian can use limbs to deal 50 damage with 80% hit rate
     * @return modified intrinsic weapon object
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon(){
        int damage = 250;
        int hitRate = 90;
        return new IntrinsicWeapon(damage,"punches", hitRate);
    }


    /**
     * Upon defeat, the Living Branch has a 50% chance to drop a Bloodberry
     * and a 15% chance to drop a RefreshingFlask.
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return string describing actor death
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {

        Bloodberry bloodberry = new Bloodberry();
        Runes runes = new Runes(this.runesAmount);
        String ret = "";

        ret += this + " met their demise in the hand of " + actor;
        ret += bloodberry.dropped(this, map.locationOf(this), 50);
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
