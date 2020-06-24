package ch.virt.trayutils.settings;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class saves and loads the settings
 * @author VirtCode
 * @version 1.0
 */
public class Settings {
    public static final String DIR = System.getenv("APPDATA") + "/WinUtils/";
    public static final String FILE = "settings.json";

    @Expose
    private ArrayList<ModuleSettings> modules;
    @Expose
    private int[] baseKeyCodes = {3675, 42};
    @Expose
    private int guiKeyCode = 52;
    @Expose
    private boolean consumeKeys = true;
    @Expose
    private boolean startWithSystem = true;

    /**
     * Creates empty settings
     */
    public Settings() {
        this.modules = new ArrayList<>();
    }

    /**
     * Loads previously saved Settings
     * @return loaded settings
     */
    public static Settings load(){
        new File(DIR).mkdir();
        File settings = new File(DIR + FILE);
        if (settings.exists() && !settings.isDirectory()){
            try {
                return new Gson().fromJson(new FileReader(settings), Settings.class);
            } catch (FileNotFoundException e) {
                System.err.println("Failed to read Settingsfile");
                return new Settings();
            }
        }else return new Settings();
    }

    /**
     * Writes the settings to file
     */
    public void save() {
        String json = new Gson().toJson(this);
        new File(DIR).mkdir();
        File settings = new File(DIR + FILE);
        try {
            FileWriter writer = new FileWriter(settings);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to write Settingsfile");
        }
    }

    /**
     * Returns the settings for a module
     * @param id id of that module
     * @return settings of that module
     */
    public ModuleSettings getModuleSettings(int id){
        for (ModuleSettings module : modules) {
            if (module.getId() == id) return module;
        }
        return null;
    }

    /**
     * Returns all settings for all modules
     * @return settings
     */
    public ModuleSettings[] getModules() {
        return modules.toArray(new ModuleSettings[0]);
    }

    public void setModules(ModuleSettings[] settings){
        modules = new ArrayList<>(Arrays.asList(settings));
    }

    /**
     * Replaces or adds the settings for a module
     * @param settings settings to add
     */
    public void setModuleSettings(ModuleSettings settings){
        modules.removeIf(module -> module.getId() == settings.getId());
        modules.add(settings);
    }

    /**
     * Returns the base key codes
     * @return base key codes
     */
    public int[] getBaseKeyCodes() {
        return baseKeyCodes;
    }

    /**
     * Replaces the base key codes
     * @param baseKeyCodes codes
     */
    public void setBaseKeyCodes(int[] baseKeyCodes) {
        this.baseKeyCodes = baseKeyCodes;
    }

    /**
     * Returns the key code for the gui to be triggered
     * @return key code for the gui to be triggered
     */
    public int getGuiKeyCode() {
        return guiKeyCode;
    }

    /**
     * Sets the gui keycode
     * @param guiKeyCode new keycode
     */
    public void setGuiKeyCode(int guiKeyCode) {
        this.guiKeyCode = guiKeyCode;
    }

    /**
     * Return whether the system should consume key events
     * @return should consume key events
     */
    public boolean isConsumeKeys() {
        return consumeKeys;
    }

    /**
     * Set whether the system should consume key events
     * @param consumeKeys should consume key events
     */
    public void setConsumeKeys(boolean consumeKeys) {
        this.consumeKeys = consumeKeys;
    }

    /**
     * Returns whether the program should start with the operating system
     * @return whether the program should start with the os
     */
    public boolean isStartWithSystem() {
        return startWithSystem;
    }

    /**
     * Sets whether the program should start with the system
     * @param startWithSystem start with system
     */
    public void setStartWithSystem(boolean startWithSystem) {
        this.startWithSystem = startWithSystem;
    }
}
