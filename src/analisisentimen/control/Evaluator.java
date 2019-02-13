/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Tweet;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public final class Evaluator {
    double fMeasure, precision, accuracy, recall;
    int TP, FP, TN, FN;
    
    
    public Evaluator(int[] classify, MNBClassifier mnbc) {
        TP=0; FP=0; TN=0; FN=0;
        //System.out.println(mnbc.classifyFull());
        
        
        for (int i = 0; i < classify.length; i++) {

            if(classify[i] == 0) {
                if(classify[i] == mnbc.getTestingSet().get(i).getClassSentiment()) {
                    TN++;
                } else {
                    FN++;
                }
            } else if(classify[i] == 1) {
                if(classify[i] == mnbc.getTestingSet().get(i).getClassSentiment()) {
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
        return round(precision, 4);
    }

    private void recall(int TN, int FN, int TP, int FP) {
        recall = (double)TP/(TP+FN);
    }
    
    public double getRecall(){
        return round(recall, 4);
    }

    private void fMeasure(int TN, int FN, int TP, int FP) {
        fMeasure = (double)2 * ((getPrecision()* getRecall())/(getPrecision()+ getRecall()));
    }
    
    public double getFMeasure(){
        return round(fMeasure, 4);
    }

    private void accuracy(int TN, int FN, int TP, int FP) {
        accuracy = (double)(TP+TN)/(TP+TN+FP+FN);
    }
    
    public double getAccuracy(){
        return round(accuracy, 4);
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
    
    public double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
}
