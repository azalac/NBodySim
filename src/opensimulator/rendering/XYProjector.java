/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.rendering;

import opensimulator.buffer.DoubleBuffer;

/**
 *
 * @author azalac
 */
public class XYProjector implements CoordinateProjector{

    @Override
    public void Project(DoubleBuffer positions, int index, int n_dimensions, double[] out) {
        
        index *= n_dimensions;
        
        if(n_dimensions >= 2) {
            out[0] = positions.get(index);
            out[1] = positions.get(index + 1);
        }else if(n_dimensions == 1){
            out[0] = positions.get(index);
            out[1] = 0.5;
        }else{
            throw new IllegalArgumentException("N_Dimensions must be >= 1");
        }
    }

    @Override
    public void Project(double[] position, double[] out) {
        
        if(position.length >= 2) {
            out[0] = position[0];
            out[1] = position[1];
        }else if(position.length == 0){
            out[0] = position[0];
            out[1] = 0.5;
        }else{
            throw new IllegalArgumentException("N_Dimensions must be >= 1");
        }
        
    }
    
}
