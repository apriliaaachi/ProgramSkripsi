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


    public Evaluator(Weighting bobot) {
        
        MNBProbabilistik mnbProbabilistik = new MNBProbabilistik(bobot);
        
//        System.out.println(bobot.getGlobalTermList().getTotalTerm());
        //mnbProbabilistik.calculatePriorProbability(bobot);
        double priorProb[] = mnbProbabilistik.calculatePriorProbability();
        mnbProbabilistik.calculatePriorProbability();
        for (int i = 0; i < priorProb.length; i++) {
            //System.out.println(mnbProbabilistik.calculatePriorProbability(bobot).size());
            System.out.println("Kelas :" + " " + i);
            System.out.println(priorProb[i]);
        }
        
        double data[][] = mnbProbabilistik.calculateConditionalProbability();
//        for (int i = 0; i < data.length; i++) {
//            //System.out.println("Kelas :" + " " + i);
//            for (int j = 0; j < data[0].length; j++) {
//                
//                System.out.println( data[i][j] + " ");
//            }
//            //System.out.println(mnbProbabilistik.calculatePriorProbability(bobot).size());
//            System.out.println();
//            
//        }
        
//        System.out.println("=============================");
        //mnbProbabilistik.calculateConditionalProbability(trainingSet, bobot);
        
//        MNBClassifier mnbc = new MNBClassifier();
//        mnbc.prepareToClassification();
//        List<Tweet> hasil = mnbc.getTweetList();
//        for (int i = 0; i < hasil.size(); i++) {
//            //System.out.println(hasil.get(i).getTermList().getTotalTerm());
//            System.out.println(hasil.get(i).getContentTweet());
//        }
//        
//        mnbc.cetak();
        
        
        
    }
}
