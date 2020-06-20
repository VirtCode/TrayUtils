package ch.virt.winutils.gui;

import ch.virt.winutils.event.GuiEventBus;
import ch.virt.winutils.event.Listener;
import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.components.ModuleGui;
import ch.virt.winutils.gui.components.SettingsGui;
import ch.virt.winutils.gui.components.TopBarGui;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.modules.ModuleLoader;
import ch.virt.winutils.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * This is the main gui instance
 * @author VirtCode
 * @version 1.0
 */
public class Gui {

    private JDialog frame;

    private JPanel currentMain;

    private TopBarGui topBar;
    private ModuleGui modules;
    private SettingsGui settings;

    private final MainEventBus mainEvents;
    private final GuiEventBus guiEvents;

    private final Settings settingsInstance;
    private final ModuleLoader modulesInstance;

    private final Listener<Object> focusLost;

    /**
     * Creates the main instance
     * @param eventBus main event bus
     * @param settings settings to manipulate
     * @param modules modules to use
     */
    public Gui(MainEventBus eventBus, Settings settings, ModuleLoader modules, Listener<Object> focusLost){
        this.mainEvents = eventBus;
        this.focusLost = focusLost;
        this.guiEvents = createGuiEvents();

        settingsInstance = settings;
        modulesInstance = modules;

        setupFrame();

        createComponents();
        assignComponents();

        finishFrame();
    }

    /**
     * Sets the frame up
     */
    public void setupFrame() {
        //ComponentFactory.initialize();
        frame = ComponentFactory.getMainFrame();

        Dimension bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
        int width = bounds.width / 6;
        int height = bounds.height / 2;
        int x = bounds.width - width - 10;
        int y = bounds.height - height;

        frame.setSize(width, height);
        frame.setLocation(x, y);

        frame.setLayout(new BorderLayout());

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                focusLost.called(null);
            }
        });
    }

    /**
     * Finishes the frame to display
     */
    public void finishFrame(){
        frame.setVisible(true);
    }

    /**
     * Creates the various components used
     */
    public void createComponents(){
        topBar = new TopBarGui(mainEvents, guiEvents);
        topBar.init();

        modules = new ModuleGui(modulesInstance, mainEvents);
        modules.init();

        settings = new SettingsGui(settingsInstance);
        settings.init();
    }

    /**
     * Assigns those components
     */
    public void assignComponents(){
        frame.add(topBar.getParent(), BorderLayout.PAGE_START);
        setMain(modules.getParent());
    }

    /**
     * Sets the currently displayed panel (settings or modules)
     * @param panel panel to display
     */
    public void setMain(JPanel panel){
        if(currentMain != null) frame.remove(currentMain);
        currentMain = panel;
        frame.add(currentMain);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Removes the gui
     */
    public void exitGui(){
        frame.setVisible(false);
        frame.dispose();
        frame = null;
    }

    /**
     * Creates the gui event bus
     * @return bus
     */
    private GuiEventBus createGuiEvents(){
        return new GuiEventBus() {
            @Override
            public void openSettings() {
                setMain(settings.getParent());
            }

            @Override
            public void openModules() {
                setMain(modules.getParent());
            }
        };
    }
}
