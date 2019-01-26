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
public class PriorProbability {
    private double hasil;
    private int classes;

    public PriorProbability(int classSentiment, double probability)
    {
        classes = classSentiment;
        hasil = probability;
    }
    
    public double getPriorProbability(){
        return hasil;
    }
}
