package game;

import game.actors.Player;
import game.utils.FancyMessage;
import game.utils.GameMapBuilder;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;

/**
 * The main class to start the game.
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Sandra Lo Yii Shin
 *
 */
public class Application {


  public static void main(String[] args) {

    // generate new world
    World world = new World(new Display());
    GameMapBuilder gameMapBuilder = new GameMapBuilder();

    // generate game maps
    gameMapBuilder.generateAllMaps();

    for (GameMap gameMap : gameMapBuilder.getMaps()){
      world.addGameMap(gameMap);
    }

    gameMapBuilder.addObjectsToMap();


    // add player to starting map
    GameMap theAbandonedVillage = gameMapBuilder.getGameMapByName("The Abandoned Village");
    // Player creation

    // FOR THE SAKE OF DEMONSTRATION (and my sanity), i gave player more HP <3
//     Player player = new Player("The Abstracted One", '@', 150, 200);
    Player player = new Player("The Abstracted One", '@', 10000, 200,theAbandonedVillage.at(29,5));

    world.addPlayer(player,theAbandonedVillage.at(29,5));

    // for overgrown sanctuary testing
//    GameMap theAbandonedVillage = gameMapBuilder.getGameMapByName("The Overgrown Sanctuary");
//    world.addPlayer(player,theAbandonedVillage.at(4,2));


    // TITLE CARD: DesignBorne
    for (String line : FancyMessage.TITLE.split("\n")) {
      new Display().println(line);
      try {
        Thread.sleep(200);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    world.run();
  }
}
