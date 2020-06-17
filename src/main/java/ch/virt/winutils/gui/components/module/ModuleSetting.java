package ch.virt.winutils.gui.components.module;

import ch.virt.winutils.event.Listener;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.gui.helper.GroupFactory;

import javax.swing.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ModuleSetting {
    private final int id;

    private JButton triggerButton;

    private JLabel title;
    private JButton launch;
    private JPanel changeBind;

    private JPanel settings;
    private JPanel specific;

    private JPanel parent;

    public ModuleSetting(int id, String title, String icon, JPanel specific, Listener<Integer> moduleCalled){
        this.id = id;
        this.specific = specific;

        this.triggerButton = ComponentFactory.createImageButton(icon);
        triggerButton.addActionListener(e -> moduleCalled.called(id));

        create(title);
        assign();
        listen();

    }

    private void create(String title){
        this.parent = ComponentFactory.createGroup();

        this.title = ComponentFactory.createHeader();
        this.title.setText(title);

        this.settings = GroupFactory.createSettingSubCategory();

        this.launch = ComponentFactory.createButton();
        launch.setText("Launch");

        this.changeBind = GroupFactory.createChangeKeyBindModule(new int[]{0}, "Keybind", arg -> {}, true);
    }

    private void assign(){
        parent.add(title);
        parent.add(settings);

        settings.add(launch);
        settings.add(changeBind);

        settings.add(specific);
    }

    private void listen(){
        this.launch.addActionListener(e -> System.out.println("Module Launched: " + id));
    }

    public int getId(){
        return id;
    }

    public JButton getTriggerButton(){
        return triggerButton;
    }

    public JPanel getGui(){
        return parent;
    }
}
