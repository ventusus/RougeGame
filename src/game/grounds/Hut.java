package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import game.actors.enemies.spawners.Spawner;
/**
 * Class representing a hut that can spawn spooky entities.
 */
public class Hut extends Ground {

  private Spawner spawner;


  public Hut(Spawner spawner) {
    super('h');
    this.spawner = spawner;
  }

  /**
   * Spawns an enemy at a given rate each turn.
   * @param location The location of the Ground
   */
  @Override
  public void tick(Location location){
    super.tick(location);
    spawner.spawnEnemy(location);
  }

}