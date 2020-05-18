package ch.virt.winutils.event;


/**
 * This class forwards event to the main class
 * @author VirtCode
 * @version 1.0
 */
public abstract class EventBus {
    /**
     * Saves the settings
     */
    public abstract void saveSettings();

    /**
     * Signalizes that a module has been pressed
     * @param id id of that module
     */
    public abstract void modulePressed(int id);

    /**
     * Evokes the new key bind dialog
     * @param listener listener for that
     */
    public abstract void getNewBind(Listener<Integer[]> listener);

    /**
     * Forces the client to choose a new base key bind for the application
     * (Use with care)
     */
    public abstract void chooseBaseBind();

    /**
     * Chooses a new key bind for the specified module
     * @param id id of that module
     */
    public abstract void chooseModuleBind(int id);

    /**
     * Quits the application
     */
    public abstract void quit();
}
