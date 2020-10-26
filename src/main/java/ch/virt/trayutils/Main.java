package ch.virt.trayutils;

import ch.virt.trayutils.event.MainEventBus;
import ch.virt.trayutils.event.InputBus;
import ch.virt.trayutils.event.InputListener;
import ch.virt.trayutils.gui.GuiWrapper;
import ch.virt.trayutils.modules.JarModuleLoader;
import ch.virt.trayutils.modules.KeyBind;
import ch.virt.trayutils.modules.ModuleLoader;
import ch.virt.trayutils.ui.KeyChooser;
import ch.virt.trayutils.settings.Settings;
import ch.virt.trayutils.ui.Tray;

import java.util.Arrays;

/**
 * This is the main class
 * @author VirtCode
 * @version 1.0
 */
public class Main {
    private static final String TAG = "[Main] ";
    public static final String VERSION = "Release 1.2.1:";
    /**
     * This is just another main method - JAMM
     * @param args args of that method
     */
    public static void main(String[] args) {
        System.out.println(TAG + "Going to Start application");
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
        if (startup) System.out.println(TAG + "Is starting with OS");

        settings = Settings.load();
        if (startup && !settings.isStartWithSystem()){
            System.out.println(TAG + "Going to close because of starting with os is disabled");
            System.exit(0);
        }

        events = createEventBus();
        inputs = new InputBus();

        modules = new ModuleLoader(events, inputs);
        registerModules();

        applyModuleSettings();

        listener = new InputListener(settings, events, inputs, modules.getKeyModuleMap());
        KeyChooser.init(inputs);

        tray = new Tray(events);

        gui = new GuiWrapper(events, settings, modules);

        System.out.println(TAG + "Application started successfully");

        events.saveSettings();
    }

    /**
     * Registers the module into the system
     */
    private void registerModules(){
        if (settings.isLoadJarModules()) registerJarModules();
        // Register local Modules
    }

    /**
     * Loads the modules from jars
     */
    private void registerJarModules(){
        JarModuleLoader loader = new JarModuleLoader();

        loader.load();
        loader.registerFoundModules(modules);
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
                System.out.println(TAG + "Calling launch event for module: " + id);
                modules.keyEventForModule(id);
            }

            @Override
            public void quit() {
                System.out.println(TAG + "Going to exit!");
                settings.save();
                System.exit(0);
            }

            @Override
            public void toggleGui() {
                gui.toggle();
            }

            @Override
            public void showTrayNotification(String title, String text) {
                tray.showNotification(title, text);
            }
        };
    }
}
