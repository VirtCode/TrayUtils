package ch.virt.winutils;

import javax.swing.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Dialogs {

    public static void initialize(){
        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showError(String message){
        JOptionPane.showMessageDialog(null, message, "WinUtils Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showAbout(){
        JOptionPane.showMessageDialog(null, "WinUtils Version 0.1\n" +
                "(C) 2020 - VirtCode", "About WinUtils", JOptionPane.INFORMATION_MESSAGE);
    }
}
