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
    private Weighting weightTerm;
    
    public MNBProbabilistik(Weighting weight) {
        weightTerm = weight;
        
    }

    public double[] calculatePriorProbability(){
       
        int classes[] = new int[]{0,1};
        
        double[] prior = new double[classes.length];
        int numberOfData = weightTerm.getTweetList().size();
        double result = 0;
        
        for (int i = 0; i < classes.length; i++) {
            
            result = (double)(weightTerm.numberOfDataWithClass(classes[i]) + 1) / (numberOfData + classes.length);
            prior[i] = result; 
                                
        }
        
        return prior;
    }
    
    
    
    public double[][] calculateConditionalProbability() {
        int classes[] = new int[2];
        classes[0] = 0;
        classes[1] = 1;
  
        int totalTerm = weightTerm.getGlobalTermList().getTotalTerm();
        conditional = new double[classes.length][totalTerm];
        
        
        for(int i=0; i < classes.length; i++){
            
            for (int j = 0; j < totalTerm ; j++) {

                double result = (weightTerm.numberOfWeightWithClassInData(i, weightTerm.getGlobalTermList().getTermAt(j))+1)/((weightTerm.numberOfAllWeightWithClass(i)) + totalTerm);
                conditional[i][j] = result;

            }
                                  
        }        
        return conditional;
    }
    
    
 
}