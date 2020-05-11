package ch.virt.winutils.modules;

import ch.virt.winutils.event.EventBus;
import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.modules.Module;
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
    private EventBus events;
    private InputBus inputs;

    public ModuleLoader(EventBus bus, InputBus inputs) {
        modules = new HashMap<>();
        this.events = bus;
        this.inputs = inputs;
    }

    public void registerModule(Module module){
        module.setBuses(events, inputs);
        module.create();
        modules.put(module.getID(), module);
    }

    public void keyEventForModule(int id){
        modules.get(id).keyStrokeCalled();
    }

    public MenuItem[] getSettingsMenus(){
        ArrayList<MenuItem> items = new ArrayList<>();
        for (Module value : modules.values()) if (value.settingsMenu() != null) items.add(value.settingsMenu());
        return items.toArray(new MenuItem[0]);
    }

    public void applySettings(ModuleSettings settings){
        if(modules.get(settings.getId()) != null) modules.get(settings.getId()).fromSettings(settings.getSettings());
    }

    public ModuleSettings getNewSpecificSettings(ModuleSettings settings){
        if(modules.get(settings.getId()) != null) settings.setSettings(modules.get(settings.getId()).toSettings());
        return settings;
    }
}
