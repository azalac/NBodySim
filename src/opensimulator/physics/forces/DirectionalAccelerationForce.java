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
public class DirectionalAccelerationForce implements Force{

    private double[] force;
    private final int mass_attr_id;
    
    public DirectionalAccelerationForce(int mass_attr_id, double... force) {
        this.mass_attr_id = mass_attr_id;
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
        
        BufferMath.ScaleAdd(this.force, 1d/u.GetAttr(index, mass_attr_id), force, force);
        
    }
    
}
