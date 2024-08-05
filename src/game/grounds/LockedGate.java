package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.ResetManager;
import game.Resettable;
import game.enums.Ability;
import game.utils.GameMapBuilder;
import game.actions.TravelAction;
import game.actions.UnlockGateAction;
import game.enums.GroundCapability;

import java.util.ArrayList;

/**
 * Class representing a LockedGate that needs to be unlocked
 */
public class LockedGate extends Ground implements Resettable {

  private GameMap toGameMap;
  private Location newLocation;
  private String mapName;

  private ArrayList<GameMap> toGameMaps;
  private ArrayList<String> mapNames;
  private ArrayList<Location> newLocations;


  /**
   Default constructor
   * @param toGameMap the game map this gate has access to
   * @param newLocation location of spawn on the new game map
   * @param mapName name of the maps to go to
   */
  public LockedGate(GameMap toGameMap, String mapName, Location newLocation){
    super('=');
    this.addCapability(GroundCapability.BLOCKED);
    this.toGameMap = toGameMap;
    this.mapName = mapName;
    this.newLocation = newLocation;

  }
  /**
   * Constructor for more than 2 destinations
   * @param toGameMaps ArrayList containing the game maps this gate has access to
   * @param mapNames ArrayList containing name of the maps to go to
   * @param newLocations ArrayList containing locations of spawn on the new game map
   */
  public LockedGate(ArrayList<GameMap> toGameMaps, ArrayList<String> mapNames, ArrayList<Location> newLocations){
    super('=');
    this.addCapability(GroundCapability.BLOCKED);
    this.toGameMaps = toGameMaps;
    this.mapNames = mapNames;
    this.newLocations = newLocations;
  }


  /**
   * Actor can only enter if this ground is not blocked.
   * @param actor the Actor to check
   * @return a list of actions for the actor to perform on the locked gate
   */
  @Override
  public boolean canActorEnter(Actor actor) {
    return !this.hasCapability(GroundCapability.BLOCKED);
  }

  @Override
  public ActionList allowableActions(Actor actor, Location location, String direction) {
    ActionList actions = new ActionList();
    actions.add(new UnlockGateAction(location));
    ResetManager.getResetManager().addToResetList(this);
    // if gate is unlocked, and the actor interacting can travel
    if ((this.hasCapability(GroundCapability.TRANSPORTABLE) && actor.hasCapability(Ability.TRAVEL)&&(!this.hasCapability(GroundCapability.BLOCKED)))){
      if (toGameMaps != null) {
        for (int i = 0; i < this.toGameMaps.size(); i++) {
          toGameMap = this.toGameMaps.get(i);
          mapName = this.mapNames.get(i);
          newLocation = this.newLocations.get(i);
          actions.add(new TravelAction(toGameMap, mapName, newLocation));
        }
      } else {
        actions.add(new TravelAction(toGameMap, mapName, newLocation));
      }
    }

    return actions;
  }

  /**
   * Reset function for the related object.
   */
  @Override
  public void reset() {
    this.addCapability(GroundCapability.BLOCKED);
    this.removeCapability(GroundCapability.TRANSPORTABLE);
  }
}
