package ch.virt.winutils.settings;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
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
     * Returns a keybind to module hashMap
     * @return map
     */
    public HashMap<Integer, Integer> getKeyModuleMap(){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (ModuleSettings module : modules) {
            map.put(module.getKeyBinds(), module.getId());
        }
        return map;
    }
}
