package game.utils.weathers;

import edu.monash.fit2099.engine.displays.Display;
import game.enums.Weather;

/**
 * Class representing the Weather Control Skill of an actor
 */
public class WeatherManager {
  /**
   * To store the current weather.
   */
  private static Weather currWeather;
  /**
   * The default weather is sunny.
   */
  private static final Weather DEFAULT_WEATHER = Weather.SUNNY;
  /**
   * The weather will be change after 3 turns
   */
  private static final int WEATHER_CHANGE_TURNS = 3;
  /**
   * To store current weather's turn
   */
  private int currWeatherTurns;

  /**
   * Constructor.
   */
  public WeatherManager() {
    // sunny as default weather
    currWeather = DEFAULT_WEATHER;
    this.currWeatherTurns = 0;
  }

  /**
   * Resets the weather to Sunny by default.
   */
  public void resetWeather(){
    changeWeather(DEFAULT_WEATHER);
  }

  /**
   * Alters the weather in the game.
   * @param weather weather to change to
   */
  public void changeWeather(Weather weather) {
    currWeather = weather;
  }

  /**
   * Returns the current weather of the game.
   * @return current game weather
   */
  public Weather getCurrWeather() {
    return currWeather;
  }


  /**
   * Displays the current weather.
   */
  public void displayWeather(){
    new Display().println(String.format("The weather is now %s", getCurrWeather()));
  }

  /**
   * Returns a new Weather to change to based on a fixed number of turns.
   * @return the Weather to change to
   */
  public Weather controlWeather() {
    currWeatherTurns++;
    Weather newWeather;

    // if enough turns passed, change weather and reset counter
    if (currWeatherTurns >= WEATHER_CHANGE_TURNS) {
      if (currWeather == Weather.SUNNY) {
        newWeather = Weather.RAINY;
      } else {
        newWeather = Weather.SUNNY;
      }
      currWeatherTurns = 0;
    }
    // otherwise, return the current weather
    else newWeather = getCurrWeather();
    return newWeather;
  }

}
