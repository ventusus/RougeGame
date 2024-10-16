package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.utils.monologues.Monologue;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing a ListenAction to allow players to listen to monologues.
 */
public class ListenAction extends Action {

  private final Random random = new Random();
  private Actor talkingActor;
  private Monologue monologue;

  /**
   * Constructor.
   * @param talkingActor the actor talking
   * @param monologue the monologue spoken by the talkingActor
   */
  public ListenAction(Actor talkingActor,  Monologue monologue) {
    this.talkingActor = talkingActor;
    this.monologue = monologue;
  }

  /**
   * Executes a listen action to listen to monologues.
   * @param actor The actor performing the action.
   * @param map The map the actor is on.
   * @return one of the monologue Strings
   */
  @Override
  public String execute(Actor actor, GameMap map) {

    ArrayList<String> allMonologues = monologue.getAvailableMonologue(actor);

    if(!allMonologues.isEmpty()) {
      String chosenMonologue = allMonologues.get(random.nextInt(allMonologues.size()));
      return String.format("%s: %s", talkingActor, chosenMonologue);
    } else return "";

  }

  /**
   * Describe what action will be performed if this Action is chosen in the menu.
   *
   * @param actor The actor performing the action.
   * @return the action description to be displayed on the menu
   */
  @Override
  public String menuDescription(Actor actor) {
    return String.format("%s listens to %s", actor, talkingActor);
  }


}
