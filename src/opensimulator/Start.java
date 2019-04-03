/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator;

import opensimulator.universe.UniverseTicker;
import opensimulator.universe.Universe;
import opensimulator.universe.UniverseFactory;
import opensimulator.physics.forces.NBodyGravityForce;
import opensimulator.physics.Simulator;
import opensimulator.rendering.SimulationRenderer;
import opensimulator.rendering.XYProjector;
import opensimulator.rendering.WorldToScreenTranslation;
import opensimulator.display.JFrameDisplay;
import opensimulator.display.Display;
import opensimulator.physics.SimulationEngine;
import opensimulator.physics.constraints.BoundsConstraint;
import opensimulator.physics.forces.MassAccelerationFunction;

/**
 *
 * @author memca
 */
public class Start {

    public static int MASS_ATTR_ID;
    public static int RADIUS_ATTR_ID;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int n = 7;

        UniverseFactory factory = new UniverseFactory();

        factory.setCount(n);

        MASS_ATTR_ID = factory.CreateAttribute();
        RADIUS_ATTR_ID = factory.CreateAttribute();
        
        Universe universe = factory.build();

        universe.Randomize();

        for(int i = 0; i < universe.count; i++) {
            universe.SetAttr(i, MASS_ATTR_ID, 1);
        }
        
        Simulator sim = new Simulator(universe, new MassAccelerationFunction(MASS_ATTR_ID));

        sim.addForce(new NBodyGravityForce(MASS_ATTR_ID));
        
        sim.addConstraint(new BoundsConstraint(1, new double[]{-1,-1}, new double[]{1,1}));
        
        SimulationRenderer render = new SimulationRenderer(universe, new XYProjector(), new WorldToScreenTranslation(0.9));

        Display disp = new JFrameDisplay();
        
        disp.show();
        
        UniverseTicker ticker = new UniverseTicker(universe, sim, render, disp);

        ticker.start();

    }

}
