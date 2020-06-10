package ch.virt.winutils;

import ch.virt.winutils.event.Listener;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Utils {

    public static Integer[] fromPrimitive(int[] target){
        Integer[] goal = new Integer[target.length];

        for (int i = 0; i < goal.length; i++) {
            goal[i] = target[i];
        }

        return goal;
    }

    public static int[] toPrimitive(Integer[] target){
        int[] goal = new int[target.length];

        for (int i = 0; i < goal.length; i++) {
            goal[i] = target[i];
        }

        return goal;
    }

}
