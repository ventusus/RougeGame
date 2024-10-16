package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;

/**
 * Class representing an Action to consume items.
 */
public class ConsumeAction extends Action {

  /**
   * The item that can be consumed
   */
  private final Consumable consumable;

  /**
   * Constructor.
   * @param consumable the item that can be consumed
   */
  public ConsumeAction(Consumable consumable) {
    this.consumable = consumable;
  }

  @Override
  public String execute(Actor actor, GameMap gameMap) {
    String ret = consumable.consumed(actor);
    return ret;
  }

  @Override
  public String menuDescription(Actor actor){
    return actor + " consumes " + consumable;
  }


}
