package ch.virt.winutils.modules;

import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ColorPickerModule extends Module {
    private boolean showInstructions = true;

    private boolean clicked;
    private boolean cancelled;
    private boolean alreadyRunning;

    public ColorPickerModule() {
        super(9846, "Colorpicker");
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

        JLabel label = (JLabel) frame.getContentPane().getComponent(showInstructions ? 1 : 0);
        label.setText("#" + toHex(color));
        label.setForeground(luma > 100 ? Color.BLACK : Color.WHITE);

        if (showInstructions) {
            JLabel instructions = (JLabel) frame.getContentPane().getComponent(0);
            instructions.setForeground(luma > 100 ? new Color(0x5E5E5E) : new Color(0x979797));
        }

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

        if (showInstructions) {
            JLabel instructions = new JLabel("Click to Select, Esc to Cancel.", SwingConstants.CENTER);
            instructions.setFont(new Font("Calibri", Font.PLAIN, 12));
            instructions.setSize(200, 20);
            instructions.setLocation(0, 80);
            frame.add(instructions);
        }

        JLabel label = new JLabel("#", SwingConstants.CENTER);
        label.setSize(200, 100);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        frame.add(label);

        return frame;
    }

    @Override
    public MenuItem[] settingsMenu() {
        CheckboxMenuItem showHelp = new CheckboxMenuItem("Show Instructions", showInstructions);
        showHelp.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                showInstructions = e.getStateChange() == ItemEvent.SELECTED;
                eventBus.saveSettings();
            }
        });
        return new MenuItem[] {showHelp};
    }

    @Override
    public void fromSettings(String s) {
        showInstructions = Boolean.parseBoolean(s);
    }

    @Override
    public String toSettings() {
        return showInstructions + "";
    }

    @Override
    public void create() {
        inputBus.addMousePressedListener(e -> {
            clicked = true;
        });

        inputBus.addKeyReleasedListener(e -> {
            if (e == NativeKeyEvent.VC_ENTER) clicked = true;
            else if (e == NativeKeyEvent.VC_ESCAPE) cancelled = true;
        });
    }
}
