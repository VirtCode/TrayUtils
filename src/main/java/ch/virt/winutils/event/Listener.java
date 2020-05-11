package ch.virt.winutils.event;

/**
 * @author VirtCode
 * @version 1.0
 */
public interface Listener<T> {
    void called(T arg);
}
