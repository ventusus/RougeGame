package game.utils.monologues;

import edu.monash.fit2099.engine.actors.Actor;
import java.util.ArrayList;

/**
 * Abstract class representing Monologue available in game
 */
public abstract class Monologue {

  /**
   * Returns all monologue options that can be spoken by the talking actor.
   * @param listeningActor the actor listening to the monologue
   * @return list of monologue strings
   */
  public abstract ArrayList<String> getAvailableMonologue(Actor listeningActor);
}
