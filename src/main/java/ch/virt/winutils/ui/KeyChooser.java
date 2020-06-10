package ch.virt.winutils.ui;

import ch.virt.winutils.event.InputBus;
import ch.virt.winutils.event.Listener;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * @author VirtCode
 * @version 1.0
 */
public class KeyChooser {

    private static ArrayList<Integer> currentlyPressed;
    private static boolean running;
    private static boolean finished;
    private static boolean canceled;
    private static boolean chooseOne;

    public KeyChooser(InputBus bus) {
        currentlyPressed = new ArrayList<>();
        bus.addKeyPressedListener(c -> {
            if (running){
                if (c == NativeKeyEvent.VC_ENTER) finished = true;
                else if (chooseOne){
                    currentlyPressed = new ArrayList<>();
                    currentlyPressed.add(c);
                } else if(!currentlyPressed.contains(c)) currentlyPressed.add(c);
            }

        });
        bus.addKeyReleasedListener(c -> {
            finished = true;
        });

        bus.addMouseReleasedListener(c -> {
            canceled = true;
        });
    }

    public static JDialog createFrame(){
        JDialog frame = new JDialog();
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 400, 200, 5, 5));
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel instructions = new JLabel("Click to Cancel. Enter to force confirmation.", SwingConstants.CENTER);
        instructions.setFont(new Font("Calibri", Font.PLAIN, 12));
        instructions.setSize(400, 20);
        instructions.setLocation(0, 180);
        frame.add(instructions);

        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setSize(400, 200);
        label.setFont(new Font("Calibri", Font.BOLD, 20));
        label.setLocation(0, 0);
        label.setForeground(Color.WHITE);
        frame.add(label);

        frame.getContentPane().setBackground(new Color(0x3C3F41));

        return frame;
    }

    public static void choose(Listener<Integer[]> chosenKeycodes){
        chooseOne = false;

        Runnable frameRun = () -> {
            running = true;
            canceled = false;
            finished = false;
            currentlyPressed = new ArrayList<>();

            JDialog frame = createFrame();

            while (true) {
                JLabel label = (JLabel) frame.getContentPane().getComponent(1);
                label.setText(keyString());

                if (finished){
                    chosenKeycodes.called(currentlyPressed.toArray(new Integer[0]));
                    break;
                }
                if (canceled) break;
            }

            frame.dispose();
            running = false;
        };

        Thread thread = new Thread(frameRun);
        thread.start();
    }

    public static void chooseOne(Listener<Integer> chosenKeycodes){
        chooseOne = true;

        Runnable frameRun = () -> {
            running = true;
            canceled = false;
            finished = false;
            currentlyPressed = new ArrayList<>();

            JDialog frame = createFrame();

            while (true) {
                JLabel label = (JLabel) frame.getContentPane().getComponent(1);
                label.setText(keyString());

                if (finished){
                    chosenKeycodes.called(currentlyPressed.get(0));
                    break;
                }
                if (canceled) break;
            }

            frame.dispose();
            running = false;
        };

        Thread thread = new Thread(frameRun);
        thread.start();
    }

    private static String keyString(){
        return prettifyKeyArray(currentlyPressed.toArray(new Integer[0]));
    }

    public static String prettifyKeyArray(Integer[] binds){
        if (binds.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int integer : binds) {
            sb.append(" + ");
            sb.append(NativeKeyEvent.getKeyText(integer));
        }

        return sb.toString().substring(3);
    }
}
