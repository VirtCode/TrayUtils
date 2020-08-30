package ch.virt.trayutils.modules;

import ch.virt.trayutils.event.MainEventBus;
import ch.virt.trayutils.event.InputBus;
import ch.virt.trayutils.gui.helper.ResourceHelper;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

/**
 * This class is the base class for a Module
 * @author VirtCode
 * @version 1.0
 */
public abstract class Module {
    protected MainEventBus eventBus;
    protected InputBus inputBus;

    protected final int id;
    protected final String name;
    protected final String description;
    protected String icon;

    protected boolean enabled;

    protected ExternalResourceLoader resourceLoader;

    /**
     * Used to apply the event busses to the module
     * @param eventBus main event bus
     * @param inputBus user input bus
     */
    public void setBuses(MainEventBus eventBus, InputBus inputBus) {
        this.inputBus = inputBus;
        this.eventBus = eventBus;
    }

    /**
     * Used to create a Module
     * @param id id of that module (should be unique)
     * @param name name of that module
     * @param icon path to the main icon of that module
     * @param description description of the module
     */
    public Module(int id, String name, String icon, String description){
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.enabled = true;
    }

    /**
     * Loads a resource from the set resource Loader
     * @param path path to load from
     * @return loaded stream
     */
    protected InputStream loadResource(String path){
        if (resourceLoader != null) return resourceLoader.load(path);
        return null;
    }

    public void setResourceLoader(ExternalResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Returns the path of the icon
     * @return string
     */
    public Image getIcon(){
        return ResourceHelper.loadImage(loadResource(icon));
    }

    /**
     * Returns the id of the module
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of that module
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether the module is enabled
     * @return is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the module is enabled
     * @param enabled whether enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Creates the module
     * (is called when the busses have been initialized)
     */
    public abstract void create();

    /**
     * Should create a panel with the settings things on
     * @return jpanel panel
     */
    public abstract JPanel settingsMenu();

    /**
     * Should load the settings in
     * @param s String settings
     */
    public abstract void fromSettings(JsonObject s);

    /**
     * Should convert settings to a String to save
     * @return String settings
     */
    public abstract JsonObject toSettings();

    /**
     * Returns the description of the module
     * @return String description
     */
    public String getDescription() {
        return description;
    }
}
