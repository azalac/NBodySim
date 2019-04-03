/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.rendering;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 *
 * @author azalac
 */
public class BufferStrategyGraphicsProvider implements GraphicsProvider {

    private final BufferStrategy bs;

    private Graphics g;

    private int xoff, yoff;
    
    public BufferStrategyGraphicsProvider(BufferStrategy bs) {
        this(bs, 0, 0);
    }

    public BufferStrategyGraphicsProvider(BufferStrategy bs, int xoffset, int yoffset) {
        this.bs = bs;
        xoff = xoffset;
        yoff = yoffset;
    }

    @Override
    public Graphics get() {
        
        if (g == null) {
            g = bs.getDrawGraphics();
            g.translate(xoff, yoff);
        }
        
        return g;
    }

    @Override
    public void finish() {
        
        if (g != null) {
            g.dispose();
            g = null;
        }

        if (!bs.contentsLost()) {
            bs.show();
        }

    }

}
