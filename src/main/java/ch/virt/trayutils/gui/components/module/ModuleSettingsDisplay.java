package ch.virt.trayutils.gui.components.module;

import ch.virt.trayutils.event.Listener;
import ch.virt.trayutils.event.MainEventBus;
import ch.virt.trayutils.gui.helper.ComponentFactory;
import ch.virt.trayutils.gui.helper.GroupFactory;
import ch.virt.trayutils.gui.helper.manager.StringManager;
import ch.virt.trayutils.modules.Module;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates the ui for a module
 * @author VirtCode
 * @version 1.0
 */
public class ModuleSettingsDisplay {
    private final Module module;

    private JButton triggerButton;

    private JLabel title;
    private JLabel description;
    private JButton launch;
    private JPanel changeBind;
    private JCheckBox enabled;

    private JPanel settings;
    private JSeparator separatorOne;
    private JSeparator separatorTwo;

    private JPanel parent;

    private final MainEventBus mainEventBus;

    /**
     * Creates a ModuleSettingsDisplay
     * @param module module to create settings from
     * @param mainEventBus main event bus
     * @param moduleCalled listener when the module gui should be displayed
     */
    public ModuleSettingsDisplay(Module module, MainEventBus mainEventBus, Listener<Integer> moduleCalled){
        this.module = module;
        this.mainEventBus = mainEventBus;

        this.triggerButton = ComponentFactory.createImageButton(module.getIconPath());
        triggerButton.addActionListener(e -> moduleCalled.called(module.getId()));

        create();
        assign();
        listen();
    }

    /**
     * Creates the necessary components
     */
    private void create(){
        this.parent = GroupFactory.createSettingSubCategory();
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));

        this.title = ComponentFactory.createLabelHeader();
        this.title.setText(module.getName());

        this.description = ComponentFactory.createLabel();
        this.description.setText("<html>" + module.getDescription() + "<html>");

        this.settings = GroupFactory.createSettingSubCategory();

        this.enabled = ComponentFactory.createCheckBox();
        enabled.setText(StringManager.enable);
        enabled.setSelected(module.isEnabled());

        this.separatorOne = ComponentFactory.createMenuSeparator();
        separatorOne.setMaximumSize(new Dimension(10000, 10));
        this.separatorTwo = ComponentFactory.createMenuSeparator();
        separatorTwo.setMaximumSize(new Dimension(10000, 10));

        this.launch = ComponentFactory.createButton();
        launch.setText(StringManager.launch);

        this.changeBind = GroupFactory.createChangeKeyBindModule(new int[]{module.getKeyBind()}, StringManager.keybind, arg -> {module.assignKeyBind(arg[0]); mainEventBus.saveSettings();}, true);
    }

    /**
     * Assigns the necessary components
     */
    private void assign(){
        parent.add(title);
        parent.add(settings);

        settings.add(description);
        settings.add(Box.createRigidArea(new Dimension(0, 8)));
        settings.add(separatorOne);
        settings.add(launch);
        settings.add(enabled);
        settings.add(changeBind);
        settings.add(Box.createRigidArea(new Dimension(0, 2)));
        settings.add(separatorTwo);

        settings.add(module.settingsMenu());
    }

    /**
     * Assigns listener to the components
     */
    private void listen(){
        launch.addActionListener(e -> mainEventBus.modulePressed(module.getId()));
        enabled.addChangeListener(e -> {
            module.setEnabled(enabled.isSelected());
            mainEventBus.saveSettings();
        });
    }

    /**
     * Returns the id of the display
     * @return id
     */
    public int getId(){
        return module.getId();
    }

    /**
     * Returns the module activation button
     * @return button
     */
    public JButton getTriggerButton(){
        return triggerButton;
    }

    /**
     * Returns the gui that should then be displayed
     * @return the gui
     */
    public JPanel getGui(){
        return parent;
    }
}
