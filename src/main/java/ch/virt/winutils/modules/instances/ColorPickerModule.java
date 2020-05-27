package ch.virt.winutils.modules.instances;

import ch.virt.winutils.modules.Module;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * @author VirtCode
 * @version 1.0
 */
public class ColorPickerModule extends Module {
    private boolean showInstructions = true;
    private int frameX = -1;
    private int frameY = -1;

    private boolean clicked;
    private boolean cancelled;
    private boolean alreadyRunning;

    private boolean isMouseInside;
    private boolean isMoving;
    private Point inFramePos;

    public ColorPickerModule() {
        super(9846, "Colorpicker", 46);
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
            inFramePos = null;
            isMouseInside = false;
            isMoving = false;

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

                    if (isMoving){
                        if (inFramePos == null){
                            inFramePos = frame.getMousePosition();
                        }
                        Point mouse = MouseInfo.getPointerInfo().getLocation();
                        frame.setLocation(mouse.x - inFramePos.x, mouse.y - inFramePos.y);

                        Point frameLocation = frame.getLocation();
                        frameX = frameLocation.x;
                        frameY = frameLocation.y;
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
        if(frameX == -1 && frameY == -1) frame.setLocation(bounds.width - 10 - 200, bounds.height - 50 - 100);
        else frame.setLocation(frameX, frameY);

        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                isMouseInside = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isMouseInside = false;
            }
        });
        frame.setVisible(true);

        if (showInstructions) {
            JLabel instructions = new JLabel("Click to Select. Esc to Cancel.", SwingConstants.CENTER);
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
        showHelp.addItemListener(e -> {
            showInstructions = e.getStateChange() == ItemEvent.SELECTED;
            eventBus.saveSettings();
        });

        MenuItem resetPosition = new MenuItem("Reset Position");
        resetPosition.addActionListener(e -> {
            frameX = -1;
            frameY = -1;
            eventBus.saveSettings();
        });

        return new MenuItem[] {showHelp, resetPosition};
    }

    @Override
    public void fromSettings(String s) {
        JsonObject object = new Gson().fromJson(s, JsonObject.class);

        frameX = object.get("frame_x").getAsInt();
        frameY = object.get("frame_y").getAsInt();
        showInstructions = object.get("instructions").getAsBoolean();
    }
    @Override
    public String toSettings() {
        JsonObject object = new JsonObject();

        object.addProperty("instructions", showInstructions);
        object.addProperty("frame_x", frameX);
        object.addProperty("frame_y", frameY);

        return new Gson().toJson(object);
    }

    @Override
    public void create() {
        inputBus.addMousePressedListener(e -> {
            if (isMouseInside) isMoving = true;
            else clicked = true;
        });

        inputBus.addMouseReleasedListener(e -> {
            if (isMoving){
                isMoving = false;
                inFramePos = null;

                eventBus.saveSettings();
            }
        });

        inputBus.addKeyReleasedListener(e -> {
            if (e == NativeKeyEvent.VC_ENTER) clicked = true;
            else if (e == NativeKeyEvent.VC_ESCAPE) cancelled = true;
        });
    }
}