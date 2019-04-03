/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics.forces;

import opensimulator.universe.Universe;
import static opensimulator.universe.Universe.N_DIMENSIONS;

/**
 *
 * @author memca
 */
public class NBodySumForce implements Force {

    @Override
    public void Apply(Universe universe, int index, double[] force) {

        double[] R = new double[N_DIMENSIONS];

        for (int i = 0; i < universe.count; i++) {

            if (i == index) {
                continue;
            }

            double len2 = 0;

            for (int d = 0; d < N_DIMENSIONS; d++) {

                R[d] = get(i, d, universe) - get(index, d, universe);

                len2 += R[d] * R[d];

            }

            if (len2 == 0) {
                continue;
            }

            for (int d = 0; d < N_DIMENSIONS; d++) {
                force[d] += R[d] / (Math.sqrt(len2) * len2);
            }

        }

    }

    private double get(int index, int dimension, Universe u) {
        return u.positions.get(index * Universe.N_DIMENSIONS + dimension);
    }

}
