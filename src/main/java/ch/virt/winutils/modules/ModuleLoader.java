package ch.virt.winutils.modules;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.settings.ModuleSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ModuleLoader {

    private HashMap<Integer, Module> modules;
    private MainEventBus events;
    private InputBus inputs;

    public ModuleLoader(MainEventBus bus, InputBus inputs) {
        modules = new HashMap<>();
        this.events = bus;
        this.inputs = inputs;
    }

    public void registerModule(Module module){
        module.setBuses(events, inputs);
        module.create();
        modules.put(module.getId(), module);
    }

    public void keyEventForModule(int id){
        modules.get(id).keyStrokeCalled();
    }


    public void applySettings(ModuleSettings settings){
        if(modules.get(settings.getId()) != null){
            modules.get(settings.getId()).fromSettings(settings.getSettings());
            modules.get(settings.getId()).setKeyBind(settings.getKeyBinds());
        }
    }

    public ModuleSettings getNewSpecificSettings(ModuleSettings settings){
        if(modules.get(settings.getId()) != null) settings.setSettings(modules.get(settings.getId()).toSettings());
        return settings;
    }

    public Module[] getModules(){
        return modules.values().toArray(new Module[0]);
    }
}
