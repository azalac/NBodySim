/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics;

import opensimulator.buffer.BufferMath;
import opensimulator.physics.constraints.PhysicsConstraint;
import opensimulator.universe.Universe;
import static opensimulator.universe.Universe.N_DIMENSIONS;

/**
 *
 * @author azalac
 */
public class RadialConstraint implements PhysicsConstraint {

    private final int radius_attr_id;

    public RadialConstraint(Universe universe, int radius_attr_id) {
        this.radius_attr_id = radius_attr_id;
    }

    @Override
    public void ApplyConstraint(Universe universe, int index, double[] pos, double[] vel) {

        double[] q0 = v();

        double t = 1;
        int hit = -1;

        double[] vel_norm = v();
        
        BufferMath.Normalize(vel, vel_norm);
        
        for (int i = 0; i < universe.count; i++) {

            if (i == index) {
                continue;
            }

            double t_ = CircleCircleIntersect(universe, index, i, pos, vel_norm, radius_attr_id);

            if (t_ > 0 && t_ < t) {
                t = t_;
                hit = i;
            }

        }

        if (hit != -1) {
            
        }

    }

    /**
     * Calculates the intersection position when raytracing two circles.
     *
     * @param vel Must be normalized
     * @see <a href="https://www.desmos.com/calculator/lg4ngeelrk">Graph
     * Version</a>
     */
    public static double CircleCircleIntersect(Universe u, int circle1, int circle2, double[] pos, double[] vel, int radius_attr_id) {

        double[] p_b = BufferMath.AsArray(u.positions, circle2, N_DIMENSIONS);
        
        double[] pa = v();

        BufferMath.Sub(pos, p_b, pa);

        double a = BufferMath.Dot(vel, pa);

        double[] T = v();

        BufferMath.ScaleAdd(vel, -a, pa, T);
        BufferMath.Sub(T, pos, T);

        double r_tot = u.GetAttr(circle1, radius_attr_id) + u.GetAttr(circle2, radius_attr_id);

        double b = Math.sqrt(r_tot * r_tot - BufferMath.Length2Sub(pa, T));

        return -(a + b) / Math.sqrt(BufferMath.Length2Sub(p_b, pos));

    }

    private static double[] v() {
        return new double[N_DIMENSIONS];
    }

}
