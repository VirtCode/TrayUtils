package ch.virt.winutils.gui;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.modules.ModuleLoader;
import ch.virt.winutils.settings.Settings;

/**
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

    public GuiWrapper(MainEventBus mainEvents, Settings settingsInstance, ModuleLoader modulesInstance) {
        first = true;
        this.mainEvents = mainEvents;
        this.settingsInstance = settingsInstance;
        this.modulesInstance = modulesInstance;
    }

    public void create(){
        if (alive) return;

        thread = new Thread(() -> gui = new Gui(mainEvents, settingsInstance, modulesInstance));
        thread.start();

        alive = true;
    }

    public void toggle(){
        if (alive) destroy();
        else create();
    }

    public void destroy(){
        alive = false;

        gui.exitGui();
        gui = null;
        thread.interrupt();
        thread = null;

        System.gc();
    }
}
