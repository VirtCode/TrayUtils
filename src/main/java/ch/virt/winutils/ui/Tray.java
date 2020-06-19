package ch.virt.winutils.ui;

import ch.virt.winutils.Dialogs;
import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.modules.Module;

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
     * @param moduleSettings **REMOVE**
     * @param baseBind **REMOVE**
     */
    public Tray(MainEventBus eventBus, Module[] moduleSettings, int[] baseBind) {
        this.bus = eventBus;

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            this.icon = new TrayIcon(getImage(), "WinUtils", getMenu(moduleSettings, baseBind));
            this.icon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) bus.showGui();
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

    /**
     * Refreshes the popup menu of the tray
     * @param moduleSettings **REMOVE**
     * @param baseBinds **REMOVE**
     */
    public void refreshPopupMenu(Module[] moduleSettings, int[] baseBinds) {
        this.icon.setPopupMenu(getMenu(moduleSettings, baseBinds));
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
     * @param moduleSettings **REMOVE**
     * @param baseBinds **REMOVE**
     * @return the created popup menu
     */
    private PopupMenu getMenu(Module[] moduleSettings, int[] baseBinds) {
        PopupMenu menu = new PopupMenu("WinUtils");
        menu.add(new MenuItem("WinUtils"));
        Integer[] integers = new Integer[baseBinds.length];
        for (int i = 0; i < integers.length; i++) integers[i] = baseBinds[i];
        menu.add(KeyChooser.prettifyKeyArray(integers) + " + [Module]");
        menu.addSeparator();

        MenuItem about = new MenuItem("About");
        about.addActionListener(e -> Dialogs.showAbout());
        menu.add(about);

        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(e -> bus.quit());
        menu.add(exit);

        return menu;
    }

}
