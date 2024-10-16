package game;

import java.util.ArrayList;

/**
 * A reset manager class that manages a list of resettables.
 */
public class ResetManager {
    private final ArrayList<Resettable> resettableList;
    private static ResetManager resetManager;

    /**
     * Constructor
     */

    public ResetManager() {
        this.resettableList = new ArrayList<>();
    }

    /**
     * Returns the singleton ResetManager
     * @return the singleton ResetManager
     */
    public static ResetManager getResetManager(){
        if (resetManager == null){
            resetManager = new ResetManager();
        }
        return resetManager;
    }

    /**
     * Execute reset for all resettable instances in the resettable list
     */
    public void runReset(){
        for(Resettable resettable: this.resettableList){
            resettable.reset();
        }
    }

    /**
     * To record a resettable
     * @param resettable Instance to be recorded
     */
    public void addToResetList(Resettable resettable){
        this.resettableList.add(resettable);
    }

    /**
     * To remove a resettable
     * @param object Instance to be removed
     */
    public void removeFromResetList(Object object){
        this.resettableList.remove(object);
    }

    /**
     * To get resettableList
     * @return resettableList
     */
    public ArrayList<Resettable> getResettableList(){
        return resettableList;
    }
}
