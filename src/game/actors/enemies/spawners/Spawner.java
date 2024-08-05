package game.actors.enemies.spawners;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Spawner
 */
public interface Spawner {

  Display display = new Display();
  void spawnEnemy(Location location);

}
