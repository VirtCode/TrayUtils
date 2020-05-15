package ch.virt.winutils.settings;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.sun.jdi.VoidType;

import java.io.*;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Settings {
    public static final String dir = System.getenv("APPDATA") + "/WinUtils/";
    public static final String file = "settings.json";

    @Expose
    private ArrayList<ModuleSettings> modules;
    @Expose
    private int[] baseKeyCodes = {3675, 42};

    public Settings() {
        this.modules = new ArrayList<>();
    }

    public static Settings load(){
        new File(dir).mkdir();
        File settings = new File(dir + file);
        if (settings.exists() && !settings.isDirectory()){
            try {
                return new Gson().fromJson(new FileReader(settings), Settings.class);
            } catch (FileNotFoundException e) {
                System.err.println("Failed to read Settingsfile");
                return new Settings();
            }
        }else return new Settings();
    }

    public ModuleSettings getModuleSettings(int id){
        for (ModuleSettings module : modules) {
            if (module.getId() == id) return module;
        }
        return null;
    }

    public ModuleSettings[] getModules() {
        return modules.toArray(new ModuleSettings[0]);
    }

    public void setModuleSettings(ModuleSettings settings){
        modules.removeIf(module -> module.getId() == settings.getId());
        modules.add(settings);
    }

    public int[] getBaseKeyCodes() {
        return baseKeyCodes;
    }

    public HashMap<Integer, Integer> getKeyModuleMap(){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (ModuleSettings module : modules) {
            map.put(module.getKeyBind(), module.getId());
        }
        return map;
    }

    public void save() {
        String json = new Gson().toJson(this);
        new File(dir).mkdir();
        File settings = new File(dir + file);
        try {
            FileWriter writer = new FileWriter(settings);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to write Settingsfile");
        }

    }

    public void setBaseKeyCodes(int[] baseKeyCodes) {
        this.baseKeyCodes = baseKeyCodes;
    }
}
