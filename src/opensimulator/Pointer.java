/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator;

/**
 *
 * @author memca
 */
public class Pointer<T> {
    
    public T value;
    
    public Pointer() {
        this(null);
    }
    
    public Pointer(T initial) {
        value = initial;
    }
    
}
