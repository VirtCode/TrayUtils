package ch.virt.winutils.event;

import ch.virt.winutils.settings.Settings;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.lang.annotation.Native;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles all user inputs and forwards them to listening modules
 * @author VirtCode
 * @version 1.0
 */
public class InputListener implements NativeKeyListener, NativeMouseListener {

    private MainEventBus events;
    private InputBus inputs;

    private HashMap<Integer, Integer> moduleKeyMap;

    private int[] baseKeys;
    private boolean[] basePressed;
    private int guiKey;

    private boolean consumeKeyEvents;

    /**
     * Creates an InputListener
     * @param settings settings to use for binds
     * @param events events to call
     * @param inputs inputs to call
     */
    public InputListener(Settings settings, MainEventBus events, InputBus inputs, HashMap<Integer, Integer> moduleKeyMap){
        this.baseKeys = settings.getBaseKeyCodes();
        this.basePressed = new boolean[baseKeys.length];
        this.guiKey = settings.getGuiKeyCode();
        this.consumeKeyEvents = settings.isConsumeKeys();

        this.moduleKeyMap = moduleKeyMap;

        this.events = events;
        this.inputs = inputs;

        initNativeHook();
    }

    /**
     * Sets the JNativeHook library up
     */
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
            boolean done = true;
            if (nativeKeyEvent.getKeyCode() == guiKey) events.toggleGui();
            else if (moduleKeyMap.get(nativeKeyEvent.getKeyCode()) != null) events.modulePressed(moduleKeyMap.get(nativeKeyEvent.getKeyCode()));
            else done = false;

            if (consumeKeyEvents && done) attemptConsumption(nativeKeyEvent);
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

    /**
     * Refreshes the settings having to do with the input listener
     * @param settings settings to refresh
     */
    public void refreshModuleBinds(Settings settings){
        this.baseKeys = settings.getBaseKeyCodes();
        this.basePressed = new boolean[baseKeys.length];
        this.guiKey = settings.getGuiKeyCode();
        this.consumeKeyEvents = settings.isConsumeKeys();
    }

    /**
     * Refreshes the module keycodes on the fly
     * @param binds new binds
     */
    public void refreshModuleBinds(HashMap<Integer, Integer> binds){
        this.moduleKeyMap = binds;
    }

    /**
     * Attempts the Consumption of one Key event
     * @param e event to consume
     */
    public void attemptConsumption(NativeKeyEvent e){
        try {
            Field f = NativeInputEvent.class.getDeclaredField("reserved");
            f.setAccessible(true);
            f.setShort(e, (short) 0x01);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            System.err.println("Failed to consume key event");
            ex.printStackTrace();
        }
    }
}
