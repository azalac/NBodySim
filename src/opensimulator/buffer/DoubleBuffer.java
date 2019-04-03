/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.buffer;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azalac
 */
public abstract class DoubleBuffer {

    /**
     * False for buffer 1, True for buffer 2.<br>
     *
     * When buffer 1 is the current buffer, get from it, and write to buffer 2.
     * When buffer 2 is the current buffer, get from it, and write to buffer 1.
     */
    protected boolean current_buffer = false;

    public DoubleBuffer(int size) {

    }

    public void swap() {
        current_buffer = !current_buffer;
    }

    public abstract double get(int i);

    public abstract void set(int i, double d);

    /**
     * Creates a new buffer, based on the fastest type.
     *
     * @param preferFasterRead {@code true} if the buffer should be read-optimized, {@code false} for write optimized
     * @param size The size of the buffer
     * @return The created buffer, or null on an error.
     */
    public static DoubleBuffer create(boolean preferFasterRead, int size) {
        Class clazz = preferFasterRead ? fastest_classes[0] : fastest_classes[1];

        try {
            return (DoubleBuffer) clazz.getConstructor(int.class).newInstance(size);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            // What a catch!
            Logger.getLogger(DoubleBuffer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * Creates a new read-optimized buffer.
     * @param size The size of the buffer
     * @return The created buffer, or null on an error.
     * 
     * @see DoubleBuffer#create(boolean, int)
     */
    public static DoubleBuffer create(int size) {
        return create(true, size);
    }
    
    private static Class[] fastest_classes = DoSpeedTest(500000);

    /**
     * ArrayDoubleBuffer is faster than UnsafeDoubleBuffer in the majority of
     * cases.
     *
     * @param n The number of iterations to check
     * @return The fastest read and write classes.
     */
    public final static Class[] DoSpeedTest(int n) {

        Class[] classes = {UnsafeDoubleBuffer.class, ArrayDoubleBuffer.class};

        long[] read_times = new long[classes.length];
        long[] write_times = new long[classes.length];

        for (int c = 0; c < classes.length; c++) {

            Class clazz = classes[c];

            if (!DoubleBuffer.class.isAssignableFrom(clazz)) {
                System.err.println("Warning: found invalid class while speed testing: " + clazz.getSimpleName());
                continue;
            }

            DoubleBuffer buffer;

            try {
                buffer = (DoubleBuffer) clazz.getConstructor(int.class).newInstance(n);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                // What a catch!
                Logger.getLogger(DoubleBuffer.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }

            long pre = System.nanoTime();

            for (int i = 0; i < n; i++) {
                buffer.set(i, Math.random());
            }

            long len_write = (System.nanoTime() - pre) / 1000000;

            pre = System.nanoTime();

            for (int i = 0; i < n; i++) {
                double d = buffer.get(i);
            }

            long len_read = (System.nanoTime() - pre) / 1000000;

            System.out.format("%s:\n\tWrite:\t\t%d ms\n\tRead:\t\t%d ms\n\tTotal:\t\t%d ms\n", clazz.getSimpleName(), len_write, len_read, (len_write + len_read));

            write_times[c] = len_write;
            read_times[c] = len_read;

        }

        long ms_threshold = 10;

        long read_fastest = 0;
        long write_fastest = 0;

        Class read = null, write = null;

        for (int i = 0; i < classes.length; i++) {

            if (read_times[i] < read_fastest - ms_threshold || read == null) {
                read_fastest = read_times[i];
                read = classes[i];
            }

            if (write_times[i] < write_fastest - ms_threshold || write == null) {
                write_fastest = write_times[i];
                write = classes[i];
            }

        }

        System.out.format("Fastest Reader: %s\n\t%d ms\nFastest Writer: %s\n\t%d ms\n", read.getSimpleName(), read_fastest, write.getSimpleName(), write_fastest);

        return new Class[]{read, write};

    }
}
