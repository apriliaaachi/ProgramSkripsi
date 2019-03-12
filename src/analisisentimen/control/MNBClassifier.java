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
    private final List<Tweet> testingSet;
    private DocumentReader dr;
    private TermList globaltermlist;
    private double[][] hasilPembobotan;
    private final Praprocess praproses = new Praprocess();
    private final double[] priorProb;
    private final double[][] conditionalProb;
    private final Weighting weight;
    

    public MNBClassifier(List<Tweet> testingSetC, double[] priorProbability, double[][] conditionalProbability, Weighting weighting) {
        testingSet = testingSetC;
        priorProb = priorProbability;
        conditionalProb = conditionalProbability;
        weight = weighting;
    }


    public void prepareToClassifyWithPOS(){
        try{
            
            for(int i=0; i< testingSet.size();i++) {
                Tweet tweet = testingSet.get(i);
                
                praproses.PraWithPOSTag(tweet);

                tweet.setTermlist(praproses.getCurrentTokenList());

            }
            
        }catch(FileNotFoundException ex){
            
        }catch(IOException ex){
            
        }
    }
    
    public void prepareToClassify(){
        try{
            
            for(int i=0; i< testingSet.size();i++) {
                Tweet tweet = testingSet.get(i);
                
                praproses.PraWithoutPOSTag(tweet);

                tweet.setTermlist(praproses.getCurrentTokenList());
                

            }

        }catch(FileNotFoundException ex){
            
        }catch(IOException ex){
            
        }
    }
    
    public int[] classifyFull(){
        
        double data[][] = new double[testingSet.size()][priorProb.length];    
        double result;
        

        for (int i = 0; i < testingSet.size(); i++) {
            for (int j = 0; j < testingSet.get(i).getTermList().getTotalTerm(); j++) {
            
                for (int k = 0; k < priorProb.length; k++) {
                    result = Math.log10(priorProb[k]) + testingSet.get(i).getTermList().getTotalTerm() *
                            (numberOfConditionalProb(testingSet.get(i).getTermList().getTermAt(j).getTerm(), k));
                    data[i][k] = result;
                }
            }
        }
        
       
        
        return argMax(data);
    }
    
    public double[][] classifyFul(){
        
        double data[][] = new double[testingSet.size()][priorProb.length];    
        double result;

        for (int i = 0; i < testingSet.size(); i++) {
            for (int j = 0; j < testingSet.get(i).getTermList().getTotalTerm(); j++) {
            
                for (int k = 0; k < priorProb.length; k++) {
                    result = Math.log10(priorProb[k]) + testingSet.get(i).getTermList().getTotalTerm() *
                            (numberOfConditionalProb(testingSet.get(i).getTermList().getTermAt(j).getTerm(), k));
                    data[i][k] = result;
                    
                }
            }
        }
        
        return data;
    }
    
    private double numberOfConditionalProb(String term, int i){
        double result=0;

        for (int k = 0; k < conditionalProb[0].length; k++) {
            if(term.equals(weight.getGlobalTermList().getTermAt(k).getTerm())){
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
    
    public List<Tweet> getTestingSet(){
        return testingSet;
    }
    
   
    
 
}
