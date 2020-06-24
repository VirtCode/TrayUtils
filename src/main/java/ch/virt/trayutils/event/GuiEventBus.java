package ch.virt.trayutils.event;

/**
 * @author VirtCode
 * @version 1.0
 */
public abstract class GuiEventBus {

    /**
     * Evokes the settings gui
     */
    public abstract void openSettings();

    /**
     * Evokes the modules gui
     */
    public abstract void openModules();

}
