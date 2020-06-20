package ch.virt.winutils.gui.components;

import ch.virt.winutils.Utils;
import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.gui.helper.GroupFactory;
import ch.virt.winutils.settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * This class is the settings gui part of the ui
 * @author VirtCode
 * @version 1.0
 */
public class SettingsGui {

    private JPanel parentGroup;

    private JLabel titleLabel;
    private JPanel settingsPanel;

    private JLabel generalSubTitle;
    private JPanel generalSettings;

    private JPanel changeBaseKeyBind;
    private JPanel changeGuiKeyBind;

    private JLabel advancedSubTitle;
    private JPanel advancedSettings;

    private JCheckBox startWithOS;

    private final Settings settings;

    /**
     * Creates the settings gui
     * @param settings settings to manipulate
     */
    public SettingsGui(Settings settings) {
        this.settings = settings;
    }

    /**
     * Initializes the gui
     */
    public void init(){
        create();
        assign();
        listen();
    }

    /**
     * Creates the components
     */
    private void create(){
        parentGroup = ComponentFactory.createPanel(ColorManager.mainBackground);
        parentGroup.setLayout(new BoxLayout(parentGroup, BoxLayout.Y_AXIS));
        parentGroup.setBorder(new EmptyBorder(8, 16, 8,16));

        titleLabel = ComponentFactory.createLabelHeader();
        titleLabel.setText("Settings");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        settingsPanel = GroupFactory.createSettingSubCategory();
        generalSettings = GroupFactory.createSettingSubCategory();
        advancedSettings = GroupFactory.createSettingSubCategory();

        generalSubTitle = ComponentFactory.createLabelSubHeader();
        generalSubTitle.setText("General");

        changeBaseKeyBind = GroupFactory.createChangeKeyBindModule(settings.getBaseKeyCodes(), "Base Keybind", arg -> {settings.setBaseKeyCodes(Utils.toPrimitive(arg)); settings.save();}, false);

        changeGuiKeyBind = GroupFactory.createChangeKeyBindModule(new int[]{settings.getGuiKeyCode()}, "Gui Keybind", arg -> {settings.setGuiKeyCode(arg[0]); settings.save();}, true);

        advancedSubTitle = ComponentFactory.createLabelSubHeader();
        advancedSubTitle.setText("Advanced");

        startWithOS = ComponentFactory.createCheckBox();
        startWithOS.setText("Start with OS");

    }

    /**
     * Assigns the components
     */
    private void assign(){
        parentGroup.add(titleLabel);
        parentGroup.add(settingsPanel);

        settingsPanel.add(generalSubTitle);
        settingsPanel.add(generalSettings);

        generalSettings.add(changeBaseKeyBind);
        generalSettings.add(changeGuiKeyBind);

        settingsPanel.add(advancedSubTitle);
        settingsPanel.add(advancedSettings);

        advancedSettings.add(startWithOS);
    }

    /**
     * Assigns listeners to the components
     */
    private void listen(){
    }

    /**
     * Returns the parent panel
     * @return panel
     */
    public JPanel getParent(){
        return parentGroup;
    }
}
