package ch.virt.trayutils.modules;

import ch.virt.trayutils.event.MainEventBus;
import ch.virt.trayutils.event.InputBus;
import ch.virt.trayutils.settings.ModuleSettings;

import javax.swing.*;
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
        Module module = modules.get(id);
        if (module instanceof ActionModule && module.isEnabled()) ((ActionModule)module).keyStrokeCalled();
    }

    /**
     * applies the new module settings to a module
     * @param settings settings to apply
     */
    public void distributeSettings(ModuleSettings[] settings){
        for (ModuleSettings setting : settings) {
            if(modules.get(setting.getId()) != null){
                setting.apply(modules.get(setting.getId()));
            }
        }
    }

    /**
     * Fetches all settings of present modules
     * @return fetched settings
     */
    public ModuleSettings[] fetchSettings(){
        ModuleSettings[] settings = new ModuleSettings[modules.size()];
        Module[] modules = this.modules.values().toArray(new Module[0]);
        for (int i = 0; i < modules.length; i++) {
            settings[i] = new ModuleSettings(modules[i]);
        }
        return settings;
    }

    /**
     * Returns all the modules in a array
     * @return modules
     */
    public Module[] getModules(){
        return modules.values().toArray(new Module[0]);
    }

    /**
     * Returns a keybind to module hashMap
     * @return map
     */
    public HashMap<Integer, Integer> getKeyModuleMap(){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Module module : modules.values()) {
            if(module instanceof ActionModule) map.put(((ActionModule) module).getKeyBind(), module.getId());
        }
        return map;
    }
}
