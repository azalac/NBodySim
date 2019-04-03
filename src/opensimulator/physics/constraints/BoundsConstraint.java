/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics.constraints;

import opensimulator.buffer.BufferMath;
import opensimulator.universe.Universe;
import static opensimulator.universe.Universe.N_DIMENSIONS;

/**
 *
 * @author azalac
 */
public class BoundsConstraint implements PhysicsConstraint {

    private double[] mins, maxes;

    private double res;

    public BoundsConstraint(double restitution, double[] mins, double[] maxes) {
        this.res = restitution;
        this.mins = mins;
        this.maxes = maxes;
    }

    @Override
    public void ApplyConstraint(Universe universe, int index, double[] pos, double[] vel) {

        double min = 1;
        int dim = 0;

        // determine which line to reflect in
        for (int d = 0; d < N_DIMENSIONS; d++) {

            double t = Math.min(intersectmin(pos, vel, d), intersectmax(pos, vel, d));

            if (t > 0 && t < min) {
                min = t;
                dim = d;
            }

            if(pos[d] < mins[d]) {
                pos[d] = mins[d];
                vel[d] *= -res;
            }
            
            if(pos[d] > maxes[d]) {
                pos[d] = maxes[d];
                vel[d] *= -res;
            }
            
        }

        // if the vector doesn't intersect with the box, return
        if (min == 1) {
            return;
        }
        
        BufferMath.ScaleAdd(vel, min, pos, pos);
        
        vel[dim] *= -res;

    }

    private double intersectmin(double[] p, double[] d, int dimension) {
        return (mins[dimension] - p[dimension]) / d[dimension];
    }

    private double intersectmax(double[] p, double[] d, int dimension) {
        return (maxes[dimension] - p[dimension]) / d[dimension];
    }

}
