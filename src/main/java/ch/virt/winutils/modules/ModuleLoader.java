package ch.virt.winutils.modules;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.settings.ModuleSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Loads and handles the modules
 * @author VirtCode
 * @version 1.0
 */
public class ModuleLoader {

    private HashMap<Integer, Module> modules;
    private MainEventBus events;
    private InputBus inputs;

    /**
     * Creates a Module Loader
     * @param bus main event bus
     * @param inputs input bus
     */
    public ModuleLoader(MainEventBus bus, InputBus inputs) {
        modules = new HashMap<>();
        this.events = bus;
        this.inputs = inputs;
    }

    /**
     * Registers a new module to the loader
     * @param module module to register
     */
    public void registerModule(Module module){
        module.setBuses(events, inputs);
        module.create();
        modules.put(module.getId(), module);
    }

    /**
     * Calls a key event ot a module
     * @param id id of that module
     */
    public void keyEventForModule(int id){
        modules.get(id).keyStrokeCalled();
    }

    /**
     * applies the new module settings to a module
     * @param settings settings to apply
     */
    public void applySettings(ModuleSettings settings){
        if(modules.get(settings.getId()) != null){
            modules.get(settings.getId()).fromSettings(settings.getSettings());
            modules.get(settings.getId()).assignKeyBind(settings.getKeyBinds());
        }
    }

    /**
     * Gets new settings for a module
     * @param settings old settings
     * @return new settings
     */
    public ModuleSettings getNewSpecificSettings(ModuleSettings settings){
        if(modules.get(settings.getId()) != null) settings.setSettings(modules.get(settings.getId()).toSettings());
        return settings;
    }

    /**
     * Returns all the modules in a array
     * @return modules
     */
    public Module[] getModules(){
        return modules.values().toArray(new Module[0]);
    }
}
