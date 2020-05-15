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
        if (SystemTray.isSupported()){
            this.bus = eventBus;
            SystemTray tray = SystemTray.getSystemTray();
            this.icon = new TrayIcon(getImage(), "WinUtils", getMenu(moduleSettings, baseBind));
            try {
                tray.add(icon);
            } catch (AWTException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to create system tray icon. (I know this dialog is kinda plebig)", "WinUtils Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Sorry, but system tray is not supported by your system. (I know this dialog is kinda plebig)", "WinUtils Error", JOptionPane.ERROR_MESSAGE);
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
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "WinUtils Version 0.1 \n(C) 2020 - VirtCode\n This program is completely free to use, feel free to share this program among others. \n\n (I know this dialog is kinda plebig)", "About WinUtils", JOptionPane.INFORMATION_MESSAGE));
        menu.add(about);

        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(e -> bus.quit());
        menu.add(exit);

        return menu;
    }
}
