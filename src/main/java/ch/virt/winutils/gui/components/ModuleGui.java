package ch.virt.winutils.gui.components;

import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ModuleGui {

    private JPanel parentGroup;
    private JPanel sideGroup;
    private JPanel mainGroup;

    public void init(){
        create();
        assign();
        listen();
    }

    private void create(){
        parentGroup = ComponentFactory.createGroup();
        parentGroup.setLayout(new BorderLayout());

        sideGroup = ComponentFactory.createPanel(ColorManager.sideBackground);
        sideGroup.setLayout(new BoxLayout(sideGroup, BoxLayout.Y_AXIS));

        mainGroup = ComponentFactory.createPanel(ColorManager.mainBackground);

        //Test
        sideGroup.add(ComponentFactory.createImageButton("/icon_medium.png"));
        sideGroup.add(ComponentFactory.createImageButton("/icon_medium.png"));
        sideGroup.add(ComponentFactory.createImageButton("/icon_medium.png"));
        sideGroup.add(ComponentFactory.createImageButton("/icon_medium.png"));
    }

    private void assign(){
        parentGroup.add(sideGroup, BorderLayout.LINE_START);
        parentGroup.add(mainGroup, BorderLayout.CENTER);
    }

    private void listen(){

    }

    public JPanel getParent(){
        return parentGroup;
    }
}
