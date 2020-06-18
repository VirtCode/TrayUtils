package ch.virt.winutils.gui.components;

import ch.virt.winutils.Dialogs;
import ch.virt.winutils.event.GuiEventBus;
import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class is the top bar visible on the gui
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
    private JMenuItem modules;
    private JMenuItem settings;

    private final MainEventBus mainEvents;
    private final GuiEventBus guiEvents;

    /**
     * Creates the top bar gui
     * @param mainEvents main eventbus
     * @param guiEvents gui eventbus
     */
    public TopBarGui(MainEventBus mainEvents, GuiEventBus guiEvents){
        this.mainEvents = mainEvents;
        this.guiEvents = guiEvents;
    }

    /**
     * initializes the gui
     */
    public void init(){
        create();
        assign();
        listen();
    }

    /**
     * Creates the components
     */
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
        modules = ComponentFactory.createMenuItem();
        modules.setText("Modules");
    }

    /**
     * Assigns the components
     */
    private void assign(){
        parentGroup.add(titleImage, BorderLayout.LINE_START);
        parentGroup.add(titleLabel, BorderLayout.CENTER);
        parentGroup.add(menuButton, BorderLayout.LINE_END);
        mainMenu.add(modules);
        mainMenu.add(settings);
        mainMenu.add(ComponentFactory.createMenuSeparator());
        mainMenu.add(website);
        mainMenu.add(about);
        mainMenu.add(ComponentFactory.createMenuSeparator());
        mainMenu.add(hide);
        mainMenu.add(exit);
    }

    /**
     * Applies listeners to the components
     */
    private void listen(){
        menuButton.addActionListener(e -> mainMenu.show(menuButton, menuButton.getWidth() - mainMenu.getWidth(), menuButton.getHeight()));

        exit.addActionListener(e -> mainEvents.quit());
        hide.addActionListener(e -> mainEvents.hideGui());
        about.addActionListener(e -> Dialogs.showAbout());
        website.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://google.ch"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }
        });
        settings.addActionListener(e -> guiEvents.openSettings());
        modules.addActionListener(e -> guiEvents.openModules());
    }

    /**
     * Returns the parent panel
     * @return parent
     */
    public JPanel getParent(){
        return parentGroup;
    }
}
