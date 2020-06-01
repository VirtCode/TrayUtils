package ch.virt.winutils.gui;

import ch.virt.winutils.gui.helper.ComponentFactory;

/**
 * @author VirtCode
 * @version 1.0
 */
public class GuiWrapper {
    private Gui gui;
    private boolean alive;
    private boolean first;

    public GuiWrapper(){
        first = true;
    }

    public void create(){
        if (alive) return;
        if (first) {
            ComponentFactory.initialize();
            first = false;
        }
        gui = new Gui();
        alive = true;
    }

    public void destroy(){
        alive = false;
        gui = null;
        System.gc();
    }
}
