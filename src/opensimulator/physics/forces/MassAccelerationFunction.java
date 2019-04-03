/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics.forces;

import java.util.LinkedList;
import opensimulator.universe.Universe;

/**
 *
 * @author azalac
 */
public class MassAccelerationFunction implements AccelerationFunction {

    private final int mass_attr_id;

    private LinkedList<Force> forces = new LinkedList<>();

    public MassAccelerationFunction(int mass_attr_id) {
        this.mass_attr_id = mass_attr_id;
    }

    @Override
    public void GetAccceleration(Universe universe, int index, double[] accel) {

        double m = universe.GetAttr(index, mass_attr_id);

        if(m == 0) {
            throw new IllegalStateException("Mass for object " + index + " cannot be zero");
        }
        
        m = 1d / m;
        
        forces.forEach((force) -> force.Apply(universe, index, accel));

        for (int d = 0; d < Universe.N_DIMENSIONS; d++) {
            accel[d] *= m;
        }

    }

    @Override
    public void addForce(Force f) {
        forces.add(f);
    }

    @Override
    public void removeForce(Force f) {
        forces.remove(f);
    }

}
