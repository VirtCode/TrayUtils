package ch.virt.winutils.modules;

import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.RoundRectangle2D;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ColorPickerModule extends Module {
    private boolean clicked;
    private boolean cancelled;
    private boolean alreadyRunning;

    @Override
    public int getID() {
        return 9846;
    }

    @Override
    public void keyStrokeCalled() {
        if(!alreadyRunning) showJFrame();
    }

    private void showJFrame(){
        Runnable frameRun = () -> {

            JDialog frame = createFrame();
            Color selected = null;
            clicked = false;
            cancelled = false;

            try {
                Robot robot = new Robot();
                while (true){
                    Color current = robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);

                    setFrameColor(frame, current);
                    if (cancelled) break;
                    if (clicked){
                        selected = current;
                        break;
                    }
                }
            } catch (AWTException e) {
                e.printStackTrace();
                alreadyRunning = false;
            }

            frame.dispose();

            if (selected != null) colorSelected(selected);
            alreadyRunning = false;
        };

        Thread thread = new Thread(frameRun);
        alreadyRunning = true;
        thread.start();
    }

    private void setFrameColor(JDialog frame, Color color){
        int luma = (int) (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());

        frame.getContentPane().setBackground(color);

        JLabel label = (JLabel) frame.getContentPane().getComponent(0);
        label.setText("#" + toHex(color));
        label.setForeground(luma > 100 ? Color.BLACK : Color.WHITE);
    }

    private String toHex(Color color){
        return Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
    }

    public void colorSelected(Color color){
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(toHex(color)), null);
    }

    public JDialog createFrame(){
        JDialog frame = new JDialog();
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 200, 100, 5, 5));
        frame.setSize(200, 100);
        Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(bounds.width - 10 - 200, bounds.height - 50 - 100);
        frame.setVisible(true);

        JLabel label = new JLabel("#", SwingConstants.CENTER);
        label.setSize(200, 100);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        frame.add(label);

        return frame;
    }

    @Override
    public MenuItem settingsMenu() {
        return null;
    }

    @Override
    public void fromSettings(String s) {

    }

    @Override
    public String toSettings() {
        return null;
    }

    @Override
    public void create() {
        inputBus.addMousePressedListener(e -> {
            clicked = true;
        });

        inputBus.addKeyReleasedListener(e -> {
            if (e == NativeKeyEvent.VC_ESCAPE) cancelled = true;
        });
    }
}
