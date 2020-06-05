package ch.virt.winutils.gui;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.helper.ComponentFactory;

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

    public GuiWrapper(MainEventBus eventBus){
        first = true;
        this.mainEvents = eventBus;
    }

    public void create(){
        if (alive) return;

        thread = new Thread(() -> gui = new Gui(mainEvents));
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
