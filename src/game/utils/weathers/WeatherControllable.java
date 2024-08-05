package game.utils.weathers;

import game.enums.Weather;

/**
 * Interface representing a controllable weather.
 */
public interface WeatherControllable {
  /**
   * Weather manager
   */

  WeatherManager weatherManager = new WeatherManager();

  /**
   * The weather effect
   * @param weather weather
   */

  void weatherEffect(Weather weather);

}


