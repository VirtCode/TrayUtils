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

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public int getId() {
        return id;
    }

    public int getKeyBind() {
        return keyBind;
    }
}
