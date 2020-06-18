package ch.virt.winutils.gui;

import ch.virt.winutils.event.GuiEventBus;
import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.components.ModuleGui;
import ch.virt.winutils.gui.components.SettingsGui;
import ch.virt.winutils.gui.components.TopBarGui;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.modules.ModuleLoader;
import ch.virt.winutils.settings.Settings;

import javax.swing.*;
import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Gui {

    private JDialog frame;

    private JPanel currentMain;

    private TopBarGui topBar;
    private ModuleGui modules;
    private SettingsGui settings;

    private MainEventBus mainEvents;
    private GuiEventBus guiEvents;

    private Settings settingsInstance;
    private ModuleLoader modulesInstance;

    public Gui(MainEventBus eventBus, Settings settings, ModuleLoader modules){
        this.mainEvents = eventBus;
        this.guiEvents = createGuiEvents();

        settingsInstance = settings;
        modulesInstance = modules;

        setupFrame();

        createComponents();
        assignComponents();

        finishFrame();
    }

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
    }

    public void finishFrame(){
        frame.setVisible(true);
    }

    public void createComponents(){
        topBar = new TopBarGui(mainEvents, guiEvents);
        topBar.init();

        modules = new ModuleGui(modulesInstance);
        modules.init();

        settings = new SettingsGui(settingsInstance);
        settings.init();
    }

    public void assignComponents(){
        frame.add(topBar.getParent(), BorderLayout.PAGE_START);
        setMain(modules.getParent());
    }

    public void setMain(JPanel panel){
        if(currentMain != null) frame.remove(currentMain);
        currentMain = panel;
        frame.add(currentMain);
        frame.revalidate();
        frame.repaint();
    }

    public void exitGui(){
        frame.setVisible(false);
        frame.dispose();
        frame = null;
    }

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
