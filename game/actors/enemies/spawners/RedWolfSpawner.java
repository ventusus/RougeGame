package game.actors.enemies.spawners;

import edu.monash.fit2099.engine.positions.Location;
import game.enums.Weather;
import game.utils.weathers.WeatherControllable;
import game.actors.enemies.Enemy;
import game.actors.enemies.RedWolf;
/**
 * Spawner for red wolf
 */
public class RedWolfSpawner implements Spawner, WeatherControllable {

  private final double RAINY_SPAWN_MULTIPLIER = 1.5;
  private double defaultSpawnRate;
  private double spawnRate;

  public RedWolfSpawner(double defaultSpawnRate) {
    this.defaultSpawnRate = defaultSpawnRate;
    this.spawnRate = defaultSpawnRate;
  }

  @Override
  public void spawnEnemy(Location location) {

    // if the weather has changed, change weather effects
    weatherEffect(weatherManager.getCurrWeather());

    // if the location is free to spawn, determine spawn rate
    if (!location.containsAnActor() && Math.random() <= (spawnRate / 100.0)) {
      // create new instance of enemy to spawn
      Enemy spawningEnemy = new RedWolf();
      // inform player of spawn
      display.println("Quietly, a " + spawningEnemy + " emerges.");
      location.addActor(spawningEnemy);
    }
  }

  @Override
  public void weatherEffect(Weather weather){
    // Effect: spawn rate 1.5 times
    if (weather == Weather.RAINY) {
      this.spawnRate = RAINY_SPAWN_MULTIPLIER * defaultSpawnRate;
      display.println("The Red Wolves are becoming more active...");
    }
    else {
      this.spawnRate = defaultSpawnRate;
      display.println("The Red Wolves are becoming less active...");
    }
  }

}
