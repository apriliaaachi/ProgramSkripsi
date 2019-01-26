/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Tweet;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Evaluator {
    private MNBClassifier mnbClassifier;

    public Evaluator(List<Tweet> trainingSet, Weighting bobot) {
        
        mnbClassifier = new MNBClassifier();
        
        MNBProbabilistik mnbProbabilistik = new MNBProbabilistik();
        
        System.out.println("INI DIA");
        System.out.println(bobot.getGlobalTermList().getTotalTerm());
        
        mnbProbabilistik.calculatePriorProbability(trainingSet, bobot);
        for (int i = 0; i < mnbProbabilistik.calculatePriorProbability(trainingSet, bobot).size(); i++) {
            System.out.println(mnbProbabilistik.calculatePriorProbability(trainingSet, bobot).size());
            System.out.println("Probabilitas Kelas" + " " + i + " " + "=" + mnbProbabilistik.calculatePriorProbability(trainingSet, bobot).get(i).getPriorProbability());
            System.out.println();
        }
        
        for (int i = 0; i < mnbProbabilistik.calculateConditionalProbability(trainingSet, bobot).size(); i++) {
            
            System.out.println(mnbProbabilistik.calculatePriorProbability(trainingSet, bobot).size());
            System.out.println("Probabilitas Word" + " " + i + " " + "=" + mnbProbabilistik.calculateConditionalProbability(trainingSet, bobot).get(i).getPriorProbability());
            System.out.println();
        }
        //mnbProbabilistik.calculateConditionalProbability(trainingSet, bobot);
        
        
  
    }
}
