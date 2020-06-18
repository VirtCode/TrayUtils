package ch.virt.winutils.modules;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.event.InputBus;

import javax.swing.*;
import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public abstract class Module {
    protected MainEventBus eventBus;
    protected InputBus inputBus;

    protected int id;
    protected String name;
    protected int keyBind;
    protected String icon;

    public void setBuses(MainEventBus eventBus, InputBus inputBus) {
        this.inputBus = inputBus;
        this.eventBus = eventBus;
    }

    public Module(int id, String name, int keyBind, String icon){
        this.id = id;
        this.name = name;
        this.keyBind = keyBind;
        this.icon = icon;
    }

    public String getIconPath(){
        return icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void assignKeyBind(int bind){
        this.keyBind = bind;
    }

    public abstract void create();

    public abstract void keyStrokeCalled();

    public abstract JPanel settingsMenu();

    public abstract void fromSettings(String s);
    public abstract String toSettings();

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }
}
