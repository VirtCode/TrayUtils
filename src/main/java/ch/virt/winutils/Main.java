package ch.virt.winutils;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.event.InputListener;
import ch.virt.winutils.event.Listener;
import ch.virt.winutils.gui.GuiWrapper;
import ch.virt.winutils.modules.Module;
import ch.virt.winutils.modules.instances.ColorPickerModule;
import ch.virt.winutils.modules.ModuleLoader;
import ch.virt.winutils.ui.KeyChooser;
import ch.virt.winutils.settings.ModuleSettings;
import ch.virt.winutils.settings.Settings;
import ch.virt.winutils.ui.Tray;

/**
 * This is the main class
 * @author VirtCode
 * @version 1.0
 */
public class Main {
    /**
     * This is just another main method - JAMM
     * @param args args of that method
     */
    public static void main(String[] args) {
        new Main();
    }

    private final Settings settings;
    private final ModuleLoader modules;

    private final MainEventBus events;
    private final InputBus inputs;
    private final InputListener listener;
             
    private final Tray tray;
    private final KeyChooser keyChooser;

    private final GuiWrapper gui;

    /**
     * Creates the MainObject
     */
    public Main(){
        Dialogs.initialize();

        events = createEventBus();
        inputs = new InputBus();

        settings = Settings.load();
        modules = new ModuleLoader(events, inputs);
        registerModules();

        listener = new InputListener(settings, events, inputs);
        keyChooser = new KeyChooser(inputs);

        tray = new Tray(events, modules.getModules(), settings.getBaseKeyCodes());

        gui = new GuiWrapper(events, settings, modules);

        events.saveSettings();
    }

    /**
     * Registers the module into the system
     */
    private void registerModules(){
        modules.registerModule(new ColorPickerModule());
        applyModuleSettings();
    }

    /**
     * Applies all the settings to the modules
     */
    private void applyModuleSettings(){
        for (ModuleSettings module : settings.getModules()) {
            modules.applySettings(module);
        }
    }

    /**
     * Creates the main event bus
     * @return created bus
     */
    private MainEventBus createEventBus(){
        return new MainEventBus() {
            @Override
            public void saveSettings() {
                for (Module module: modules.getModules()){
                    if (settings.getModuleSettings(module.getId()) != null) settings.setModuleSettings(modules.getNewSpecificSettings(settings.getModuleSettings(module.getId())));
                    else settings.setModuleSettings(new ModuleSettings(module));
                }
                tray.refreshPopupMenu(modules.getModules(), settings.getBaseKeyCodes());
                settings.save();
            }

            @Override
            public void modulePressed(int id) {
                modules.keyEventForModule(id);
            }

            @Override
            public void chooseBaseBind() {
                keyChooser.choose(arg -> {
                    int[] ints = new int[arg.length];
                    for (int i = 0; i < ints.length; i++) ints[i] = arg[i];
                    settings.setBaseKeyCodes(ints);
                    settings.save();
                    tray.refreshPopupMenu(modules.getModules(), settings.getBaseKeyCodes());
                    listener.refreshBaseKeyCodes(settings.getBaseKeyCodes());
                });
            }

            @Override
            public void chooseModuleBind(int id) {
                keyChooser.chooseOne(arg -> {
                    ModuleSettings ms = settings.getModuleSettings(id);
                    ms.setKeyBinds(arg);
                    settings.setModuleSettings(ms);
                    settings.save();
                    applyModuleSettings();
                    tray.refreshPopupMenu(modules.getModules(), settings.getBaseKeyCodes());
                    listener.refreshModuleBinds(settings.getKeyModuleMap());
                });
            }

            @Override
            public ModuleSettings getModuleSettings(int id) {
                return settings.getModuleSettings(id);
            }

            @Override
            public void quit() {
                settings.save();
                System.exit(0);
            }

            @Override
            public void hideGui() {
                Dialogs.showError("Hiding not implemented yet!");
            }

            @Override
            public void showGui() {
                gui.toggle();
            }
        };
    }
}
