package ch.virt.winutils.gui;

import ch.virt.winutils.gui.helper.ComponentFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Gui {

    private JDialog frame;

    private JPanel topRegion;
    private JPanel sideRegion;
    private JPanel mainRegion;

    private JButton menuButton;
    private JLabel titleLabel;
    private JLabel titleImage;

    public Gui(){
        setupFrame();
        createRegions();
        assignRegions();
        createComponents();
        assignComponents();
        finishFrame();
    }

    public void setupFrame() {
        frame = ComponentFactory.getMainFrame();
        adjustFrame(frame);
        frame.setLayout(new BorderLayout());
    }

    public void finishFrame(){
        frame.setVisible(true);
    }

    public void createRegions(){
        topRegion = ComponentFactory.createGroup();
        topRegion.setLayout(new BorderLayout());

        sideRegion = ComponentFactory.createGroup();
        sideRegion.setLayout(new BoxLayout(sideRegion, BoxLayout.Y_AXIS));

        mainRegion = ComponentFactory.createGroup();
    }

    public void assignRegions(){
        frame.add(topRegion, BorderLayout.PAGE_START);
        frame.add(sideRegion, BorderLayout.LINE_START);
        frame.add(mainRegion, BorderLayout.CENTER);
    }

    public void createComponents(){
        createTopBar();
    }

    public void assignComponents(){
        assignTopBar();
    }

    public void createTopBar(){
        titleImage = ComponentFactory.createFlatImage("/icon_medium.png");
        titleLabel = ComponentFactory.createLabel();
        titleLabel.setText("WinUtils Settings");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuButton = ComponentFactory.createImageButton("/menu_burger.png");
    }
    public void assignTopBar(){
        topRegion.add(titleImage, BorderLayout.LINE_START);
        topRegion.add(titleLabel, BorderLayout.CENTER);
        topRegion.add(menuButton, BorderLayout.LINE_END);
    }

    public void adjustFrame(JDialog frame){
        Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
        int width = bounds.width / 6;
        int height = bounds.height / 2;
        int x = bounds.width - width - 10;
        int y = bounds.height - height;

        frame.setSize(width, height);
        frame.setLocation(x, y);
    }
}
