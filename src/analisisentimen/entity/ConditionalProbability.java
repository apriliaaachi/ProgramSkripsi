/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.entity;

/**
 *
 * @author Asus
 */
public class ConditionalProbability {
    private double hasil;
    private int words;

    public ConditionalProbability(int wordss, double probability)
    {
        words = words;
        hasil = probability;
    }
    
    public double getPriorProbability(){
        return hasil;
    }
}
