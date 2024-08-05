package game.actors.enemies.spawners;

import edu.monash.fit2099.engine.positions.Location;
import game.actors.enemies.EldentreeGuardian;
import game.actors.enemies.Enemy;
import game.actors.enemies.HollowSoldier;
/**
 * Spawner for eldentree guardian
 */
public class EldentreeGuardianSpawner implements Spawner {

    private double spawnRate;

    public EldentreeGuardianSpawner(double spawnRate) {
        this.spawnRate = spawnRate;
    }

    @Override
    public void spawnEnemy(Location location) {

        // if the location is free to spawn, determine spawn rate
        if (!location.containsAnActor() && Math.random() <= (spawnRate/100.0)){
            // create new instance of enemy to spawn
            Enemy spawningEnemy = new EldentreeGuardian();
            // inform player of spawn
            display.println("Quietly, a " + spawningEnemy + " emerges.");
            location.addActor(spawningEnemy);
        }
    }


}


