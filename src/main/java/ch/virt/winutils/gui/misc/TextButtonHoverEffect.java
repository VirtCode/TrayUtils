package ch.virt.winutils.gui.misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Hover effect for text buttons
 * @author VirtCode
 * @version 1.0
 */
public class TextButtonHoverEffect implements MouseListener {

    private boolean mouseIn;

    private final Color idle, hover, pressed;
    private final JComponent component;

    /**
     * Creates a text button hover effect
     * @param idle idle background color
     * @param hover hover background color
     * @param pressed pressed background color
     * @param component target to use the effect on
     */
    public TextButtonHoverEffect(Color idle, Color hover, Color pressed, JComponent component) {
        this.idle = idle;
        this.hover = hover;
        this.pressed = pressed;
        this.component = component;

        component.setBackground(idle);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        component.setBackground(pressed);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(mouseIn) component.setBackground(hover);
        else component.setBackground(idle);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        component.setBackground(hover);
        mouseIn = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        component.setBackground(idle);
        mouseIn = false;
    }
}
