package ch.virt.trayutils.modules;

import ch.virt.trayutils.Utils;
import ch.virt.trayutils.event.Listener;

/**
 * This class is a simple KeyBind instance able to be processed by the software
 * @author VirtCode
 * @version 1.0
 */
public class KeyBind {
    private int[] codes;
    private boolean[] pressed;

    private final Listener<Object> listener;

    /**
     * Creates a keybind
     * @param codes keycodes the keybind listens for
     * @param listener listener to be called
     */
    public KeyBind(int[] codes, Listener<Object> listener){
        setCodes(codes);
        this.listener = listener;
    }

    /**
     * Sets the keycodes
     * @param codes keycodes
     */
    public void setCodes(int[] codes) {
        this.codes = codes;
        this.pressed = new boolean[codes.length];
    }

    /**
     * Called when a key is pressed
     * @param id int id of that pressed key
     * @return whether the shortcut was called
     */
    public boolean keyPressed(int id){
        for (int i = 0; i < codes.length; i++) {
            if (id == codes[i]) pressed[i] = true;
        }

        return check();
    }

    /**
     * Called when a key is released
     * @param id id of that key
     */
    public void keyReleased(int id){
        for (int i = 0; i < codes.length; i++) {
            if (id == codes[i]) pressed[i] = false;
        }
    }

    /**
     * Checks whether the listener should be called
     * @return was called
     */
    private boolean check(){
        for (boolean b : pressed) {
            if (!b) return false;
        }

        Utils.runNext(() -> listener.called(null));
        return true;
    }

    /**
     * Returns the keycodes used
     * @return keycodes used
     */
    public int[] getCodes() {
        return codes;
    }
}
