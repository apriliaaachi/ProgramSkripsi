/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.ConditionalProbability;
import analisisentimen.entity.PriorProbability;
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
    
    
    public MNBProbabilistik() {
        
        
    }

    public List<PriorProbability> calculatePriorProbability(List<Tweet> trainingSet, Weighting bobot){
       
        int classes[] = new int[2];
        classes[0] = 0;
        classes[1] = 1;
        List<PriorProbability> prior = new ArrayList<>();
        int numberOfData = trainingSet.size();
        double hasil = 0;
        
        
        
        for (int i = 0; i < classes.length; i++) {
            
            //System.out.println(numberOfDataWithClass + "+" + 1 + "/" + numberOfData + "+" + classes.length + ":");  
            hasil = (double)(bobot.numberOfDataWithClass(classes[i]) + 1) / (numberOfData + classes.length);
            
            PriorProbability priProb = new PriorProbability(classes[i], hasil);
            prior.add(priProb);
                                
        }

        return prior;
    }
    
    public List<ConditionalProbability> calculateConditionalProbability(List<Tweet> trainingSet, Weighting bobot) {
        int classes[] = new int[2];
        classes[0] = 0;
        classes[1] = 1;
        double hasil = 0;
        List<ConditionalProbability> con = new ArrayList<>();
        int totalTerm = bobot.getGlobalTermList().getTotalTerm();
        
        for(int i=0; i < classes.length; i++){
            for (int j = 0; j < totalTerm ; j++) {
            
                hasil = (bobot.numberOfWeightWithClassInData(i, bobot.getGlobalTermList().getTermAt(j))+1)/((bobot.numberOfAllWeightWithClass(i))+totalTerm);

                ConditionalProbability conProb = new ConditionalProbability(classes[0], hasil);
                con.add(conProb);

            }
        }        
        
        return con;
    }
 
}