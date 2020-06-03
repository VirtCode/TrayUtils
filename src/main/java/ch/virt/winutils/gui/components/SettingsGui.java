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

    public void init(){
        create();
        assign();
        listen();
    }

    private void create(){
        parentGroup = ComponentFactory.createGroup();
    }

    private void assign(){

    }

    private void listen(){

    }

    public JPanel getParent(){
        return parentGroup;
    }
}
