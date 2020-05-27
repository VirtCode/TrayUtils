package ch.virt.winutils.gui.helper;

import ch.virt.winutils.gui.misc.ImageButtonHoverEffect;
import ch.virt.winutils.gui.misc.TextButtonHoverEffect;
import ch.virt.winutils.ui.Tray;

import javax.imageio.ImageIO;
import javax.swing.*;
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

    public static JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    public static JButton createImageButton(String path){
        JButton button = new JButton(new ImageIcon(getImage(path)));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(new Color(0x0000000, true));
        button.setBackground(new Color(0x0000000, true));
        button.addMouseListener(new ImageButtonHoverEffect(ColorManager.imageIdle, ColorManager.imageHover, ColorManager.imagePressed, button));
        return button;
    }

    public static JLabel createLabel(){
        JLabel label = new JLabel();
        label.setForeground(ColorManager.buttonForeground);
        return label;
    }

    public static JFrame getMainFrame(){
        JFrame frame = new JFrame(); //Do correct repainting
        frame.setUndecorated(true);
        frame.setBackground(new Color(0x0000000, true));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x94383838, true));

        frame.setContentPane(panel);
        return frame;
    }

    public static Image getImage(String path) {
        try {
            return ImageIO.read(Tray.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }

}
