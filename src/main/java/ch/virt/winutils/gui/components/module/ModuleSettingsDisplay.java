package ch.virt.winutils.gui.components.module;

import ch.virt.winutils.event.Listener;
import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.gui.helper.GroupFactory;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates the ui for a module
 * @author VirtCode
 * @version 1.0
 */
public class ModuleSettingsDisplay {
    private final int id;

    private JButton triggerButton;

    private JLabel title;
    private JButton launch;
    private JPanel changeBind;

    private JPanel settings;
    private JPanel specific;

    private JPanel parent;

    private final MainEventBus mainEventBus;

    /**
     * Creates a ModuleSettingsDisplay
     * @param id id of that module
     * @param title title of that module
     * @param icon path to icon of that module
     * @param specific specific gui part of that module
     * @param mainEventBus main event bus
     * @param moduleCalled listener when the module gui should be displayed
     */
    public ModuleSettingsDisplay(int id, String title, String icon, JPanel specific, MainEventBus mainEventBus, Listener<Integer> moduleCalled){
        this.id = id;
        this.specific = specific;
        this.mainEventBus = mainEventBus;

        this.triggerButton = ComponentFactory.createImageButton(icon);
        triggerButton.addActionListener(e -> moduleCalled.called(id));

        create(title);
        assign();
        listen();
    }

    /**
     * Creates the necessary components
     * @param title title of the module
     */
    private void create(String title){
        this.parent = GroupFactory.createSettingSubCategory();
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));

        this.title = ComponentFactory.createLabelHeader();
        this.title.setText(title);

        this.settings = GroupFactory.createSettingSubCategory();

        this.launch = ComponentFactory.createButton();
        launch.setText("Launch");

        this.changeBind = GroupFactory.createChangeKeyBindModule(new int[]{mainEventBus.getModuleSettings(id).getKeyBinds()}, "Keybind", arg -> mainEventBus.chooseModuleBind(id), true);
    }

    /**
     * Assigns the necessary components
     */
    private void assign(){
        parent.add(title);
        parent.add(settings);

        settings.add(launch);
        settings.add(Box.createRigidArea(new Dimension(0, 8)));
        settings.add(changeBind);
        settings.add(Box.createRigidArea(new Dimension(0, 8)));

        settings.add(specific);
    }

    /**
     * Assigns listener to the components
     */
    private void listen(){
        launch.addActionListener(e -> mainEventBus.modulePressed(id));
    }

    /**
     * Returns the id of the display
     * @return id
     */
    public int getId(){
        return id;
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
     * @return
     */
    public JPanel getGui(){
        return parent;
    }
}
