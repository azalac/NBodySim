/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.universe;

import opensimulator.buffer.DoubleBuffer;

/**
 *
 * @author memca
 */
public class Universe {
    
    public static final int N_DIMENSIONS = 2;
    
    public final int count;
    
    public final DoubleBuffer positions;
    public final DoubleBuffer velocities;
    
    public final double[] attributes;
    
    public final int n_attributes;
    
    public final double[] mean;
    public final double[] deviation;
    
    public Universe(int count, int n_attributes) {
        
        this.count = count;
        this.n_attributes = n_attributes;
        
        positions = DoubleBuffer.create(count * N_DIMENSIONS);
        velocities = DoubleBuffer.create(count * N_DIMENSIONS);
        attributes = new double[count * n_attributes];
        
        mean = new double[N_DIMENSIONS];
        deviation = new double[N_DIMENSIONS];
        
    }

    public double GetAttr(int index, int attr) {
        return attributes[index * n_attributes + attr];
    }
    
    public void SetAttr(int index, int attr, double value) {
        attributes[index * n_attributes + attr] = value;
    }
    
    public void Randomize() {
        
        for(int i = 0; i < N_DIMENSIONS * count; i++) {
            positions.set(i, Math.random() * 2 - 1);
            //velocities.set(i, Math.random() * 2 - 1);
        }
        
        positions.swap();
        velocities.swap();
        
    }
    
}
