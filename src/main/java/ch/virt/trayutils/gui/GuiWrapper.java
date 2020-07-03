package ch.virt.trayutils.gui;

import ch.virt.trayutils.event.MainEventBus;
import ch.virt.trayutils.modules.ModuleLoader;
import ch.virt.trayutils.settings.Settings;

/**
 * This class wrapps the gui to create and destroy it
 * (destroying it to save ram)
 * @author VirtCode
 * @version 1.0
 */
public class GuiWrapper {
    private static final String TAG = "[GuiWrapper] ";    
    private Thread thread;
    private Gui gui;
    private boolean alive;
    private final MainEventBus mainEvents;

    private final Settings settingsInstance;
    private final ModuleLoader modulesInstance;

    /**
     * Creates the gui wrapper
     * @param mainEvents main event bus
     * @param settingsInstance settings instance
     * @param modulesInstance module instance
     */
    public GuiWrapper(MainEventBus mainEvents, Settings settingsInstance, ModuleLoader modulesInstance) {
        this.mainEvents = mainEvents;
        this.settingsInstance = settingsInstance;
        this.modulesInstance = modulesInstance;
    }

    /**
     * Creates the gui
     */
    public void create(){
        if (alive) return;
        System.out.println(TAG + "Creating Gui");
        
        thread = new Thread(() -> gui = new Gui(mainEvents, settingsInstance, modulesInstance, arg -> destroy()));
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
        System.out.println(TAG + "Destroying Gui");
        alive = false;

        gui.exitGui();
        gui = null;
        thread.interrupt();
        thread = null;

        System.gc();
    }
}
