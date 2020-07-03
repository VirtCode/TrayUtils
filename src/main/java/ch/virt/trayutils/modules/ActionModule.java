package ch.virt.trayutils.modules;

/**
 * This class is a more classic approach to a module
 * @author VirtCode
 * @version 1.0
 */
public abstract class ActionModule extends Module {
    protected int keyBind;

    /**
     * Used to create a Module
     * @param defaultKeyBind default keybind
     * @param id          id of that module (should be unique)
     * @param name        name of that module
     * @param icon        path to the main icon of that module
     * @param description description of the module
     */
    public ActionModule(int id, String name, String icon, String description, int defaultKeyBind) {
        super(id, name, icon, description);
        this.keyBind = defaultKeyBind;
    }

    /**
     * Is called when the module is executed
     */
    public abstract void keyStrokeCalled();

    /**
     * Returns the keybind of that module
     * @return int keybind
     */
    public int getKeyBind() {
        return keyBind;
    }

    /**
     * Assigns a new key bind to the module
     * @param bind new bind
     */
    public void assignKeyBind(int bind) {
        this.keyBind = bind;
    }
}
