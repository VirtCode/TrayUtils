package ch.virt.trayutils;

import javax.swing.*;

/**
 * This class is used to show plebig dialogs
 * @author VirtCode
 * @version 1.0
 */
public class Dialogs {

    /**
     * Sets the look and feel of all the stuff
     */
    public static void initialize(){
        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows an error dialog
     * @param message error message
     */
    public static void showError(String message){
        JOptionPane.showMessageDialog(null, message, "WinUtils Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method shows an about dialog
     */
    public static void showAbout(){
        JOptionPane.showMessageDialog(null, "WinUtils Version 0.1\n" +
                "(C) 2020 - VirtCode", "About WinUtils", JOptionPane.INFORMATION_MESSAGE);
    }
}
