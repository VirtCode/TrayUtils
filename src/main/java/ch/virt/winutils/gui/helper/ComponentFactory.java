package ch.virt.winutils.gui.helper;

import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.misc.ColorHoverEffect;

import javax.swing.*;
import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ComponentFactory {

    public static void initialize(){
        setLookAndFeel();
    }

    public static void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { // Yes i'm a complete utter moron
            e.printStackTrace();
        }
    }

    public static JButton createButton(){
        JButton button = new JButton();
        button.setForeground(ColorManager.buttonForeground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addMouseListener(new ColorHoverEffect(ColorManager.buttonIdle, ColorManager.buttonHover, ColorManager.buttonPressed, button));
        return button;
    }

    public static JFrame getMainFrame(){
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBackground(new Color(0x0000000, true));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x94383838, true));


        frame.setContentPane(panel);
        return frame;
    }

}
