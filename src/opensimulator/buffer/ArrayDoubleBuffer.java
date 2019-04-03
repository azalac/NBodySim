/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.buffer;

/**
 *
 * @author azalac
 */
public class ArrayDoubleBuffer extends DoubleBuffer{

    private final double[] buffer1, buffer2;
    
    public ArrayDoubleBuffer(int size) {
        super(size);
        buffer1 = new double[size];
        buffer2 = new double[size];
    }

    @Override
    public double get(int i) {
        return (current_buffer ? buffer2 : buffer1)[i];
    }

    @Override
    public void set(int i, double d) {
        (current_buffer ? buffer1 : buffer2)[i] = d;
    }
    
}
