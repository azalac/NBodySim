/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.buffer;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/**
 *
 * @author memca
 */
public class UnsafeDoubleBuffer extends DoubleBuffer {

    private static Unsafe UNSAFE;

    static {
        try {

            Field singletonInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
            singletonInstanceField.setAccessible(true);
            UNSAFE = (Unsafe) singletonInstanceField.get(null);

        } catch (IllegalArgumentException | SecurityException | NoSuchFieldException | IllegalAccessException ex) {
            Logger.getLogger(DoubleBuffer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private final long address1, address2, real_address1, real_address2;

    public UnsafeDoubleBuffer(int size) {
        super(size);

        // add one for alignment-padding
        size++;
        
        real_address1 = UNSAFE.allocateMemory(size * Unsafe.ARRAY_DOUBLE_INDEX_SCALE);
        real_address2 = UNSAFE.allocateMemory(size * Unsafe.ARRAY_DOUBLE_INDEX_SCALE);

        address1 = alignTo(real_address1, Unsafe.ARRAY_DOUBLE_INDEX_SCALE);
        address2 = alignTo(real_address2, Unsafe.ARRAY_DOUBLE_INDEX_SCALE);
        
    }

    private long alignTo(long value, long alignment) {
        while(value % alignment != 0) {
            value++;
        }
        return value;
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        UNSAFE.freeMemory(real_address1);
        UNSAFE.freeMemory(real_address2);
    }

    @Override
    public double get(int i) {
        long address = current_buffer ? address2 : address1;
        return UNSAFE.getDouble(address + i * Unsafe.ARRAY_DOUBLE_INDEX_SCALE);
    }

    @Override
    public void set(int i, double d) {
        long address = current_buffer ? address1 : address2;
        UNSAFE.putDouble(address + i * Unsafe.ARRAY_DOUBLE_INDEX_SCALE, d);
    }

}
