/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics.forces;

import opensimulator.universe.Universe;

/**
 *
 * @author memca
 */
public interface Force {
    
    public void Apply(Universe u, int index, double[] force);
    
}
