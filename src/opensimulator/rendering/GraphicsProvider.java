/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.rendering;

import java.awt.Graphics;

/**
 *
 * @author azalac
 */
public interface GraphicsProvider {
    
    public Graphics get();
    
    public void finish();
    
}
