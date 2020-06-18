package ch.virt.winutils.gui.components;

import ch.virt.winutils.event.Listener;
import ch.virt.winutils.gui.components.module.ModuleSetting;
import ch.virt.winutils.gui.helper.ColorManager;
import ch.virt.winutils.gui.helper.ComponentFactory;
import ch.virt.winutils.modules.Module;
import ch.virt.winutils.modules.ModuleLoader;

import javax.swing.*;
import java.awt.*;
import java.lang.module.FindException;
import java.util.HashMap;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ModuleGui {

    private JPanel parentGroup;
    private JPanel sideGroup;
    private JPanel mainGroup;

    private HashMap<Integer, ModuleSetting> moduleMap;
    private final ModuleLoader modules;

    public ModuleGui(ModuleLoader modules) {
        this.modules = modules;
    }

    public ModuleLoader getModules() {
        return modules;
    }

    public void init(){
        create();
        assign();
        listen();

        buildModules();
    }

    private void buildModules(){
        moduleMap = new HashMap<>();

        for (Module module : modules.getModules()) {
            int id = module.getId();
            String title = module.getName();
            String icon = module.getIconPath();
            JPanel panel = module.settingsMenu();

            addModule(new ModuleSetting(id, title, icon, panel, this::setModule));
        }
    }

    private void addModule(ModuleSetting setting){
        moduleMap.put(setting.getId(), setting);
        sideGroup.add(setting.getTriggerButton());
    }

    private void setModule(Integer id){
        mainGroup.removeAll();
        mainGroup.add(moduleMap.get(id).getGui(), BorderLayout.CENTER);
        mainGroup.revalidate();
        mainGroup.repaint();
    }

    private void create(){
        parentGroup = ComponentFactory.createGroup();
        parentGroup.setLayout(new BorderLayout());

        sideGroup = ComponentFactory.createPanel(ColorManager.sideBackground);
        sideGroup.setLayout(new BoxLayout(sideGroup, BoxLayout.Y_AXIS));

        mainGroup = ComponentFactory.createPanel(ColorManager.mainBackground);
        mainGroup.setLayout(new BorderLayout());
    }

    private void assign(){
        parentGroup.add(sideGroup, BorderLayout.LINE_START);
        parentGroup.add(mainGroup, BorderLayout.CENTER);
    }

    private void listen(){

    }

    public JPanel getParent(){
        return parentGroup;
    }
}
