/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.rendering;

import java.awt.Color;
import java.awt.Graphics;
import opensimulator.physics.Simulator;
import opensimulator.universe.Universe;
import static opensimulator.universe.Universe.N_DIMENSIONS;

/**
 *
 * @author azalac
 */
public class SimulationRenderer {

    private final Universe u;
    private final CoordinateProjector projector;
    private final WorldToScreenTranslation translator;

    private boolean drawGrid = true;

    public SimulationRenderer(Universe universe, CoordinateProjector projector, WorldToScreenTranslation translator) {
        this.u = universe;
        this.projector = projector;
        this.translator = translator;
    }

    public void setDrawGrid(boolean draw) {
        drawGrid = draw;
    }
    
    public void Draw(Graphics g, int width, int height) {

        double[] coord = new double[2];
        double[] v = new double[2];

        CalculatePostStats();

        translator.updateTranslation(u, projector);

        if (drawGrid) {
            g.setColor(Color.cyan);

            double size = getClosestRatio(translator.getScale(), 4);
            double top = Math.floor(translator.top() / size) * size;
            double left = Math.floor(translator.left() / size) * size;
            double bottom = Math.ceil(translator.bottom() / size) * size;
            double right = Math.ceil(translator.right() / size) * size;

            for (double x = left; x < right; x += size) {

                g.drawLine(x(x, width), 0, x(x, width), height);

            }

            for (double y = top; y < bottom; y += size) {

                g.drawLine(0, y(y, height), width, y(y, height));

            }
        }
        
        g.setColor(Color.red);

        int h = g.getFontMetrics().getHeight();

        for (int i = 0; i < u.count; i++) {

            g.setColor(Color.getHSBColor((float) i / u.count, 1, 1));

            projector.Project(u.positions, i, N_DIMENSIONS, coord);
            projector.Project(u.velocities, i, N_DIMENSIONS, v);

            drawPoint(g, coord[0], coord[1], width, height);

            g.drawString(String.format("%03.02f:%03.02f,  %03.02f:%03.02f", coord[0], coord[1], v[0], v[1]), 10, 10 + h * i + h * 2);

        }

        projector.Project(u.mean, coord);

        g.setColor(Color.gray);

        drawPoint(g, coord[0], coord[1], width, height);

        g.drawString(String.format("Scale: %.2f", translator.getScale()), 10, 10 + h);

    }

    private double getClosestRatio(double x, double n) {

        int i = 0;
        
        if(x < 0) {
            x = -x;
        }

        while (x > n) {
            i++;
            x /= n;
        }

        while (x < 1) {
            i--;
            x *= n;
        }

        return Math.pow(n, i);

    }

    private void drawPoint(Graphics g, double x, double y, int width, int height) {

        g.fillRect(x(x, width) - 2, y(y, height) - 2, 4, 4);

    }

    private void drawRect(Graphics g, double x1, double y1, double x2, double y2, int w, int h) {

        g.drawRect(x(x1, w), y(y1, h), x(x2, w) - x(x1, w), y(y2, h) - y(y1, h));

    }

    private int x(double x, int width) {
        return translator.getXTranslation(x, width);
    }

    private int y(double y, int height) {
        return translator.getYTranslation(y, height);
    }

    private void CalculatePostStats() {

        for (int i = 0; i < u.count; i++) {
            for (int d = 0; d < N_DIMENSIONS; d++) {
                double dev = (u.positions.get(i * N_DIMENSIONS + d) - u.mean[d]);
                u.deviation[d] += dev * dev;
            }
        }

        for (int d = 0; d < N_DIMENSIONS; d++) {

            u.deviation[d] /= u.count;

            u.deviation[d] = Math.sqrt(u.deviation[d]);
        }

    }

}
