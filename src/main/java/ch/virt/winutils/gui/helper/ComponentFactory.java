package ch.virt.winutils.gui.helper;

import ch.virt.winutils.gui.misc.ImageButtonHoverEffect;
import ch.virt.winutils.gui.misc.TextButtonHoverEffect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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

    public static JPanel createPanel(Color color){
        JPanel panel = new JPanel();
        panel.setBackground(color);
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
        panel.setBackground(ColorManager.mainBackground);

        frame.setContentPane(panel);
        return frame;
    }

    public static JPopupMenu createPopupMenu(){
        JPopupMenu menu = new JPopupMenu();
        menu.setBorder(new LineBorder(ColorManager.menuSeparator, 1));
        menu.setBackground(ColorManager.menuBackgroundIdle);
        menu.setForeground(ColorManager.menuForeground);
        return menu;
    }

    public static JSeparator createMenuSeparator(){
        JSeparator separator = new JSeparator();
        separator.setBackground(ColorManager.menuSeparator);
        separator.setForeground(ColorManager.menuSeparator);
        return separator;
    }

    public static JMenuItem createMenuItem(){
        JMenuItem item = new JMenuItem();
        item.setBackground(ColorManager.menuBackgroundIdle);
        item.setForeground(ColorManager.menuForeground);
        item.setBorderPainted(false);
        item.addMouseListener(new TextButtonHoverEffect(ColorManager.menuBackgroundIdle, ColorManager.menuBackgroundHover, ColorManager.menuBackgroundPressed, item));
        return item;
    }

    public static JCheckBox createCheckBox(){
        JCheckBox checkBox = new JCheckBox();
        checkBox.setOpaque(false);
        checkBox.setForeground(ColorManager.checkBoxText);
        checkBox.setBorder(new EmptyBorder(8,0,8,8));
        checkBox.setBorderPainted(false);
        return checkBox;
    }

    public static JTextField createTextField(){
        JTextField textField = new JTextField();
        textField.setBackground(ColorManager.textFieldIdle);
        textField.setCaretColor(ColorManager.textFieldCursor);
        textField.setSelectionColor(ColorManager.textFieldSelection);
        textField.setForeground(ColorManager.textFieldText);
        textField.setBorder(new EmptyBorder(8,8,8,8));
        textField.setMaximumSize(new Dimension(1000, textField.getMinimumSize().height));
        return textField;
    }

    public static JLabel createHeader(){
        JLabel label = createLabel();
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 24));
        return label;
    }

    public static JLabel createSubHeader(){
        JLabel label = createLabel();
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 18));
        return label;
    }

    public static Insets getSubInsets(int index){
        return new Insets(4, 16 * index + 4, 4, 4);
    }

    private static Insets getImageInsets() {
        return new Insets(4, 4, 4, 4);
    }
}
