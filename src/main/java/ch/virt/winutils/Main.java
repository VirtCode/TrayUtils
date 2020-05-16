package ch.virt.winutils;

import ch.virt.winutils.event.EventBus;
import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.event.InputListener;
import ch.virt.winutils.event.Listener;
import ch.virt.winutils.modules.ColorPickerModule;
import ch.virt.winutils.modules.ModuleLoader;
import ch.virt.winutils.settings.KeyChooser;
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

    private final Settings settings;
    private final ModuleLoader modules;

    private final EventBus events;
    private final InputBus inputs;
    private final InputListener listener;
             
    private final Tray tray;
    private final KeyChooser keyChooser;

    public Main(){
        Dialogs.initialize();

        events = createEventBus();
        inputs = new InputBus();

        settings = Settings.load();
        modules = new ModuleLoader(events, inputs);
        registerModules();

        listener = new InputListener(settings, events, inputs);
        keyChooser = new KeyChooser(inputs);

        tray = new Tray(events, modules.getSettingsMenus(), settings.getBaseKeyCodes());
    }

    private void registerModules(){
        modules.registerModule(new ColorPickerModule());

        for (ModuleSettings module : settings.getModules()) {
            modules.applySettings(module);
        }
    }

    private  EventBus createEventBus(){
        return new EventBus() {
            @Override
            public void saveSettings() {
                for (ModuleSettings module : settings.getModules()) settings.setModuleSettings(modules.getNewSpecificSettings(module));
                tray.refreshPopupMenu(modules.getSettingsMenus(), settings.getBaseKeyCodes());
                settings.save();
            }

            @Override
            public void modulePressed(int id) {
                modules.keyEventForModule(id);
            }

            @Override
            public void getNewBind(Listener<Integer[]> listener) {
                keyChooser.choose(listener);
            }

            @Override
            public void chooseBaseBind() {
                keyChooser.choose(arg -> {
                    int[] ints = new int[arg.length];
                    for (int i = 0; i < ints.length; i++) ints[i] = arg[i];
                    settings.setBaseKeyCodes(ints);
                    settings.save();
                    tray.refreshPopupMenu(modules.getSettingsMenus(), settings.getBaseKeyCodes());
                    listener.refreshBaseKeyCodes(settings.getBaseKeyCodes());
                });
            }

            @Override
            public void quit() {
                settings.save();
                System.exit(0);
            }
        };
    }
}
