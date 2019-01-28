/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Term;
import analisisentimen.entity.TermList;
import analisisentimen.entity.Tweet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Asus
 */
public class MNBClassifier {
    private List<Tweet> tweetList;
    private DocumentReader dr;
    private TermList globaltermlist;
    private double[][] hasilPembobotan;
    private Praprocess praproses = new Praprocess();
    private MNBProbabilistik mnbProb;
    private Weighting bobotan;
    

    public MNBClassifier(List<Tweet> testingSet, Weighting bobot) {
        tweetList = testingSet;
        //prepareToClassification();
        bobotan = bobot;
    }

    public MNBClassifier() {
        
    }
    
    public void prepareToClassification(){
        try{
            
            for(int i=0; i< tweetList.size();i++) {
                Tweet tweet = tweetList.get(i);
                
                praproses.PraWithoutPOSTag(tweet);

                tweet.setTermlist(praproses.getCurrentTokenList());

            }
//            globaltermlist = praproses.getTokenList();
//            System.out.println(globaltermlist);
            

        }catch(FileNotFoundException ex){
            
        }catch(IOException ex){
            
        }
    }
    
    public double[][] classifyFull(){
        mnbProb = new MNBProbabilistik(bobotan);
        double label[][] = new double[tweetList.size()][mnbProb.calculatePriorProbability().length];    
        double result;

        for (int i = 0; i < tweetList.size(); i++) {
            for (int j = 0; j < tweetList.get(i).getTermList().getTotalTerm(); j++) {
                for (int k = 0; k < mnbProb.calculatePriorProbability().length; k++) {
                    result = mnbProb.calculatePriorProbability()[k] + tweetList.get(i).getTermList().getTotalTerm() *
                            (numberOfConditionalProb(tweetList.get(i).getTermList().getTermAt(j).getTerm(), k));
                    label[i][k] = result;
                }
            }
        }
        
        return label;
    }
    
    
    private double numberOfConditionalProb(String term, int i){
        double result=0;
        double conditional[][] = mnbProb.calculateConditionalProbability();
        //for (int i = 0; i < conditional.length; i++) {
            for (int k = 0; k < conditional[0].length; k++) {
                if(term.equals(bobotan.getGlobalTermList().getTermAt(k).getTerm())){
                    result += conditional[i][k];
                    
                }
            }
        //}
        
        return result;
    }
    
    public List<Tweet> getTweetList(){
        return tweetList;
    }
    
    public void cetak(){
        double[][] data = classifyFull();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.println(data[i][j]);
            }
            System.out.println();
        }
    }
    
    
    
}
