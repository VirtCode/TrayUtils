package ch.virt.winutils;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.event.InputListener;
import ch.virt.winutils.gui.GuiWrapper;
import ch.virt.winutils.modules.instances.ColorPickerModule;
import ch.virt.winutils.modules.ModuleLoader;
import ch.virt.winutils.ui.KeyChooser;
import ch.virt.winutils.settings.Settings;
import ch.virt.winutils.ui.Tray;

import java.util.Arrays;

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
        new Main(args);
    }

    private final Settings settings;
    private final ModuleLoader modules;

    private final MainEventBus events;
    private final InputBus inputs;
    private final InputListener listener;
             
    private final Tray tray;
    private final GuiWrapper gui;

    /**
     * Creates the MainObject
     */
    public Main(String[] args){
        boolean startup = Arrays.asList(args).contains("startup");

        Dialogs.initialize();

        events = createEventBus();
        inputs = new InputBus();

        settings = Settings.load();
        if (startup && !settings.isStartWithSystem()) System.exit(0);

        modules = new ModuleLoader(events, inputs);
        registerModules();

        applyModuleSettings();

        listener = new InputListener(settings, events, inputs, modules.getKeyModuleMap());
        KeyChooser.init(inputs);

        tray = new Tray(events);

        gui = new GuiWrapper(events, settings, modules);

        events.saveSettings();
    }

    /**
     * Registers the module into the system
     */
    private void registerModules(){
        modules.registerModule(new ColorPickerModule());
    }

    /**
     * Applies all the settings to the modules
     */
    private void applyModuleSettings(){
        modules.distributeSettings(settings.getModules());

    }

    /**
     * Renews the settings
     */
    private void renewSettings(){
        settings.setModules(modules.fetchSettings());
        settings.save();

        listener.refreshModuleBinds(settings);
        listener.refreshModuleBinds(modules.getKeyModuleMap());
    }

    /**
     * Creates the main event bus
     * @return created bus
     */
    private MainEventBus createEventBus(){
        return new MainEventBus() {
            @Override
            public void saveSettings() {
                renewSettings();
            }

            @Override
            public void modulePressed(int id) {
                modules.keyEventForModule(id);
            }

            @Override
            public void quit() {
                settings.save();
                System.exit(0);
            }

            @Override
            public void toggleGui() {
                gui.toggle();
            }
        };
    }
}
