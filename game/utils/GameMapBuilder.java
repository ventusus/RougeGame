package game.utils;

import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Blacksmith;
import game.actors.TheIsolatedTraveller;
import game.actors.enemies.Abxervyer;
import game.actors.enemies.spawners.*;
import game.grounds.Bush;
import game.grounds.Dirt;
import game.grounds.Floor;
import game.grounds.Graveyard;
import game.grounds.Hut;
import game.grounds.LockedGate;
import game.grounds.Puddle;
import game.grounds.Void;
import game.grounds.Wall;
import game.items.*;
import game.items.weapons.Broadsword;
import game.items.weapons.GiantHammer;
import game.items.weapons.GreatKnife;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility to build maps in Application.
 */
public class GameMapBuilder {


  // define new ground factory to make new terrains/grounds
  private final FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(),
      new Wall(), new Floor(), new Puddle(), new Void());

  // all maps stored and accessed from a hashmap
  private final HashMap<String, GameMap> mapsHashMap = new HashMap<>();

  /**
   * Builds all the maps required in the game.
   */
  public void generateAllMaps(){
    this.mapsHashMap.put("The Abandoned Village", new GameMap(groundFactory, Maps.THE_ABANDONED_VILLAGE));
    this.mapsHashMap.put("The Burial Ground", new GameMap(groundFactory, Maps.THE_BURIAL_GROUND));
    this.mapsHashMap.put("The Ancient Woods", new GameMap(groundFactory, Maps.THE_ANCIENT_WOODS));
    this.mapsHashMap.put("Abxervyer, The Forest Watcher", new GameMap(groundFactory, Maps.ABXERVYER_BATTLE_MAP));
    this.mapsHashMap.put("The Overgrown Sanctuary", new GameMap(groundFactory, Maps.THE_OVERGROWN_SANCTUARY));
  }

  /**
   * Returns all GameMap objects as a list.
   * @return arraylist of GameMaps
   */
  public ArrayList<GameMap> getMaps(){
    return new ArrayList<>(mapsHashMap.values());
  }

  /**
   * Returns a list of names of the maps available in game.
   * @return arraylist of GameMap names
   */
  public ArrayList<String> getMapNames(){ return new ArrayList<>(mapsHashMap.keySet());}

  /**
   * Retrieve a specific game map by its name.
   * @param mapName name of the map
   * @return the associated string map
   */
  public GameMap getGameMapByName(String mapName){
    return this.mapsHashMap.get(mapName);
  }

  /**
   * Populates the game maps with actors, items, grounds.
   */
  public void addObjectsToMap(){

    // define map names
    String villageName = "The Abandoned Village";
    String burialName = "The Burial Ground";
    String woodsName = "The Ancient Woods";
    String abxervyerMapName = "Abxervyer, The Forest Watcher";
    String sanctuaryName = "The Overgrown Sanctuary";

    // get all maps
    GameMap theAbandonedVillage = getGameMapByName(villageName);
    GameMap burialGround = getGameMapByName(burialName);
    GameMap ancientWoods = getGameMapByName(woodsName);
    GameMap abxervyerBattle = getGameMapByName(abxervyerMapName);
    GameMap overgrownSanctuary = getGameMapByName(sanctuaryName);

    // travel spawn sites
    Location abandonedVillageSpawnSite = theAbandonedVillage.at(29,5);
    Location burialGroundSpawnSite = burialGround.at(22,7);
    Location ancientWoodsSpawnSite = ancientWoods.at(20,4);
    Location abxervyerMapSpawnSite = abxervyerBattle.at(13,9);
    Location overgrownSanctuarySpawnSite = overgrownSanctuary.at(10,6);


    ArrayList<GameMap> gameMapList = new ArrayList<>();
    ArrayList<String> mapNameList = new ArrayList<>();
    ArrayList<Location> spawnLocationList = new ArrayList<>();

    // add Void
    for (int i = 15; i <= 23; i++) {
      for (int j = 9; j <= 11; j+=2){
        theAbandonedVillage.at(i, j+1).setGround(new Void());
      }
    }

    // add graveyard
    for (int i = 42; i <= 48; i +=6){
//      theAbandonedVillage.at(i,6).setGround(new Graveyard(new WanderingUndead(), 25));
      theAbandonedVillage.at(i,6).setGround(new Graveyard(new WanderingUndeadSpawner(25)));
    }

    // add Broadsword
    theAbandonedVillage.at(28,6).addItem(new Broadsword());
    // add LockedGate (linked to THE BURIAL GROUND)

    theAbandonedVillage.at(29,12).setGround(new LockedGate(burialGround, burialName,burialGroundSpawnSite));

    // add OldKey for testing
    theAbandonedVillage.at(29,8).addItem(new OldKey());
    // add Bloodberry for testing
    theAbandonedVillage.at(29,9).addItem(new Bloodberry());
    // add NPC Blacksmith
    theAbandonedVillage.at(27,5).addActor(new Blacksmith());
    theAbandonedVillage.at(28,5).addItem(new Runes(10000));
    // for testing purpose
//    theAbandonedVillage.at(30,5).addItem(new HealingVial());
//    theAbandonedVillage.at(31,5).addItem(new HealingVial());
//    theAbandonedVillage.at(30,5).addItem(new RefreshingFlask());
//    theAbandonedVillage.at(31,5).addItem(new RefreshingFlask());
    theAbandonedVillage.at(30,5).addItem(new GreatKnife());


    // TEMPORARY HACK
//    theAbandonedVillage.at(31,12).setGround(new LockedGate(abxervyerBattle,abxervyerMapName,abxervyerMapSpawnSite));


    // THE BURIAL GROUND

    // add LockedGate back to abandoned village and ancient woods
    burialGround.at(22,14).setGround(new LockedGate(theAbandonedVillage,villageName,abandonedVillageSpawnSite));
    burialGround.at(25,14).setGround(new LockedGate(ancientWoods,woodsName,ancientWoodsSpawnSite));

    // add graveyard to spawn Hollow Soldiers
    for (int i = 23; i <= 30; i += 3){
      burialGround.at(i,4).setGround(new Graveyard(new HollowSoldierSpawner(10)));

    }

    // THE ANCIENT WOODS

    // add LockedGate back to burial ground
    ancientWoods.at(20,11).setGround(new LockedGate(burialGround,burialName,burialGroundSpawnSite));
    ancientWoods.at(22,11).setGround(new LockedGate(abxervyerBattle,abxervyerMapName,abxervyerMapSpawnSite));
    // add Hut to spawn Forest Keepers
    ancientWoods.at(29,4).setGround(new Hut(new ForestKeeperSpawner(15)));
    // add Bush to spawn Red Wolves
    ancientWoods.at(9,4).setGround(new Bush(new RedWolfSpawner(30)));
    // add isolated traveller
    ancientWoods.at(21,4).addActor(new TheIsolatedTraveller());
    // test purchase purpose
    ancientWoods.at(21,3).addItem(new Runes(1000));

    // test boss purpose
//    ancientWoods.at(15,8).addActor(new Abxervyer(new LockedGate(gameMapList,mapNameList,spawnLocationList)));
//    ancientWoods.at(22,4).addItem(new GiantHammer());


    // The Overgrown Sanctuary
    overgrownSanctuary.at(28,5).setGround(new Graveyard(new HollowSoldierSpawner(10)));
    overgrownSanctuary.at(6,13).setGround(new Hut(new EldentreeGuardianSpawner(20)));
    overgrownSanctuary.at(7,13).setGround(new Hut(new EldentreeGuardianSpawner(20)));
    overgrownSanctuary.at(4,1).setGround(new LockedGate(abxervyerBattle, abxervyerMapName, abxervyerMapSpawnSite));

    overgrownSanctuary.at(5, 10).setGround(new Bush(new LivingBranchSpawner(90)));

    // ABXERVYER, THE FOREST WATCHER
    abxervyerBattle.at(16,16).setGround(new Hut(new ForestKeeperSpawner(15)));
    abxervyerBattle.at(7,6).setGround(new Bush(new RedWolfSpawner(30)));
    // boss fight

    gameMapList.add(ancientWoods);
    gameMapList.add(overgrownSanctuary);

    mapNameList.add(woodsName);
    mapNameList.add(sanctuaryName);

    spawnLocationList.add(ancientWoodsSpawnSite);
    spawnLocationList.add(overgrownSanctuarySpawnSite);

//    abxervyerBattle.at(15,8).setGround(new LockedGate(gameMapList,mapNameList,spawnLocationList));
    abxervyerBattle.at(15,8).addActor(new Abxervyer(new LockedGate(gameMapList,mapNameList,spawnLocationList)));
    abxervyerBattle.at(13,11).addItem(new GiantHammer());



  }


}
