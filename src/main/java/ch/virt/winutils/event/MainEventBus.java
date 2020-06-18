package ch.virt.winutils.event;


import ch.virt.winutils.settings.ModuleSettings;

/**
 * This class forwards event to the main class
 * @author VirtCode
 * @version 1.0
 */
public abstract class MainEventBus {
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
     * Returns the settings for the correspondent modules
     * @param id settings
     * @return modules
     */
    public abstract ModuleSettings getModuleSettings(int id);

    /**
     * Quits the application
     */
    public abstract void quit();

    /**
     * Hides the Gui of the application
     */
    public abstract void hideGui();

    /**
     * Shows the Gui of the application
     */
    public abstract void showGui();
}
