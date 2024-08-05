package game.actors.enemies.spawners;

import edu.monash.fit2099.engine.positions.Location;
import game.actors.enemies.Enemy;
import game.actors.enemies.ForestKeeper;
import game.actors.enemies.WanderingUndead;

/**
 * Spawner for wandering undead
 */
public class WanderingUndeadSpawner implements Spawner {

  private double spawnRate;

  public WanderingUndeadSpawner(double spawnRate) {
    this.spawnRate = spawnRate;
  }

  @Override
  public void spawnEnemy(Location location) {

    // if the location is free to spawn, determine spawn rate
    if (!location.containsAnActor() && Math.random() <= (spawnRate/100.0)){
      // create new instance of enemy to spawn
      Enemy spawningEnemy = new WanderingUndead();
      // inform player of spawn
      display.println("Quietly, a " + spawningEnemy + " emerges.");
      location.addActor(spawningEnemy);
    }
  }


}



