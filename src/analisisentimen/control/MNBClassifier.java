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
import static java.util.Collections.max;
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
    private double[] priorProb;
    private double[][] conditionalProb;
    private Weighting bobotan;
    

    public MNBClassifier(List<Tweet> testingSet, double[] priorProbability, double[][] conditionalProbability,Weighting bobot) {
        tweetList = testingSet;
        priorProb = priorProbability;
        conditionalProb = conditionalProbability;
        bobotan = bobot;
    }


    public void prepareToClassifyWithPOS(){
        try{
            
            for(int i=0; i< tweetList.size();i++) {
                Tweet tweet = tweetList.get(i);
                
                praproses.PraWithPOSTag(tweet);

                tweet.setTermlist(praproses.getCurrentTokenList());

            }
            
        }catch(FileNotFoundException ex){
            
        }catch(IOException ex){
            
        }
    }
    
    public void prepareToClassify(){
        try{
            
            for(int i=0; i< tweetList.size();i++) {
                Tweet tweet = tweetList.get(i);
                
                praproses.PraWithoutPOSTag(tweet);

                tweet.setTermlist(praproses.getCurrentTokenList());

            }

        }catch(FileNotFoundException ex){
            
        }catch(IOException ex){
            
        }
    }
    
    public int[] classifyFull(){
        //mnbProb = new MNBProbabilistik(bobotan);
        double data[][] = new double[tweetList.size()][priorProb.length];    
        double result;

        for (int i = 0; i < tweetList.size(); i++) {
            for (int j = 0; j < tweetList.get(i).getTermList().getTotalTerm(); j++) {
            
                for (int k = 0; k < priorProb.length; k++) {
                    result = Math.log10(priorProb[k]) + tweetList.get(i).getTermList().getTotalTerm() *
                            (numberOfConditionalProb(tweetList.get(i).getTermList().getTermAt(j).getTerm(), k));
                    data[i][k] = result;
                }
            }
        }
        
        return argMax(data);
    }
    
    
    private double numberOfConditionalProb(String term, int i){
        double result=0;

        for (int k = 0; k < conditionalProb[0].length; k++) {
            if(term.equals(bobotan.getGlobalTermList().getTermAt(k).getTerm())){
                result += Math.log10(conditionalProb[i][k]);
                    
            }
        }
        
        return result;
    }
    
    private int[] argMax(double[][] data){
        int outClass[] = new int[data.length];
        
        int index_max = 0;
        for (int i = 0; i < data.length; i++) {
            double max = Integer.MIN_VALUE;
            for (int j = 0; j < data[0].length; j++) {
                if(data[i][j] > max) {
                    max = data[i][j];
                    index_max = j;
                }
                    
            }
            outClass[i] = index_max; 
            
        }
        return outClass;
    }
    
    public List<Tweet> getTweetList(){
        return tweetList;
    }
    
    
//    public void cetak(){
//        double[][] data = classifyFull();
//        for (int i = 0; i < data.length; i++) {
//            for (int j = 0; j < data[0].length; j++) {
//                System.out.println(data[i][j]);
//            }
//            System.out.println();
//        }
//        
//    }
    
 
}
