/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.display;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import opensimulator.rendering.BufferStrategyGraphicsProvider;
import opensimulator.rendering.GraphicsProvider;

/**
 *
 * @author memca
 */
public class JFrameDisplay implements Display {

    private final JFrame frame = new JFrame("Display");

    private final JPanel panel = new JPanel();

    private BufferStrategy bs;

    public JFrameDisplay() {

        frame.setContentPane(panel);

        frame.setSize(750, 750);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setIgnoreRepaint(true);

    }

    @Override
    public GraphicsProvider getGraphics() {
        
        Point a = frame.getLocationOnScreen(), b = frame.getContentPane().getLocationOnScreen();
        
        return new BufferStrategyGraphicsProvider(bs, b.x - a.x, b.y - a.y);
    }

    @Override
    public void show() {
        
        frame.setVisible(true);
        
        if ((bs = frame.getBufferStrategy()) == null) {
            frame.createBufferStrategy(3);
            bs = frame.getBufferStrategy();
        }

    }

    @Override
    public Dimension getSize() {
        return frame.getContentPane().getSize();
    }

}
