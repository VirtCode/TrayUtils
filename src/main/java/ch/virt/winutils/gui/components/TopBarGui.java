package ch.virt.winutils.gui.components;

import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class TopBarGui {

    private JPanel parentGroup;

    private JButton menuButton;
    private JLabel titleLabel;
    private JLabel titleImage;
    private JPopupMenu mainMenu;
    private JMenuItem hide;
    private JMenuItem exit;
    private JMenuItem about;
    private JMenuItem website;
    private JMenuItem settings;

    public void init(){
        create();
        assign();
        listen();
    }

    private void create(){
        parentGroup = ComponentFactory.createPanel(ColorManager.topBackground);
        parentGroup.setLayout(new BorderLayout());

        titleImage = ComponentFactory.createFlatImage("/icon_medium.png");
        titleLabel = ComponentFactory.createLabel();
        titleLabel.setText("WinUtils Settings");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuButton = ComponentFactory.createImageButton("/menu_burger.png");

        mainMenu = ComponentFactory.createPopupMenu();

        exit = ComponentFactory.createMenuItem();
        exit.setText("Exit");
        hide = ComponentFactory.createMenuItem();
        hide.setText("Hide");
        about = ComponentFactory.createMenuItem();
        about.setText("About");
        website = ComponentFactory.createMenuItem();
        website.setText("Website");
        settings = ComponentFactory.createMenuItem();
        settings.setText("Settings");
    }

    private void assign(){
        parentGroup.add(titleImage, BorderLayout.LINE_START);
        parentGroup.add(titleLabel, BorderLayout.CENTER);
        parentGroup.add(menuButton, BorderLayout.LINE_END);
        mainMenu.add(settings);
        mainMenu.add(ComponentFactory.createMenuSeparator());
        mainMenu.add(website);
        mainMenu.add(about);
        mainMenu.add(ComponentFactory.createMenuSeparator());
        mainMenu.add(hide);
        mainMenu.add(exit);
    }

    private void listen(){
        menuButton.addActionListener(e -> mainMenu.show(menuButton, menuButton.getWidth() - mainMenu.getWidth(), menuButton.getHeight()));
    }

    public JPanel getParent(){
        return parentGroup;
    }
}
