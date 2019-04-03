/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.rendering;

import opensimulator.universe.Universe;

/**
 *
 * @author memca
 */
public class CenteredW2STranslation extends WorldToScreenTranslation{
    
    public CenteredW2STranslation(double buffer_percent) {
        super(buffer_percent);
    }
    
    @Override
    public void updateTranslation(Universe universe, CoordinateProjector projector) {
        
        double size_x = 0, size_y = 0;
        
        double[] coords = new double[2];
        
        for(int i = 0; i < universe.count; i++) {
            
            projector.Project(universe.positions, i, Universe.N_DIMENSIONS, coords);
            
            double dx = coords[0];
            double dy = coords[1];
            
            if(dx < 0) {
                dx *= -1;
            }
            
            if(dy < 0) {
                dy *= -1;
            }
            
            if(dx > size_x) {
                size_x = dx;
            }
            
            if(dy > size_y) {
                size_y = dy;
            }
            
        }
        
        size_x /= max_percent;
        size_y /= max_percent;
        
        this.x = -size_x;
        this.y = -size_y;
        
        if(size_x < size_y) {
            this.s = size_y * 2;
            this.x -= size_y - size_x;
        }else{
            this.s = size_x * 2;
            this.y -= size_x - size_y;
        }
        
    }
    
}
