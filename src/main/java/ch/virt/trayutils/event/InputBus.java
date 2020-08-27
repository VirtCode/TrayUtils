package ch.virt.trayutils.event;

import ch.virt.trayutils.Utils;
import ch.virt.trayutils.modules.KeyBind;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;

import java.util.ArrayList;

/**
 * This class forwards all keyboard and mouse events
 * The most methods explain themselves pretty well
 * @author VirtCode
 * @version 1.0
 */
public class InputBus {

    /**
     * Initializes the lists
     */
    public InputBus(){
        binds = new ArrayList<>();
        keyPressedListeners = new ArrayList<>();
        keyReleasedListeners = new ArrayList<>();
        mousePressedListeners = new ArrayList<>();
        mouseReleasedListeners = new ArrayList<>();
    }

    private final ArrayList<KeyBind> binds;
    public void addKeyBind(KeyBind bind){
        binds.add(bind);
    }
    public void removeKeyBind(KeyBind bind){
        binds.remove(bind);
    }

    private final ArrayList<Listener<Integer>> keyPressedListeners;
    public void addKeyPressedListener(Listener<Integer> listener){
        keyPressedListeners.add(listener);
    }
    public void keyPressed(NativeKeyEvent keycode){
        for (KeyBind bind : binds) {
            if (bind.keyPressed(keycode.getKeyCode())) InputListener.attemptConsumption(keycode);
        }
        for (Listener<Integer> keyPressedListener : keyPressedListeners) {
            Utils.runNext(() -> keyPressedListener.called(keycode.getKeyCode()));
        }
    }

    private final ArrayList<Listener<Integer>> keyReleasedListeners;
    public void addKeyReleasedListener(Listener<Integer> listener){
        keyReleasedListeners.add(listener);
    }
    public void keyReleased(NativeKeyEvent keycode){
        for (KeyBind bind : binds) {
            bind.keyReleased(keycode.getKeyCode());
        }
        for (Listener<Integer> keyReleasedListener : keyReleasedListeners) {
            Utils.runNext(() -> keyReleasedListener.called(keycode.getKeyCode()));
        }
    }

    private final ArrayList<Listener<Integer>> mousePressedListeners;
    public void addMousePressedListener(Listener<Integer> listener){
        mousePressedListeners.add(listener);
    }
    public void mousePressed(NativeMouseEvent keycode){
        for (Listener<Integer> mousePressedListener : mousePressedListeners) {
            Utils.runNext(() -> mousePressedListener.called(keycode.getButton()));
        }
    }

    private final ArrayList<Listener<Integer>> mouseReleasedListeners;
    public void addMouseReleasedListener(Listener<Integer> listener){
        mouseReleasedListeners.add(listener);
    }
    public void mouseReleased(NativeMouseEvent keycode){
        for (Listener<Integer> mouseReleasedListener : mouseReleasedListeners) {
            Utils.runNext(() -> mouseReleasedListener.called(keycode.getButton()));
        }
    }

}
