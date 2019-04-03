/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics.constraints;

import opensimulator.Pointer;
import opensimulator.universe.Universe;

/**
 *
 * @author azalac
 */
public interface PhysicsConstraint {
    
    public void ApplyConstraint(Universe universe, int index, double[] pos, double[] vel);
    
}
