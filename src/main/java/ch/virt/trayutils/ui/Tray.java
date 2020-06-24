package ch.virt.trayutils.ui;

import ch.virt.trayutils.Dialogs;
import ch.virt.trayutils.event.MainEventBus;
import ch.virt.trayutils.gui.helper.manager.ResourceManager;
import ch.virt.trayutils.gui.helper.manager.StringManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Manages the icon in the system tray
 * @author VirtCode
 * @version 1.0
 */
public class Tray {

    private TrayIcon icon;
    private MainEventBus bus;

    /**
     * Creates the system tray
     * @param eventBus main event bus

     */
    public Tray(MainEventBus eventBus) {
        this.bus = eventBus;

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            this.icon = new TrayIcon(getImage(), StringManager.trayTitle, getMenu());
            this.icon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) bus.toggleGui();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            try {
                tray.add(icon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    private Image getImage() {
        try {
            return ImageIO.read(Tray.class.getResourceAsStream(ResourceManager.logoSmall));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * returns the popup menu of the tray
     * @return the created popup menu
     */
    private PopupMenu getMenu() {
        PopupMenu menu = new PopupMenu(StringManager.trayTitle);
        menu.add(new MenuItem(StringManager.trayTitle));

        menu.addSeparator();

        MenuItem showGui = new MenuItem(StringManager.showGui);
        showGui.addActionListener(e -> bus.toggleGui());
        menu.add(showGui);

        MenuItem about = new MenuItem(StringManager.about);
        about.addActionListener(e -> Dialogs.showAbout());
        menu.add(about);

        menu.addSeparator();

        MenuItem exit = new MenuItem(StringManager.exit);
        exit.addActionListener(e -> bus.quit());
        menu.add(exit);

        return menu;
    }

}
