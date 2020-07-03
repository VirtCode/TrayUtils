package ch.virt.trayutils;

import ch.virt.trayutils.gui.helper.ComponentFactory;
import ch.virt.trayutils.gui.helper.manager.ColorManager;
import ch.virt.trayutils.gui.helper.manager.ResourceManager;
import ch.virt.trayutils.gui.helper.manager.StringManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URI;

/**
 * This class is used to show dialogs
 * @author VirtCode
 * @version 1.0
 */
public class Dialogs {
    
    private static final String TAG = "[Dialogs] "; 

    /**
     * This method shows an error dialog
     * @param message error message
     */
    public static void showErrorDialog(String message){
        System.out.println(TAG + "Showing error dialog with message: " + message);
        JDialog dialog = new JDialog();
        JPanel parent = ComponentFactory.createPanel(ColorManager.mainBackground);
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        dialog.setContentPane(parent);
        dialog.setTitle(StringManager.errorTitle);

        JLabel label = ComponentFactory.createLabelSubHeader();
        label.setText(StringManager.errorIntro);
        label.setBorder(new EmptyBorder(10, 0, 0, 40));
        parent.add(label);

        JLabel text = ComponentFactory.createLabel();
        text.setText(message);
        text.setBorder(new EmptyBorder(10, 0, 10, 40));
        parent.add(text);


        JPanel panel = ComponentFactory.createGroup();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton button = ComponentFactory.createButton();
        button.setText(StringManager.dialogClose);
        button.addActionListener(e -> dialog.dispose());

        panel.add(button);
        parent.add(panel);

        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /**
     * This method shows an about dialog
     */
    public static void showAboutDialog(){
        System.out.println(TAG + "Showing about Dialog");
        JDialog dialog = new JDialog();
        JPanel parent = ComponentFactory.createPanel(ColorManager.mainBackground);
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        dialog.setContentPane(parent);
        dialog.setTitle(StringManager.aboutTitle);

        JPanel upperPanel = ComponentFactory.createGroup();
        upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        upperPanel.add(ComponentFactory.createFlatImage(ResourceManager.logoMedium));

        JLabel label = ComponentFactory.createLabel();
        label.setText(StringManager.aboutText);
        label.setBorder(new EmptyBorder(0, 0, 0, 10));
        upperPanel.add(label);

        parent.add(upperPanel);

        JPanel panel = ComponentFactory.createGroup();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton button = ComponentFactory.createButton();
        button.setText(StringManager.dialogClose);
        button.addActionListener(e -> dialog.dispose());

        JButton website = ComponentFactory.createButton();
        website.setText(StringManager.website);
        website.addActionListener(e -> { try {
            Desktop.getDesktop().browse(new URI(ResourceManager.website));
        } catch (Exception ex) {
            showErrorDialog("Failed to Open Website!");
        }});

        panel.add(website);
        panel.add(button);
        parent.add(panel);

        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
