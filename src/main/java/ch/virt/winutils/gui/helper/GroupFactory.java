package ch.virt.winutils.gui.helper;

import ch.virt.winutils.Utils;
import ch.virt.winutils.event.Listener;
import ch.virt.winutils.ui.KeyChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author VirtCode
 * @version 1.0
 */
public class GroupFactory {

    public static JPanel createSettingSubCategory(){
        JPanel category = ComponentFactory.createGroup();
        category.setBorder(new EmptyBorder(ComponentFactory.getSubInsets(1)));
        category.setLayout(new BoxLayout(category, BoxLayout.Y_AXIS));

        return category;
    }

    public static JPanel createChangeKeyBindModule(int[] currentBind, String bindTitle, Listener<Integer[]> bindChosen, boolean single){
        JButton button = ComponentFactory.createButton();
        button.setText("Change");

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
