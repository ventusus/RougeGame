package game.utils.monologues;

import edu.monash.fit2099.engine.actors.Actor;
import game.enums.Status;
import java.util.ArrayList;

/**
 * Class containing the Isolated Traveller's monologue.
 */
public class IsolatedTravellerMonologue extends Monologue {

  /**
   * Checks the listeningActor for certain conditions and returns a list of available monologue options.
   * @param listeningActor the actor listening to the monologue
   * @return the list of monologue options
   */
  @Override
  public ArrayList<String> getAvailableMonologue(Actor listeningActor) {

    ArrayList<String> availableMonologues = new ArrayList<>();

    // sus traveller lines
    String approach1 = "Of course, I will never give you up, valuable customer!";
    String approach2 = "I promise I will never let you down with the quality of the items that I sell.";
    String approach3 = "You can always find me here. I'm never gonna run around and desert you, dear customer!";
    String approach4 = "I'm never gonna make you cry with unfair prices.";
    String approach5 = "Trust is essential in this business. I promise I’m never gonna say goodbye to a valuable customer like you.";
    String approach6 = "Don't worry, I’m never gonna tell a lie and hurt you.";

    String holdsGiantHammer = "Ooh, that’s a fascinating weapon you got there. I will pay a good price for it. You wouldn't get this price from any other guy.";
    String abxervyerAlive = "You know the rules of this world, and so do I. Each area is ruled by a lord. Defeat the lord of this area, Abxervyer, and you may proceed to the next area.";
    String abxervyerDefeated = "Congratulations on defeating the lord of this area. I noticed you still hold on to that hammer. Why don’t you sell it to me? We've known each other for so long. I can tell you probably don’t need that weapon any longer.";

    availableMonologues.add(approach1);
    availableMonologues.add(approach2);
    availableMonologues.add(approach3);
    availableMonologues.add(approach4);
    availableMonologues.add(approach5);
    availableMonologues.add(approach6);

    // special conditions

    // if player has hammer
    if (listeningActor.hasCapability(Status.HAS_GIANT_HAMMER)) {
      availableMonologues.add(holdsGiantHammer);
      // if the abxervyer is also defeated
      if (listeningActor.hasCapability(Status.ABXERVYER_DEFEATED)) {
        availableMonologues.add(abxervyerDefeated);
      }
    }
    // if the abxervyer has not been defeated
    if (!listeningActor.hasCapability(Status.ABXERVYER_DEFEATED)) {
      availableMonologues.add(abxervyerAlive);
    }




    return availableMonologues;


  }

}
