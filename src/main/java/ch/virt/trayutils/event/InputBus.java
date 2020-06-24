package ch.virt.trayutils.event;

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
        keyPressedListeners = new ArrayList<>();
        keyReleasedListeners = new ArrayList<>();
        mousePressedListeners = new ArrayList<>();
        mouseReleasedListeners = new ArrayList<>();
    }

    public ArrayList<Listener<Integer>> keyPressedListeners;
    public void addKeyPressedListener(Listener<Integer> listener){
        keyPressedListeners.add(listener);
    }
    public void keyPressed(int keycode){
        for (Listener<Integer> keyPressedListener : keyPressedListeners) {
            keyPressedListener.called(keycode);
        }
    }

    public ArrayList<Listener<Integer>> keyReleasedListeners;
    public void addKeyReleasedListener(Listener<Integer> listener){
        keyReleasedListeners.add(listener);
    }
    public void keyReleased(int keycode){
        for (Listener<Integer> keyReleasedListener : keyReleasedListeners) {
            keyReleasedListener.called(keycode);
        }
    }

    public ArrayList<Listener<Integer>> mousePressedListeners;
    public void addMousePressedListener(Listener<Integer> listener){
        mousePressedListeners.add(listener);
    }
    public void mousePressed(int keycode){
        for (Listener<Integer> mousePressedListener : mousePressedListeners) {
            mousePressedListener.called(keycode);
        }
    }

    public ArrayList<Listener<Integer>> mouseReleasedListeners;
    public void addMouseReleasedListener(Listener<Integer> listener){
        mouseReleasedListeners.add(listener);
    }
    public void mouseReleased(int keycode){
        for (Listener<Integer> mouseReleasedListener : mouseReleasedListeners) {
            mouseReleasedListener.called(keycode);
        }
    }

}
