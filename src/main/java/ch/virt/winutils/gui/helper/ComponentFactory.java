package ch.virt.winutils.gui.helper;

import ch.virt.winutils.Main;
import ch.virt.winutils.gui.misc.ImageButtonHoverEffect;
import ch.virt.winutils.gui.misc.TextButtonHoverEffect;
import ch.virt.winutils.ui.Tray;
import jdk.jshell.execution.JdiDefaultExecutionControl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
        } catch (Exception e) { // Yes i'm a complete moron
            e.printStackTrace();
        }
    }

    public static JButton createButton(){
        JButton button = new JButton();
        button.setForeground(ColorManager.buttonForeground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addMouseListener(new TextButtonHoverEffect(ColorManager.buttonIdle, ColorManager.buttonHover, ColorManager.buttonPressed, button));
        return button;
    }

    public static JPanel createGroup(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    public static JButton createImageButton(String path){
        JButton button = new JButton(new ImageIcon(ResourceHelper.loadImage(path)));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.addMouseListener(new ImageButtonHoverEffect(ColorManager.imageIdle, ColorManager.imageHover, ColorManager.imagePressed, button));
        button.setMargin(getImageInsets());
        return button;
    }

    public static JLabel createFlatImage(String path){
        JLabel label = new JLabel(new ImageIcon(ResourceHelper.colorizeImage(ResourceHelper.loadImage(path), ColorManager.imageIdle)));
        label.setOpaque(false);
        label.setBorder(new EmptyBorder(getImageInsets()));
        return label;
    }

    public static JLabel createLabel(){
        JLabel label = new JLabel();
        label.setForeground(ColorManager.buttonForeground);
        return label;
    }

    public static JDialog getMainFrame(){
        JDialog frame = new JDialog();
        frame.setUndecorated(true);
        frame.setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        panel.setBackground(ColorManager.frameBackground);

        frame.setContentPane(panel);
        return frame;
    }

    private static Insets getImageInsets() {
        return new Insets(4, 4, 4, 4);
    }
}
