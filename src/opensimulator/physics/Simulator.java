/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.physics;

import java.util.LinkedList;
import opensimulator.Pointer;
import opensimulator.buffer.BufferMath;
import opensimulator.physics.constraints.PhysicsConstraint;
import opensimulator.universe.Universe;
import static opensimulator.universe.Universe.N_DIMENSIONS;
import opensimulator.physics.forces.AccelerationFunction;
import opensimulator.physics.forces.Force;

/**
 *
 * @author memca
 */
public class Simulator implements SimulationEngine {

    private final Universe u;

    private final AccelerationFunction acceleration;

    private final LinkedList<PhysicsConstraint> constraints = new LinkedList<>();

    public Simulator(Universe universe, AccelerationFunction acceleration) {
        this.u = universe;
        this.acceleration = acceleration;
    }

    public void addForce(Force force) {
        acceleration.addForce(force);
    }

    public void removeForce(Force force) {
        acceleration.removeForce(force);
    }

    public void addConstraint(PhysicsConstraint constraint) {
        constraints.add(constraint);
    }

    public void removeConstraint(PhysicsConstraint constraint) {
        constraints.add(constraint);
    }

    public void Tick(double deltat) {

        double[] a = new double[N_DIMENSIONS],
                v = new double[N_DIMENSIONS],
                p = new double[N_DIMENSIONS];

        BufferMath.Put(u.mean, 0);

        for (int i = 0; i < u.count; i++) {

            BufferMath.Put(a, 0);
            
            acceleration.GetAccceleration(u, i, a);

            BufferMath.Copy(u.velocities, i * N_DIMENSIONS, v, 0, N_DIMENSIONS);
            BufferMath.Copy(u.positions, i * N_DIMENSIONS, p, 0, N_DIMENSIONS);

            BufferMath.ScaleAdd(a, deltat, v, v);

            for (PhysicsConstraint constraint : constraints) {
                constraint.ApplyConstraint(u, i, p, v);
            }

            BufferMath.ScaleAdd(v, deltat, p, p);

            BufferMath.Add(p, u.mean, u.mean);

            BufferMath.Copy(v, 0, u.velocities, i * N_DIMENSIONS, N_DIMENSIONS);
            BufferMath.Copy(p, 0, u.positions, i * N_DIMENSIONS, N_DIMENSIONS);

        }

        BufferMath.Scale(u.mean, 1d / u.count, u.mean);

    }

}
