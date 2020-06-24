package ch.virt.winutils.ui;

import ch.virt.winutils.Dialogs;
import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.modules.Module;

import javax.imageio.ImageIO;
import javax.swing.plaf.BorderUIResource;
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

            this.icon = new TrayIcon(getImage(), "WinUtils", getMenu());
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
                Dialogs.showError("Failed to create system tray icon. Going to close!");
                System.exit(0);
            }
        } else {
            Dialogs.showError("Sorry, but system tray is not supported by your system.");
            System.exit(0);
        }
    }

    private Image getImage() {
        try {
            return ImageIO.read(Tray.class.getResourceAsStream("/logo.png"));
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
        PopupMenu menu = new PopupMenu("WinUtils");
        menu.add(new MenuItem("WinUtils"));

        menu.addSeparator();

        MenuItem showGui = new MenuItem("Show Gui");
        showGui.addActionListener(e -> bus.toggleGui());
        menu.add(showGui);

        MenuItem about = new MenuItem("About");
        about.addActionListener(e -> Dialogs.showAbout());
        menu.add(about);

        menu.addSeparator();

        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(e -> bus.quit());
        menu.add(exit);

        return menu;
    }

}
