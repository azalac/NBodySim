/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.display;

import java.awt.Dimension;
import opensimulator.rendering.GraphicsProvider;

/**
 *
 * @author memca
 */
public interface Display {
    
    public GraphicsProvider getGraphics();
    
    public void show();
    
    public Dimension getSize();
    
}
