/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics.forces;

import opensimulator.universe.Universe;

/**
 *
 * @author azalac
 */
public interface AccelerationFunction {
    
    public void GetAccceleration(Universe universe, int index, double[] force);
    
    public void addForce(Force f);
    
    public void removeForce(Force f);
    
}
