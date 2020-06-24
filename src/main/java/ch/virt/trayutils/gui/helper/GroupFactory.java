package ch.virt.trayutils.gui.helper;

import ch.virt.trayutils.Utils;
import ch.virt.trayutils.event.Listener;
import ch.virt.trayutils.gui.helper.manager.StringManager;
import ch.virt.trayutils.ui.KeyChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Used to create mini modules used in the ui
 * @author VirtCode
 * @version 1.0
 */
public class GroupFactory {

    /**
     * Creates a sub category inset panel for settings
     * @return panel
     */
    public static JPanel createSettingSubCategory(){
        JPanel category = ComponentFactory.createGroup();
        category.setBorder(new EmptyBorder(ComponentFactory.getSubInsets(1)));
        category.setLayout(new BoxLayout(category, BoxLayout.Y_AXIS));

        return category;
    }

    /**
     * Creates a panel with stuff on to change a keybind
     * @param currentBind currently chosen keybind
     * @param bindTitle guiTitle of the bind
     * @param bindChosen listener if the bind is chosen
     * @param single whether only one key is chosen
     * @return panel
     */
    public static JPanel createChangeKeyBindModule(int[] currentBind, String bindTitle, Listener<Integer[]> bindChosen, boolean single){
        JButton button = ComponentFactory.createButton();
        button.setText(StringManager.change);

        JLabel title = ComponentFactory.createLabel();
        title.setText(bindTitle + ":");

        JLabel bindDisplay = ComponentFactory.createLabel();
        bindDisplay.setText(KeyChooser.prettifyKeyArray(Utils.fromPrimitive(currentBind)));

        button.addActionListener(e -> {
            if(!single) KeyChooser.choose(f -> {
                bindDisplay.setText(KeyChooser.prettifyKeyArray(f));
                bindChosen.called(f);
            });
            else KeyChooser.chooseOne(d -> {
                bindDisplay.setText(KeyChooser.prettifyKeyArray(new Integer[]{d}));
                bindChosen.called(new Integer[]{d});
            });
        });

        JPanel main = ComponentFactory.createGroup();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel buttonPanel = ComponentFactory.createGroup();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(4, 8, 4, 4));

        bindDisplay.setBorder(new EmptyBorder(0, 0, 4, 0));

        main.add(title);
        main.add(buttonPanel);
        buttonPanel.add(bindDisplay);
        buttonPanel.add(button);
        return main;
    }

}
