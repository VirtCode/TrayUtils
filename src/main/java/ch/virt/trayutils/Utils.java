package ch.virt.trayutils;

/**
 * This is a utils class with various different methods
 * @author VirtCode
 * @version 1.0
 */
public class Utils {

    /**
     * Converts a primitive integer array into its class object
     * @param target primitive
     * @return class object
     */
    public static Integer[] fromPrimitive(int[] target){
        Integer[] goal = new Integer[target.length];

        for (int i = 0; i < goal.length; i++) {
            goal[i] = target[i];
        }

        return goal;
    }

    /**
     * Converts a class object integer array to its primitive
     * @param target class object
     * @return integer
     */
    public static int[] toPrimitive(Integer[] target){
        int[] goal = new int[target.length];

        for (int i = 0; i < goal.length; i++) {
            goal[i] = target[i];
        }

        return goal;
    }

}
