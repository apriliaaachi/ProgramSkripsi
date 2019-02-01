/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Tweet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Evaluator {

    public Evaluator(MNBClassifier mnbc) {
        int TP=0, FP=0, TN=0, FN=0;
        //System.out.println(mnbc.classifyFull());
        
        
        for (int i = 0; i < mnbc.classifyFull().length; i++) {
            //System.out.println(mnbc.classifyFull()[i]);
            if(mnbc.classifyFull()[i] == 0) {
                if(mnbc.classifyFull()[i] == mnbc.getTweetList().get(i).getClassSentiment()) {
                    TN++;
                } else {
                    FN++;
                }
            } else if(mnbc.classifyFull()[i] == 1) {
                if(mnbc.classifyFull()[i] == mnbc.getTweetList().get(i).getClassSentiment()) {
                    TP++;
                } else {
                    FP++;
                }
            }
            
        }

        
//        System.out.println("TN :" + " " + TN );
//        System.out.println("FN :" + " " + FN );
//        System.out.println("TP :" + " " + TP );
//        System.out.println("FP :" + " " + FP );
//        
//        
//        System.out.println("precision :" + precision(TN, FN, TP, FP));
//        System.out.println("recall :" + recall(TN, FN, TP, FP));
//        System.out.println("fmeasure :" + fMeasure(TN, FN, TP, FP));
//        System.out.println("accuracy :" + accuracy(TN, FN, TP, FP));
        
    }
    

    private double precision(int TN, int FN, int TP, int FP) {
        return (double)TP/(TP+FP);
    }

    private double recall(int TN, int FN, int TP, int FP) {
         return (double)TP/(TP+FN);
    }

    private double fMeasure(int TN, int FN, int TP, int FP) {
         return (double)2 * ((precision(TN, FN, TP, FP)* recall(TN, FN, TP, FP))/(precision(TN, FN, TP, FP)+ recall(TN, FN, TP, FP)));
    }

    private double accuracy(int TN, int FN, int TP, int FP) {
        return (double)(TP+TN)/(TP+TN+FP+FN);
    }
}
