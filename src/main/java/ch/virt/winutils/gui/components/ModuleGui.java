package ch.virt.winutils.gui.components;

import ch.virt.winutils.event.MainEventBus;
import ch.virt.winutils.gui.components.module.ModuleSettingsDisplay;
import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.modules.Module;
import ch.virt.winutils.modules.ModuleLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * This class is the module gui part
 * @author VirtCode
 * @version 1.0
 */
public class ModuleGui {

    private JPanel parentGroup;
    private JPanel sideGroup;
    private JPanel mainGroup;

    private HashMap<Integer, ModuleSettingsDisplay> moduleMap;
    private final ModuleLoader modules;
    private final MainEventBus bus;

    /**
     * Creates a ModuleGui
     * @param modules module loader with the modules
     * @param bus main event bus
     */
    public ModuleGui(ModuleLoader modules, MainEventBus bus) {
        this.modules = modules;
        this.bus = bus;
    }

    /**
     * Initialises the gui
     */
    public void init(){
        create();
        assign();
        listen();

        buildModules();
    }

    /**
     * Builds all the displays
     * @see ModuleSettingsDisplay
     */
    private void buildModules(){
        moduleMap = new HashMap<>();

        for (Module module : modules.getModules()) {
            addModule(new ModuleSettingsDisplay(module, bus, this::setModule));
        }
    }

    /**
     * Adds a ModuleSettingsDisplay to the gui
     * @param setting display to add
     */
    private void addModule(ModuleSettingsDisplay setting){
        moduleMap.put(setting.getId(), setting);
        sideGroup.add(setting.getTriggerButton());
    }

    /**
     * Sets the module that should be shown
     * @param id id of that module
     */
    private void setModule(Integer id){
        mainGroup.removeAll();
        mainGroup.add(moduleMap.get(id).getGui(), BorderLayout.CENTER);
        mainGroup.revalidate();
        mainGroup.repaint();
    }

    /**
     * Creates the components
     */
    private void create(){
        parentGroup = ComponentFactory.createGroup();
        parentGroup.setLayout(new BorderLayout());

        sideGroup = ComponentFactory.createPanel(ColorManager.sideBackground);
        sideGroup.setLayout(new BoxLayout(sideGroup, BoxLayout.Y_AXIS));

        mainGroup = ComponentFactory.createPanel(ColorManager.mainBackground);
        mainGroup.setLayout(new BorderLayout());
    }

    /**
     * Assigns the components
     */
    private void assign(){
        parentGroup.add(sideGroup, BorderLayout.LINE_START);
        parentGroup.add(mainGroup, BorderLayout.CENTER);
    }

    /**
     * Assigns listeners to the components
     */
    private void listen(){

    }

    /**
     * Returns the parent panel of the group
     * @return panel
     */
    public JPanel getParent(){
        return parentGroup;
    }
}
