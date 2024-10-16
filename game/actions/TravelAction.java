package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Class representing action to travel to somewhere new.
 */
public class TravelAction extends Action {

  private GameMap toGameMap;
  private String mapName;
  private final Location newLocation;

  /**
   * Constructor to create a travel action
   * @param toGameMap gameMap to travel to
   * @param mapName name of the gameMap to travel to
   * @param newLocation the spawning location on the gameMap to travel to
   */
  public TravelAction(GameMap toGameMap, String mapName, Location newLocation){
    this.toGameMap = toGameMap;
    this.mapName = mapName;
    this.newLocation = newLocation;
  }

  @Override
  public String execute(Actor actor, GameMap gameMap) {
    toGameMap.moveActor(actor, newLocation);
    return actor + " ventures forth to new grounds.";

  }

  @Override
  public String menuDescription(Actor actor){
    return actor + " travels to " + mapName;
  }

}
