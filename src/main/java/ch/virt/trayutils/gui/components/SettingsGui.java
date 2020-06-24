package ch.virt.trayutils.gui.components;

import ch.virt.trayutils.Utils;
import ch.virt.trayutils.gui.helper.manager.ColorManager;
import ch.virt.trayutils.gui.helper.ComponentFactory;
import ch.virt.trayutils.gui.helper.GroupFactory;
import ch.virt.trayutils.gui.helper.manager.StringManager;
import ch.virt.trayutils.settings.Settings;

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

    private JCheckBox consumeKeys;

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
        titleLabel.setText(StringManager.settings);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        settingsPanel = GroupFactory.createSettingSubCategory();
        generalSettings = GroupFactory.createSettingSubCategory();
        advancedSettings = GroupFactory.createSettingSubCategory();

        generalSubTitle = ComponentFactory.createLabelSubHeader();
        generalSubTitle.setText(StringManager.general);

        changeBaseKeyBind = GroupFactory.createChangeKeyBindModule(settings.getBaseKeyCodes(), StringManager.baseBind, arg -> {settings.setBaseKeyCodes(Utils.toPrimitive(arg)); settings.save();}, false);

        changeGuiKeyBind = GroupFactory.createChangeKeyBindModule(new int[]{settings.getGuiKeyCode()}, StringManager.guiBind, arg -> {settings.setGuiKeyCode(arg[0]); settings.save();}, true);

        advancedSubTitle = ComponentFactory.createLabelSubHeader();
        advancedSubTitle.setText(StringManager.advanced);

        startWithOS = ComponentFactory.createCheckBox();
        startWithOS.setText(StringManager.startWithOS);
        startWithOS.setSelected(settings.isStartWithSystem());

        consumeKeys = ComponentFactory.createCheckBox();
        consumeKeys.setText(StringManager.consumeEvents);
        consumeKeys.setSelected(settings.isConsumeKeys());
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
        advancedSettings.add(consumeKeys);
    }

    /**
     * Assigns listeners to the components
     */
    private void listen(){
        consumeKeys.addChangeListener(e -> {
            settings.setConsumeKeys(consumeKeys.isSelected());
            settings.save();
        });

        startWithOS.addChangeListener(e -> {
            settings.setStartWithSystem(startWithOS.isSelected());
            settings.save();
        });
    }

    /**
     * Returns the parent panel
     * @return panel
     */
    public JPanel getParent(){
        return parentGroup;
    }
}
