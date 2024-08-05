package game.actors.enemies.spawners;

import edu.monash.fit2099.engine.positions.Location;
import game.actors.enemies.Enemy;
import game.actors.enemies.ForestKeeper;
import game.enums.Weather;
import game.utils.weathers.WeatherControllable;
/**
 * Spawner for forest keeper
 */

public class ForestKeeperSpawner implements Spawner, WeatherControllable {

  private final double SUNNY_SPAWN_MULTIPLIER = 2.0;
  private double defaultSpawnRate;
  private double spawnRate;

  public ForestKeeperSpawner(double defaultSpawnRate) {
    this.defaultSpawnRate = defaultSpawnRate;
    this.spawnRate = defaultSpawnRate;
  }

  @Override
  public void spawnEnemy(Location location) {

    // if the weather has changed, change weather effects
    weatherEffect(weatherManager.getCurrWeather());


    // if the location is free to spawn, determine spawn rate
    if (!location.containsAnActor() && Math.random() <= (spawnRate/100.0)){
      // create new instance of enemy to spawn
      Enemy spawningEnemy = new ForestKeeper();
      // inform player of spawn
      display.println("Quietly, a " + spawningEnemy + " emerges.");
      location.addActor(spawningEnemy);
    }
  }

  @Override
  public void weatherEffect(Weather weather) {

    // The huts spawn the “Forest Keeper” enemy at 2 times the original spawning rate
    if (weather == Weather.SUNNY) {
      this.spawnRate = SUNNY_SPAWN_MULTIPLIER * defaultSpawnRate;
      display.println("The Forest Keepers are becoming more active...");
    }
    else {
      this.spawnRate = defaultSpawnRate;
      display.println("The Forest Keepers are becoming less active...");
    }
  }
}

