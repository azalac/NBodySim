/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics.forces;

import java.util.Arrays;
import opensimulator.buffer.BufferMath;
import opensimulator.universe.Universe;
import static opensimulator.universe.Universe.N_DIMENSIONS;

/**
 *
 * @author azalac
 */
public class NBodyGravityForce implements Force {

    private final int mass_attr_id;

    public static final double G_CONSTANT = 0.05;

    public NBodyGravityForce(int mass_attr_id) {
        this.mass_attr_id = mass_attr_id;
    }

    @Override
    public void Apply(Universe universe, int index, double[] force) {

        double[] displacement = new double[N_DIMENSIONS];

        for (int i = 0; i < universe.count; i++) {

            if (i == index) {
                continue;
            }

            BufferMath.Sub(universe.positions, i * Universe.N_DIMENSIONS, universe.positions, index * Universe.N_DIMENSIONS, Universe.N_DIMENSIONS, displacement);
            
            double len2 = BufferMath.Length2(displacement);
            
            if (len2 == 0) {
                continue;
            }

            BufferMath.Scale(displacement, G_CONSTANT * universe.GetAttr(i, mass_attr_id) / (Math.sqrt(len2) * len2), force);
            
        }

    }

}
