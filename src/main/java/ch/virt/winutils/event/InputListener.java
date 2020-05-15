package ch.virt.winutils.event;

import ch.virt.winutils.event.EventBus;
import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.settings.Settings;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles all user inputs and forwards them to listening modules
 * @author VirtCode
 * @version 1.0
 */
public class InputListener implements NativeKeyListener, NativeMouseListener {

    private EventBus events;
    private InputBus inputs;

    private HashMap<Integer, Integer> moduleKeyMap;

    private int[] baseKeys;
    private boolean[] basePressed;

    public InputListener(Settings settings, EventBus events, InputBus inputs){
        this.baseKeys = settings.getBaseKeyCodes();
        this.basePressed = new boolean[baseKeys.length];

        this.moduleKeyMap = settings.getKeyModuleMap();

        this.events = events;
        this.inputs = inputs;

        initNativeHook();
    }

    private void initNativeHook(){
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
        }

        GlobalScreen.addNativeKeyListener(this);
        GlobalScreen.addNativeMouseListener(this);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        inputs.keyPressed(nativeKeyEvent.getKeyCode());

        for (int i = 0; i < baseKeys.length; i++) {
            if (nativeKeyEvent.getKeyCode() == baseKeys[i]){
                basePressed[i] = true;
                return;
            }
        }

        if (areAllPressed()){
            if (moduleKeyMap.get(nativeKeyEvent.getKeyCode()) != null) events.modulePressed(moduleKeyMap.get(nativeKeyEvent.getKeyCode()));
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        for (int i = 0; i < baseKeys.length; i++) {
            if (nativeKeyEvent.getKeyCode() == baseKeys[i]){
                basePressed[i] = false;
                return;
            }
        }

        inputs.keyReleased(nativeKeyEvent.getKeyCode());
    }

    private boolean areAllPressed(){
        for (boolean b : basePressed) {
            if (!b) return false;
        }
        return true;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        inputs.mousePressed(nativeMouseEvent.getButton());
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        inputs.mouseReleased(nativeMouseEvent.getButton());
    }

    public void refreshBaseKeyCodes(int[] codes){
        this.baseKeys = codes;
        this.basePressed = new boolean[codes.length];
    }
}
