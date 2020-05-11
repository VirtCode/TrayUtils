package ch.virt.winutils.modules;

import ch.virt.winutils.event.EventBus;
import ch.virt.winutils.event.InputBus;

import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public abstract class Module {
    protected EventBus eventBus;
    protected InputBus inputBus;

    public void setBuses(EventBus eventBus, InputBus inputBus) {
        this.inputBus = inputBus;
        this.eventBus = eventBus;
    }

    public abstract void create();

    public abstract int getID();

    public abstract void keyStrokeCalled();

    public abstract MenuItem settingsMenu();

    public abstract void fromSettings(String s);
    public abstract String toSettings();


}
