package ch.virt.winutils.event;

import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author VirtCode
 * @version 1.0
 */
public abstract class EventBus {
    public abstract void saveSettings();
    public abstract void modulePressed(int id);
}
