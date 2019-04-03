/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.rendering;

import opensimulator.buffer.DoubleBuffer;

/**
 *
 * @author memca
 */
public interface CoordinateProjector {
    
    /**
     * Projects an implicitly given point to a 2-dimension point on the screen.
     * @param positions The positions
     * @param index The index to convert
     * @param n_dimensions The number of dimensions
     * @param out The 2-d buffer to put the output in
     */
    public void Project(DoubleBuffer positions, int index, int n_dimensions, double[] out);
    
    public void Project(double[] position, double[] out);
    
}
