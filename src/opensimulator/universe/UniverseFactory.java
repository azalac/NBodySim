/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.universe;

/**
 *
 * @author azalac
 */
public class UniverseFactory {
    
    private int count, n_attributes;
    
    public int CreateAttribute() {
        return n_attributes++;
    }
    
    public void setCount(int count) {
        if(count >= this.count) {
            this.count = count;
        }else{
            throw new IllegalArgumentException("count cannot be negative");
        }
    }
    
    public Universe build() {
        return new Universe(count, n_attributes);
    }
    
}
