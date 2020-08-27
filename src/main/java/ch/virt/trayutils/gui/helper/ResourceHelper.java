package ch.virt.trayutils.gui.helper;

import ch.virt.trayutils.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Used to load / change resources
 * @author VirtCode
 * @version 1.0
 */
public class ResourceHelper {

    /**
     * Colorizes an Image according to its alpha values
     * @param image image
     * @param color color to colorize with
     * @return image
     */
    public static Image colorizeImage(Image image, Color color){
        BufferedImage converted = toBufferedImage(image);
        BufferedImage result = new BufferedImage(converted.getWidth(), converted.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < converted.getHeight(); i++) {
            for (int j = 0; j < converted.getWidth(); j++) {
                Color pixel = new Color(color.getRed(), color.getGreen(), color.getBlue(), new Color(converted.getRGB(j, i), true).getAlpha());
                result.setRGB(j, i, pixel.getRGB());
            }
        }

        return result;
    }

    /**
     * Converts an image to a buffered image
     * @param image image to convert
     * @return buffered image
     */
    public static BufferedImage toBufferedImage(Image image){
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

    /**
     * Loads a image from the classpath
     * @param path path to load from
     * @return image
     */
    public static Image loadImage(String path) {
        System.out.println(path);
        try {
            return ImageIO.read(Main.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }
}
