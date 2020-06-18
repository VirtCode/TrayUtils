package ch.virt.winutils.event;

/**
 * This is a basic Listener class for many uses
 * @author VirtCode
 * @version 1.0
 */
public interface Listener<T> {
    void called(T arg);
}
