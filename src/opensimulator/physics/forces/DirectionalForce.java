/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics.forces;

import opensimulator.buffer.BufferMath;
import opensimulator.universe.Universe;

/**
 *
 * @author memca
 */
public class DirectionalForce implements Force{

    private double[] force;
    
    public DirectionalForce(double... force) {
        this.force = force;
    }
    
    public void setForce(double... force) {
        this.force = force;
    }
    
    public void setForce(int dimension, double f) {
        force[dimension] = f;
    }
    
    public double getForce(int dimension) {
        return force[dimension];
    }
    
    @Override
    public void Apply(Universe u, int index, double[] force) {
        
        BufferMath.Add(force, this.force, force);
        
    }
    
}
