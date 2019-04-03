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
public class WorldToScreenTranslation {
    
    protected final double max_percent;
    
    protected double x = -1.5, y = -1.5, s = 3, sx = 3, sy = 3;
    
    public static final double EPSILON = 1e-6;
    
    public WorldToScreenTranslation(double buffer_percent) {
        this.max_percent = buffer_percent;
    }
    
    
    public int getXTranslation(double x, int width) {
        return (int) ((x - this.x) * width / s);
    }
    
    public int getYTranslation(double y, int height) {
        return (int) ((y - this.y) * height / s);
    }
    
    public double left() {
        return x;
    }
    
    public double top() {
        return y;
    }
    
    public double right() {
        return x + Math.max(sx, sy) * 2;
    }
    
    public double bottom() {
        return y + Math.max(sx, sy) * 2;
    }
    
    public void updateTranslation(Universe universe, CoordinateProjector projector) {
        
        double size_x = 0, size_y = 0;
        
        double[] coords = new double[2];
        
        double[] middle = new double[2];
        
        projector.Project(universe.mean, middle);
        
        for(int i = 0; i < universe.count; i++) {
            
            projector.Project(universe.positions, i, Universe.N_DIMENSIONS, coords);
            
            double dx = coords[0] - middle[0];
            double dy = coords[1] - middle[1];
            
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
        
        this.x = middle[0] - size_x;
        this.y = middle[1] - size_y;
        
        this.sx = size_x * 2;
        this.sy = size_y * 2;
        
        if(size_x < size_y) {
            this.s = this.sy;
            this.x -= (size_y - size_x);
        }else{
            this.s = this.sx;
            this.y -= (size_x - size_y);
        }
        
    }
    
    public double getScale() {
        return s;
    }
    
}
