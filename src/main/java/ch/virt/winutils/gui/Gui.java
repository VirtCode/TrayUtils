package ch.virt.winutils.gui;

import ch.virt.winutils.event.GuiEventBus;
import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.components.ModuleGui;
import ch.virt.winutils.gui.components.SettingsGui;
import ch.virt.winutils.gui.components.TopBarGui;
import ch.virt.winutils.gui.helper.ComponentFactory;

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

    public Gui(MainEventBus eventBus){
        this.mainEvents = eventBus;
        this.guiEvents = createGuiEvents();

        setupFrame();

        createComponents();
        assignComponents();

        finishFrame();
    }

    public void setupFrame() {
        //ComponentFactory.initialize();
        frame = ComponentFactory.getMainFrame();

        Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
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

        modules = new ModuleGui();
        modules.init();

        settings = new SettingsGui();
        settings.init();
    }

    public void assignComponents(){
        frame.add(topBar.getParent(), BorderLayout.PAGE_START);
        setMain(settings.getParent());
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
                //Dialogs.showError("Settings not implemented yet!");
            }

            @Override
            public void openModules() {
                setMain(modules.getParent());
            }
        };
    }
}
