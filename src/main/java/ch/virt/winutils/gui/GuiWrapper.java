package ch.virt.winutils.gui;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.modules.ModuleLoader;
import ch.virt.winutils.settings.Settings;

/**
 * This class wrapps the gui to create and destroy it
 * (destroying it to save ram)
 * @author VirtCode
 * @version 1.0
 */
public class GuiWrapper {
    private Thread thread;
    private Gui gui;
    private boolean alive;
    private boolean first;

    private MainEventBus mainEvents;

    private Settings settingsInstance;
    private ModuleLoader modulesInstance;

    /**
     * Creates the gui wrapper
     * @param mainEvents main event bus
     * @param settingsInstance settings instance
     * @param modulesInstance module instance
     */
    public GuiWrapper(MainEventBus mainEvents, Settings settingsInstance, ModuleLoader modulesInstance) {
        first = true;
        this.mainEvents = mainEvents;
        this.settingsInstance = settingsInstance;
        this.modulesInstance = modulesInstance;
    }

    /**
     * Creates the gui
     */
    public void create(){
        if (alive) return;

        thread = new Thread(() -> gui = new Gui(mainEvents, settingsInstance, modulesInstance));
        thread.start();

        alive = true;
    }

    /**
     * toggles the gui
     */
    public void toggle(){
        if (alive) destroy();
        else create();
    }

    /**
     * destroys the gui
     */
    public void destroy(){
        alive = false;

        gui.exitGui();
        gui = null;
        thread.interrupt();
        thread = null;

        System.gc();
    }
}
