package ch.virt.winutils.gui.misc;

import ch.virt.winutils.gui.helper.ResourceHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * @author VirtCode
 * @version 1.0
 *
 * (I know that this method used in this class is very inefficient and is only used in hope for saving ram)
 */
public class ImageButtonHoverEffect implements MouseListener {

    private boolean mouseIn;

    private final Color idle, hover, pressed;
    private final JButton component;

    public ImageButtonHoverEffect(Color idle, Color hover, Color pressed, JButton component) {
        this.idle = idle;
        this.hover = hover;
        this.pressed = pressed;
        this.component = component;

        component.setIcon(new ImageIcon(ResourceHelper.colorizeImage(((ImageIcon)component.getIcon()).getImage(), idle)));
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        component.setIcon(new ImageIcon(ResourceHelper.colorizeImage(((ImageIcon)component.getIcon()).getImage(), pressed)));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(mouseIn) component.setIcon(new ImageIcon(ResourceHelper.colorizeImage(((ImageIcon)component.getIcon()).getImage(), hover)));
        else component.setIcon(new ImageIcon(ResourceHelper.colorizeImage(((ImageIcon)component.getIcon()).getImage(), idle)));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        component.setIcon(new ImageIcon(ResourceHelper.colorizeImage(((ImageIcon)component.getIcon()).getImage(), hover)));
        mouseIn = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        component.setIcon(new ImageIcon(ResourceHelper.colorizeImage(((ImageIcon)component.getIcon()).getImage(), idle)));
        mouseIn = false;
    }
}
