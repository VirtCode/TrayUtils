package ch.virt.trayutils.ui;

import ch.virt.trayutils.Dialogs;
import ch.virt.trayutils.event.MainEventBus;
import ch.virt.trayutils.gui.helper.ResourceHelper;
import ch.virt.trayutils.gui.helper.manager.ResourceManager;
import ch.virt.trayutils.gui.helper.manager.StringManager;

import javax.imageio.ImageIO;
import javax.security.auth.login.FailedLoginException;
import java.awt.*;
import java.awt.event.MouseAdapter;
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

    private static final String TAG = "[Tray] ";

    private TrayIcon icon;
    private MainEventBus bus;

    /**
     * Creates the system tray
     * @param eventBus main event bus

     */
    public Tray(MainEventBus eventBus) {
        this.bus = eventBus;

        if (SystemTray.isSupported()) {
            System.out.println(TAG + "Going to initialize tray");
            SystemTray tray = SystemTray.getSystemTray();

            this.icon = new TrayIcon(ResourceHelper.colorizeImage(ResourceHelper.loadImage(ResourceManager.logoSmall), Color.WHITE), StringManager.trayTitle, getMenu());
            this.icon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) bus.toggleGui();
                }
            });

            try {
                tray.add(icon);
            } catch (AWTException e) {
                Dialogs.showErrorDialog("Failed to add app to system tray. (If you want to close it, open gui or kill it.");
            }
            System.out.println(TAG + "Tray successfully initialized");
        }else Dialogs.showErrorDialog("Your system does not support the system tray. You can still use it by opening the gui with the gui keybind.");
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
        about.addActionListener(e -> Dialogs.showAboutDialog());
        menu.add(about);

        menu.addSeparator();

        MenuItem exit = new MenuItem(StringManager.exit);
        exit.addActionListener(e -> bus.quit());
        menu.add(exit);

        return menu;
    }

    public void showNotification(String title, String content){
        icon.displayMessage(title, content, TrayIcon.MessageType.NONE);
    }

}
