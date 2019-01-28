/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Tweet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;


/**
 *
 * @author Asus
 */
public class MNBProbabilistik {
    private double[] prior;
    private double[][] conditional;
    private Weighting bobotTerm;
    
    public MNBProbabilistik(Weighting bobot) {
        bobotTerm = bobot;
        
    }

    public double[] calculatePriorProbability(){
       
        int classes[] = new int[2];
        classes[0] = 0;
        classes[1] = 1;
        double[] prior = new double[classes.length];
        int numberOfData = bobotTerm.getTweetList().size();
        double hasil = 0;
        
        for (int i = 0; i < classes.length; i++) {
            
            hasil = (double)(bobotTerm.numberOfDataWithClass(classes[i]) + 1) / (numberOfData + classes.length);
            prior[i] = hasil; 
                                
        }
        
        return prior;
    }
    
    public double[][] calculateConditionalProbability() {
        int classes[] = new int[2];
        classes[0] = 0;
        classes[1] = 1;
  
        int totalTerm = bobotTerm.getGlobalTermList().getTotalTerm();
        conditional = new double[classes.length][totalTerm];
        
        
        for(int i=0; i < classes.length; i++){
            
            for (int j = 0; j < totalTerm ; j++) {
//                System.out.println(bobot.numberOfWeightWithClassInData(i, bobot.getGlobalTermList().getTermAt(j)));
                
//                System.out.println(bobot.numberOfWeightWithClassInData(i, bobot.getGlobalTermList().getTermAt(j)) + 
//                        " " + "+" + "1" + "/" + bobot.numberOfAllWeightWithClass(i) + "+" + totalTerm + ":");
                double hasil = (bobotTerm.numberOfWeightWithClassInData(i, bobotTerm.getGlobalTermList().getTermAt(j))+1)/((bobotTerm.numberOfAllWeightWithClass(i)) + totalTerm);
                conditional[i][j] = hasil;

            }
            //System.out.println("==============================");
                                  
        }        
        return conditional;
    }
    
    
 
}