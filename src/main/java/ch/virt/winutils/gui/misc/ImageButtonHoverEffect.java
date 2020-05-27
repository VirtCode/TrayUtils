package ch.virt.winutils.gui.misc;

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

        component.setIcon(new ImageIcon(colorize(((ImageIcon)component.getIcon()).getImage(), idle)));
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        component.setIcon(new ImageIcon(colorize(((ImageIcon)component.getIcon()).getImage(), pressed)));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(mouseIn) component.setIcon(new ImageIcon(colorize(((ImageIcon)component.getIcon()).getImage(), hover)));
        else component.setIcon(new ImageIcon(colorize(((ImageIcon)component.getIcon()).getImage(), idle)));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        component.setIcon(new ImageIcon(colorize(((ImageIcon)component.getIcon()).getImage(), hover)));
        mouseIn = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        component.setIcon(new ImageIcon(colorize(((ImageIcon)component.getIcon()).getImage(), idle)));
        mouseIn = false;
    }

    private Image colorize(Image image, Color color){
        BufferedImage converted = toBuffered(image);
        BufferedImage result = new BufferedImage(converted.getWidth(), converted.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < converted.getHeight(); i++) {
            for (int j = 0; j < converted.getWidth(); j++) {
                Color pixel = new Color(color.getRed(), color.getGreen(), color.getBlue(), new Color(converted.getRGB(j, i), true).getAlpha());
                result.setRGB(j, i, pixel.getRGB());
            }
        }

        return result;
    }

    private BufferedImage toBuffered(Image image){
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}
