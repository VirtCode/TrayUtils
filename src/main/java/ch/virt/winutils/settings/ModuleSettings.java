package ch.virt.winutils.settings;

import ch.virt.winutils.modules.Module;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

/**
 * This class is a settings instance for a single module
 * @author VirtCode
 * @version 1.0
 */
public class ModuleSettings {
    @Expose
    private int id;
    @Expose
    private boolean enabled;
    @Expose
    private int keyBinds;
    @Expose
    private JsonObject settings;

    /**
     * Creates a settings module for a module
     * @param module module to create for
     */
    public ModuleSettings(Module module){
        fetch(module);
    }

    /**
     * Fetches the information out of the module and puts into the variables
     * @param module module
     */
    public void fetch(Module module){
        this.settings = module.toSettings();
        this.id = module.getId();
        this.keyBinds = module.getKeyBind();
        this.enabled = module.isEnabled();
    }

    /**
     * Applies the settings to the given module
     * @param module module to apply to
     */
    public void apply(Module module){
        module.fromSettings(settings);
        module.assignKeyBind(keyBinds);
        module.setEnabled(enabled);
    }

    /**
     * Returns the id of that module
     * @return id of that module
     */
    public int getId() {
        return id;
    }
}
