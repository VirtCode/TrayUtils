package ch.virt.trayutils.modules;

import ch.virt.trayutils.event.Listener;

/**
 * This class is a simple KeyBind instance able to be processed by the software
 * @author VirtCode
 * @version 1.0
 */
public class KeyBind {
    private int[] codes;
    private boolean[] pressed;

    private Listener<Object> listener;

    public KeyBind(int[] codes, Listener<Object> listener){
        setCodes(codes);
        this.listener = listener;
    }

    public void setCodes(int[] codes) {
        this.codes = codes;
        this.pressed = new boolean[codes.length];
    }

    public void keyPressed(int id){
        for (int i = 0; i < codes.length; i++) {
            if (id == codes[i]) pressed[i] = true;
        }

        check();
    }

    public void keyReleased(int id){
        for (int i = 0; i < codes.length; i++) {
            if (id == codes[i]) pressed[i] = false;
        }
    }

    private void check(){
        for (boolean b : pressed) {
            if (!b) return;
        }

        listener.called(null);
    }
}
