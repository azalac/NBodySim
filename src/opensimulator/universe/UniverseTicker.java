/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.universe;

import opensimulator.physics.SimulationEngine;
import opensimulator.rendering.SimulationRenderer;
import opensimulator.rendering.GraphicsProvider;
import opensimulator.display.Display;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azalac
 */
public class UniverseTicker {

    private final Universe universe;
    private final SimulationEngine sim;
    private final SimulationRenderer renderer;

    private final CyclicBarrier barrier = new CyclicBarrier(2);

    private Thread sim_t, render_t;

    private final GraphicsProvider gprovider;
    private final Display disp;

    private double speed = .75;

    public boolean running = true;

    public UniverseTicker(Universe universe, SimulationEngine sim, SimulationRenderer renderer,
            Display disp) {
        this.universe = universe;
        this.sim = sim;
        this.renderer = renderer;

        this.gprovider = disp.getGraphics();
        this.disp = disp;
    }

    public void start() {

        sim_t = new Thread(this::run_sim, "Simulator");
        render_t = new Thread(this::run_render, "Renderer");

        sim_t.start();
        render_t.start();

    }

    private void run_sim() {

        double start = System.nanoTime();

        while (true) {

            if (running) {
                sim.Tick((System.nanoTime() - start) / 1e9 * speed);
            }

            start = System.nanoTime();

            try {

                barrier.await();

                if (running) {
                    universe.positions.swap();
                    universe.velocities.swap();
                }else{
                    Thread.yield();
                }

                barrier.await();

            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(UniverseTicker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void run_render() {

        while (true) {

            Graphics g = gprovider.get();

            try {

                Dimension d = disp.getSize();

                g.setColor(Color.black);

                g.fillRect(0, 0, d.width, d.height);

                g.setColor(Color.red);
                
                renderer.Draw(g, d.width, d.height);

            } finally {
                gprovider.finish();
            }

            try {

                barrier.await();

                barrier.await();

            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(UniverseTicker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
