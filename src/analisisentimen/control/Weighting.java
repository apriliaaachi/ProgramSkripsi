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
    private List<Tweet> twList;
    private DocumentReader dr;
    private TermList globaltermlist;
    private double[][] hasilPembobotan;
    Praprocess praproses = new Praprocess();
    
    public Weighting(List<Tweet> tweetList) throws IOException{
        twList = tweetList;
        prepareCountWeight();
    }

    private void prepareCountWeight() throws IOException {
        
        try{
            
            for(int i=0; i< twList.size();i++) {
                Tweet tweet = twList.get(i);
                
                praproses.PraWithoutPOSTag(tweet);

                tweet.setTermlist(praproses.getCurrentTokenList());

            }
            globaltermlist = praproses.getTokenList();
            System.out.println(globaltermlist);
            

        }catch(FileNotFoundException ex){
            
        }catch(IOException ex){
            
        }
    }
    
    //    Transpose matriks double biasa
    public double[][] transpose(double[][] data){
        //System.out.println("--------------transpose----------------");
        double[][] transposedMatrix = new double[data[0].length][data.length];
        for(int rows = 0; rows < data.length; rows++){
            for(int cols = 0; cols < data[0].length; cols++){
                transposedMatrix[cols][rows] = data[rows][cols];
            }
        }
        for(double[] i:transposedMatrix){//2D arrays are arrays of arrays
            //System.out.println(Arrays.toString(i));
        }
        return transposedMatrix;
    }
    
    public void doPembobotan(){

        hasilPembobotan = new double[globaltermlist.getTotalTerm()][twList.size()];
        for(int i=0; i<twList.size(); i++){
            String sdocs[] = twList.get(i).getTermList().toStringArray(); 
//            System.out.println("Data" + "ke :" + " " +i);
//            System.out.println(twList.get(i).getContentTweet() + " : " + Arrays.toString(twList.get(i).getTermList().toStringArray()));
            for(int j=0; j<hasilPembobotan.length; j++){
                hasilPembobotan[j][i] = tf(sdocs, globaltermlist.getTermAt(j).getTerm()) * 
                        idf(twList, globaltermlist.getTermAt(j).getTerm());
                
                
//                ------------------------Print out pembobotan-----------------------------
                
//             System.out.print(globaltermlist.getTermAt(j).getTerm() + ": ");
//                System.out.print(tf(sdocs, globaltermlist.getTermAt(j).getTerm()) + " * " 
//                        + idf(tweetList, globaltermlist.getTermAt(j).getTerm()) + " = ");
//                System.out.println(hasilPembobotan[j][i]);
                
                
//--------------------------------------------------------------------------

            }
//            System.out.print("\n");
        }
//        System.out.println("-------------Pembobotan selesai--------------");
//            for(int i=0; i<globaltermlist.getTotalTerm(); i++){
//                System.out.println(globaltermlist.getTermAt(i).getTerm());
//            }
    }
    
    
    public double[][] getHasilPembobotan(){
        return hasilPembobotan;
    }
    
    public double[][] getHasilTransposeBobot(){
        return transpose(getHasilPembobotan());
    }
    
    public List<Tweet> getTweetList(){
        return twList;
    }
    
    public TermList getGlobalTermList(){
        return globaltermlist;
    }
    
    static double tf(String[] tweet, String term){
        double n = 0;
        for(String s: tweet){
            if(s.equalsIgnoreCase(term)){
                n++;
            }
        }
//        return n;
        return n/tweet.length;
    }
    
    static double idf(List<Tweet> tweetList, String term){
        double n = 0;
//        System.out.println("n = "+listdoc.size());
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
        return Math.log10(tweetList.size()/n);
    }
    
    public int numberOfDataWithClass(int classes) {
        int numberOfDataWithClass = 0;
        for (int i = 0; i < twList.size(); i++) {
            if(twList.get(i).getClassSentiment() == classes ) {
                 numberOfDataWithClass++;   
            }
        }
        
        return numberOfDataWithClass;
    }
    
    public int numberOfTermWeightWithClass(int classes){
        int numberOfTermWeightWithClass = 0;
        List<Integer> temp = new ArrayList<Integer>();
        
        
        for (int i = 0; i < twList.size(); i++) {
            
            if(twList.get(i).getClassSentiment() == classes ) {
                
                numberOfTermWeightWithClass += twList.get(i).getTermList().getTotalTerm();
                //System.out.println(twList.get(temp.get(j)).getTermList().getTotalTerm());
            }       
        }
        
        return numberOfTermWeightWithClass;
    }
    
    public double numberOfAllWeightWithClass(int classes){
        
        double[][] data = new double[getHasilTransposeBobot().length][getHasilTransposeBobot()[0].length];
        data = getHasilTransposeBobot();
        double numberOfWeightWithClass = 0;
        int[][] temp = new int[numberOfDataWithClass(classes)][getHasilTransposeBobot()[0].length];
        
        //System.out.println("===============================================");

        for(int i=0; i<data.length; i++){
            if(twList.get(i).getClassSentiment() == classes ){
                for(int j=0; j<data[0].length; j++){
                    
                    numberOfWeightWithClass += data[i][j];
                }
            }
        }
        
        return numberOfWeightWithClass;
    }
    
    public double numberOfWeightWithClassInData(int classes, Term term) {
        double[][] data = new double[getHasilTransposeBobot().length][getHasilTransposeBobot()[0].length];
        data = getHasilTransposeBobot();
        double numberOfWeightWithClassInData = 0;
        
        for (int i = 0; i < data.length; i++) {
            if(twList.get(i).getClassSentiment() == classes ){
                for(int j=0; j<data[0].length; j++){
                    if(getGlobalTermList().getTermAt(i).equals(term)){
                        numberOfWeightWithClassInData += data[i][j];
                    }
                }
            }
        }
        
        return 0;
        
    }


}
