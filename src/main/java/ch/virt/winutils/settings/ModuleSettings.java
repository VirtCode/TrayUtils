package ch.virt.winutils.settings;

import ch.virt.winutils.modules.Module;
import com.google.gson.annotations.Expose;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ModuleSettings {
    @Expose
    private String settings;
    @Expose
    private int id;
    @Expose
    private int keyBinds;

    public ModuleSettings(Module module){
        this.settings = module.toSettings();
        this.id = module.getId();
        this.keyBinds = module.getKeyBind();
    }

    /**
     * Returns the settings of that module
     * @return settings of that module
     */
    public String getSettings() {
        return settings;
    }

    /**
     * sets the settings of that module
     * @param settings settings
     */
    public void setSettings(String settings) {
        this.settings = settings;
    }

    /**
     * Returns the id of that module
     * @return id of that module
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the additional key to be pressed for that module
     * @return additional key to be pressed for that module
     */
    public int getKeyBinds() {
        return keyBinds;
    }

    /**
     * Sets the keybind of the module
     * @param keyBinds keybinds to set
     */
    public void setKeyBinds(int keyBinds) {
        this.keyBinds = keyBinds;
    }
}
