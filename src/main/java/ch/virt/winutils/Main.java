package ch.virt.winutils;

import ch.virt.winutils.event.EventBus;
import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.event.InputListener;
import ch.virt.winutils.modules.ColorPickerModule;
import ch.virt.winutils.modules.ModuleLoader;
import ch.virt.winutils.settings.ModuleSettings;
import ch.virt.winutils.settings.Settings;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        new Main();
    }

    Settings settings;
    ModuleLoader modules;
    EventBus events;
    InputBus inputs;
    InputListener listener;
    Tray tray;

    public Main(){
        events = new EventBus() {
            @Override
            public void saveSettings() {
                for (ModuleSettings module : settings.getModules()) settings.setModuleSettings(modules.getNewSpecificSettings(module));
                settings.save();
            }

            @Override
            public void modulePressed(int id) {
                modules.keyEventForModule(id);
            }
        };
        inputs = new InputBus();


        this.settings = Settings.load();
        modules = new ModuleLoader(events, inputs);
        modules.registerModule(new ColorPickerModule());

        for (ModuleSettings module : settings.getModules()) {
            modules.applySettings(module);
        }

        listener = new InputListener(settings, events, inputs);

        tray = new Tray(e -> {
            settings.save();
            System.exit(0);
        }, modules.getSettingsMenus());
    }
}
