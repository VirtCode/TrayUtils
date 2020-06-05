package ch.virt.winutils.gui.components;

import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class SettingsGui {

    private JPanel parentGroup;

    private JCheckBox checkBox;
    private JTextField textField;

    public void init(){
        create();
        assign();
        listen();
    }

    private void create(){
        parentGroup = ComponentFactory.createPanel(ColorManager.mainBackground);
        parentGroup.setLayout(new BoxLayout(parentGroup, BoxLayout.Y_AXIS));
        checkBox = ComponentFactory.createCheckBox();
        checkBox.setText("This is a checkbox");
        textField = ComponentFactory.createTextField();
    }

    private void assign(){
        parentGroup.add(checkBox);
        parentGroup.add(textField);
    }

    private void listen(){

    }

    public JPanel getParent(){
        return parentGroup;
    }
}
