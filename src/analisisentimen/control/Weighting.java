/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Tweet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import analisisentimen.entity.TermList;
import analisisentimen.control.Praprocess;
import analisisentimen.entity.Term;
import static java.lang.Integer.sum;
import java.util.Arrays;
import java.util.stream.DoubleStream;


/**
 *
 * @author Asus
 */
public class Weighting {
    private final List<Tweet> trainingSet;
    private TermList globaltermlist;
    private double[][] resultOfWeighting;
    private Praprocess praprocess = new Praprocess();
    
    
    public Weighting(List<Tweet> trainingSetP) throws IOException{
        trainingSet = trainingSetP;
    }

    public void prepareCountWeight() throws IOException {

        for(int i=0; i< trainingSet.size();i++) {
            Tweet tweet = trainingSet.get(i);
                
            praprocess.PraWithoutPOSTag(tweet);

            tweet.setTermlist(praprocess.getCurrentTokenList());

        }
        globaltermlist = praprocess.getTokenList();

    }
    
    public void prepareCountWeightPOS() throws IOException {

        for(int i=0; i< trainingSet.size();i++) {
            Tweet tweet = trainingSet.get(i);
                
            praprocess.PraWithPOSTag(tweet);

            tweet.setTermlist(praprocess.getCurrentTokenList());

        }
        globaltermlist = praprocess.getTokenList();

    }
    
    //    Transpose matriks double biasa
    private double[][] transpose(double[][] data){
        double[][] transposedMatrix = new double[data[0].length][data.length];
        for(int rows = 0; rows < data.length; rows++){
            for(int cols = 0; cols < data[0].length; cols++){
                transposedMatrix[cols][rows] = data[rows][cols];
            }
        }

        return transposedMatrix;
    }
    
    public void doWeighting(){

        resultOfWeighting = new double[globaltermlist.getTotalTerm()][trainingSet.size()];
        for(int i=0; i<trainingSet.size(); i++){
            String tweets[] = trainingSet.get(i).getTermList().toStringArray(); 
            
            for(int j=0; j<resultOfWeighting.length; j++){
                
                resultOfWeighting[j][i] = tf(tweets, globaltermlist.getTermAt(j).getTerm()) * 
                        idf(trainingSet, globaltermlist.getTermAt(j).getTerm());

            }

        }

    }
    
    public void doWeightingPOS(){

        resultOfWeighting = new double[globaltermlist.getTotalTerm()][trainingSet.size()];
        
        for(int i=0; i<trainingSet.size(); i++){
            String tweets[] = trainingSet.get(i).getTermList().toStringArray(); 
            double value = 0;
            
            for (int k = 0; k < resultOfWeighting.length; k++) {
                value += tfPOS(tweets, globaltermlist.getTermAt(k).getTerm());
            }
                        
            for(int j=0; j<resultOfWeighting.length; j++){
                
                resultOfWeighting[j][i] = tfPOS(tweets, globaltermlist.getTermAt(j).getTerm())/value * 
                        idf(trainingSet, globaltermlist.getTermAt(j).getTerm());
                
            }

        }
        
    }
    
    public double[][] getResultOfWeighting(){
        return resultOfWeighting;
    }
    
    public double[][] getResultOfTransposeBobot(){
        return transpose(getResultOfWeighting());
    }
    
    public List<Tweet> getTweetList(){
        return trainingSet;
    }
    
    public TermList getGlobalTermList(){
        return globaltermlist;
    }
    
    private static double tf(String[] tweet, String term){
        double n = 0;
        double tf;
        for(String s: tweet){
            if(s.equalsIgnoreCase(term)){
                n++;
            }
        }
        
        if(n>0){
            tf = n/tweet.length;
        } else {
            tf = n;
        }
        
        //System.out.println(tf + "/" + tweet.length + "=" + tf/tweet.length);
        //return n/tweet.length;
        return tf;
    }
    
    private static double tfPOS(String[] tweet, String term){
        double n = 0; double tfPOS;
        double w=0;
        double isi [] = new double[tweet.length];
        for(String s: tweet){
            if(s.equalsIgnoreCase(term)){
                n++;
            }
        }
        
        if(term.contains("JJ")){
            w = 4;            
        } else if(term.contains("RB")){
            w = 1;
        } else if(term.contains("VB")){
            w = 3;  
        }  else if(term.contains("NN")){
            w = 2;  
        }
        
        tfPOS = n * w;
        
        return tfPOS;
    }
    
    private static double idf(List<Tweet> tweetList, String term){
        double n = 0; double idf;
        for(int i=0; i<tweetList.size(); i++){
            for(int j=0; j<tweetList.get(i).getTermList().getTotalTerm(); j++){
                String s = tweetList.get(i).getTermList().getTermAt(j).getTerm();
                if(s.equalsIgnoreCase(term)){
                    n++;
                    break;
                }
            }
        }
//        System.out.println("df = "+n);
//        System.out.println("idf = "+Math.log10(2));
        idf = Math.log10(tweetList.size()/n);
        return idf;
    }
    
    public int numberOfDataWithClass(int classes) {
        int numberOfDataWithClass = 0;
        for (int i = 0; i < trainingSet.size(); i++) {
            if(trainingSet.get(i).getClassSentiment() == classes ) {
                 numberOfDataWithClass++;   
            }
        }
        
        return numberOfDataWithClass;
    }
    
    public int numberOfTermWeightWithClass(int classes){
        int numberOfTermWeightWithClass = 0;
        List<Integer> temp = new ArrayList<Integer>();
        
        for (int i = 0; i < trainingSet.size(); i++) {
            
            if(trainingSet.get(i).getClassSentiment() == classes ) {
                
                numberOfTermWeightWithClass += trainingSet.get(i).getTermList().getTotalTerm();
            }       
        }
        
        return numberOfTermWeightWithClass;
    }
    
    public double numberOfAllWeightWithClass(int classes){
        
        double[][] data = new double[getResultOfTransposeBobot().length][getResultOfTransposeBobot()[0].length];
        data = getResultOfTransposeBobot();
        double numberOfWeightWithClass = 0;

        for(int i=0; i<data.length; i++){
            if(trainingSet.get(i).getClassSentiment() == classes ){
                for(int j=0; j<data[0].length; j++){
                    
                    numberOfWeightWithClass += data[i][j];
                }
            }
        }
        
        return numberOfWeightWithClass;
    }
    
    public double numberOfWeightWithClassInData(int classes, Term term) {
        double[][] data = new double[getResultOfTransposeBobot().length][getResultOfTransposeBobot()[0].length];
        data = getResultOfTransposeBobot();
        double numberOfWeightWithClassInData = 0;
        
        for (int i = 0; i < data.length; i++) {
            if(trainingSet.get(i).getClassSentiment() == classes ){
                for(int j=0; j<data[0].length; j++){
                    if(getGlobalTermList().getTermAt(i).equals(term)){
                        numberOfWeightWithClassInData += data[i][j];
                    }
                }
            }
        }
        
        return numberOfWeightWithClassInData;
        
    }


}
