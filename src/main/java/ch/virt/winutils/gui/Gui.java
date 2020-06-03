package ch.virt.winutils.gui;

import ch.virt.winutils.gui.components.ModuleGui;
import ch.virt.winutils.gui.components.SettingsGui;
import ch.virt.winutils.gui.components.TopBarGui;
import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public Gui(){
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
        topBar = new TopBarGui();
        topBar.init();

        modules = new ModuleGui();
        modules.init();

        settings = new SettingsGui();
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
    }
}
