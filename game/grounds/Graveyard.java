package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.enemies.spawners.Spawner;

/**
 * Class representing a graveyard that can spawn spooky entities.
 */
public class Graveyard extends Ground {

  private Spawner spawner;

  /**
   * Constructor
   * @param spawner spawner
   */
  public Graveyard(Spawner spawner) {
    super('n');
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
