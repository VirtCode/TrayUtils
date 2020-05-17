package ch.virt.winutils.settings;

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
    private int keyBind;

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
    public int getKeyBind() {
        return keyBind;
    }
}
