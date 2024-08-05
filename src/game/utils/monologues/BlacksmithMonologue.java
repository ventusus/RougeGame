package game.utils.monologues;

import edu.monash.fit2099.engine.actors.Actor;
import game.enums.Status;
import java.util.ArrayList;

/**
 * Class containing the Blacksmith's monologue.
 */
public class BlacksmithMonologue extends Monologue {

  /**
   * Checks the listeningActor for certain conditions and returns a list of available monologue options.
   * @param listeningActor the actor listening to the monologue
   * @return the list of monologue options
   */
  @Override
  public ArrayList<String> getAvailableMonologue(Actor listeningActor) {

    ArrayList<String> availableMonologues = new ArrayList<>();

    // blacksmith monologues
    String approach1 = "I used to be an adventurer like you, but then …. Nevermind, let’s get back to smithing.";
    String approach2 = "It’s dangerous to go alone. Take my creation with you on your adventure!";
    String approach3 = "Ah, it’s you. Let’s get back to make your weapons stronger.";

    String abxervyerAlive = "Beyond the burial ground, you’ll come across the ancient woods ruled by Abxervyer. Use my creation to slay them and proceed further!";
    String holdsGreatKnife = "Hey now, that’s a weapon from a foreign land that I have not seen for so long. I can upgrade it for you if you wish.";
    String abxervyerDefeated = "Somebody once told me that a sacred tree rules the land beyond the ancient woods until this day.";

    // add default monologues
    availableMonologues.add(approach1);
    availableMonologues.add(approach2);
    availableMonologues.add(approach3);

    // special conditions

    if (listeningActor.hasCapability(Status.HAS_GREAT_KNIFE)) {
      availableMonologues.add(holdsGreatKnife);
    }

    if (listeningActor.hasCapability(Status.ABXERVYER_DEFEATED)) {
      availableMonologues.add(abxervyerDefeated);
    } else {
      availableMonologues.add(abxervyerAlive);
    }

    return availableMonologues;
  }

}
