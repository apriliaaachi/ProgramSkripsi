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
public final class Evaluator {
    double fMeasure, precision, accuracy, recall;
    int TP, FP, TN, FN;
    
    public Evaluator(MNBClassifier mnbc) {
        TP=0; FP=0; TN=0; FN=0;
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

        
        System.out.println("TN :" + " " + TN );
        System.out.println("FN :" + " " + FN );
        System.out.println("TP :" + " " + TP );
        System.out.println("FP :" + " " + FP );

        
        precision(TN, FN, TP, FP);
        recall(TN, FN, TP, FP);
        fMeasure(TN, FN, TP, FP);
        accuracy(TN, FN, TP, FP);
        
    }
    

    private void precision(int TN, int FN, int TP, int FP) {
        precision = (double)TP/(TP+FP);
    }
    
    public double getPrecision(){
        return precision;
    }

    private void recall(int TN, int FN, int TP, int FP) {
        recall = (double)TP/(TP+FN);
    }
    
    public double getRecall(){
        return recall;
    }

    private void fMeasure(int TN, int FN, int TP, int FP) {
        fMeasure = (double)2 * ((getPrecision()* getRecall())/(getPrecision()+ getRecall()));
    }
    
    public double getFMeasure(){
        return fMeasure;
    }

    private void accuracy(int TN, int FN, int TP, int FP) {
        accuracy = (double)(TP+TN)/(TP+TN+FP+FN);
    }
    
    public double getAccuracy(){
        return accuracy;
    }
    
    public int getFalsePositive(){
        return FP;
    }
    
    public int getTruePositive(){
        return TP;
    }
    
    public int getTrueNegative(){
        return TN;
    }
    
    public int getFalseNegative(){
        return FN;
    }
}
