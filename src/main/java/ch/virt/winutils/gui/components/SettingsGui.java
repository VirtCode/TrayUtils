package ch.virt.winutils.gui.components;

import ch.virt.winutils.Utils;
import ch.virt.winutils.event.Listener;
import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.gui.helper.GroupFactory;
import ch.virt.winutils.ui.KeyChooser;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class SettingsGui {

    private JPanel parentGroup;

    private JLabel titleLabel;
    private JPanel settings;

    private JLabel generalSubTitle;
    private JPanel generalSettings;

    private JPanel changeBaseKeyBind;
    private JPanel changeGuiKeyBind;

    private JLabel advancedSubTitle;
    private JPanel advancedSettings;

    private JCheckBox startWithOS;


    public void init(){
        create();
        assign();
        listen();
    }

    private void create(){
        parentGroup = ComponentFactory.createPanel(ColorManager.mainBackground);
        parentGroup.setLayout(new BoxLayout(parentGroup, BoxLayout.Y_AXIS));
        parentGroup.setBorder(new EmptyBorder(8, 16, 8,16));

        titleLabel = ComponentFactory.createHeader();
        titleLabel.setText("Settings");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        settings = GroupFactory.createSettingSubCategory();
        generalSettings = GroupFactory.createSettingSubCategory();
        advancedSettings = GroupFactory.createSettingSubCategory();

        generalSubTitle = ComponentFactory.createSubHeader();
        generalSubTitle.setText("General");

        changeBaseKeyBind = GroupFactory.createChangeKeyBindModule(new int[]{44, 45}, "Base Keybind", arg -> {}, false);

        changeGuiKeyBind = GroupFactory.createChangeKeyBindModule(new int[]{44}, "Gui Keybind", arg -> {}, true);

        advancedSubTitle = ComponentFactory.createSubHeader();
        advancedSubTitle.setText("Advanced");

        startWithOS = ComponentFactory.createCheckBox();
        startWithOS.setText("Start with OS");

    }

    private void assign(){
        parentGroup.add(titleLabel);
        parentGroup.add(settings);

        settings.add(generalSubTitle);
        settings.add(generalSettings);

        generalSettings.add(changeBaseKeyBind);
        generalSettings.add(changeGuiKeyBind);

        settings.add(advancedSubTitle);
        settings.add(advancedSettings);

        advancedSettings.add(startWithOS);
    }

    private void listen(){

    }

    public JPanel getParent(){
        return parentGroup;
    }
}
