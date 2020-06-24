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
     * Quits the application
     */
    public abstract void quit();

    /**
     * Shows the Gui of the application
     */
    public abstract void toggleGui();
}
