package ch.virt.winutils;

import ch.virt.winutils.event.EventBus;
import ch.virt.winutils.settings.KeyChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Tray {

    private TrayIcon icon;
    private EventBus bus;

    public Tray(EventBus eventBus, MenuItem[] moduleSettings, int[] baseBind) {
        this.bus = eventBus;

        if (SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();

            this.icon = new TrayIcon(getImage(), "WinUtils", getMenu(moduleSettings, baseBind));
            try {
                tray.add(icon);
            } catch (AWTException e) {
                e.printStackTrace();
                Dialogs.showError("Failed to create system tray icon. Going to close!");
                System.exit(0);
            }
        }else {
            Dialogs.showError("Sorry, but system tray is not supported by your system.");
            System.exit(0);
        }
    }

    public void refreshPopupMenu(MenuItem[] moduleSettings, int[] baseBinds){
        this.icon.setPopupMenu(getMenu(moduleSettings, baseBinds));
    }

    private Image getImage(){
        try {
            return ImageIO.read(Tray.class.getResourceAsStream("/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }

    private PopupMenu getMenu(MenuItem[] moduleSettings, int[] baseBinds){
        PopupMenu menu = new PopupMenu("WinUtils");
        menu.add(new MenuItem("WinUtils"));
        Integer[] integers = new Integer[baseBinds.length];
        for (int i = 0; i < integers.length; i++) integers[i] = baseBinds[i];
        menu.add(KeyChooser.prettifyKeyArray(integers) + " + [Module]");
        menu.addSeparator();

        for (MenuItem moduleSetting : moduleSettings) {
            menu.add(moduleSetting);
        }

        menu.addSeparator();

        MenuItem changeBaseBind = new MenuItem("Change base Keys");
        changeBaseBind.addActionListener(e -> bus.chooseBaseBind());
        menu.add(changeBaseBind);

        MenuItem about = new MenuItem("About");
        about.addActionListener(e -> Dialogs.showAbout());
        menu.add(about);

        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(e -> bus.quit());
        menu.add(exit);

        return menu;
    }
}
