package ch.virt.winutils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Tray {

    private TrayIcon icon;

    public Tray(ActionListener exit, MenuItem[] moduleSettings) {
        if (SystemTray.isSupported()){

            SystemTray tray = SystemTray.getSystemTray();
            this.icon = new TrayIcon(getImage(), "WinUtils", getMenu(exit, moduleSettings));
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

    private Image getImage(){
        try {
            return ImageIO.read(Tray.class.getResourceAsStream("/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }

    private PopupMenu getMenu(ActionListener exitListener, MenuItem[] moduleSettings){
        PopupMenu menu = new PopupMenu("WinUtils");
        menu.add(new MenuItem("WinUtils"));
        menu.addSeparator();

        for (MenuItem moduleSetting : moduleSettings) {
            menu.add(moduleSetting);
        }

        menu.addSeparator();

        MenuItem about = new MenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "WinUtils Version 0.1 \n(C) 2020 - VirtCode\n This program is completely free to use, feel free to share this program among others. \n\n (I know this dialog is kinda plebig)", "About WinUtils", JOptionPane.INFORMATION_MESSAGE));
        menu.add(about);

        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(exitListener);
        menu.add(exit);

        return menu;
    }
}
