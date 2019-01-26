/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

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
public class WeightingPOS {
    private List<Tweet> tweetList;
    private final DocumentReader dr;
    private TermList globaltermlist;
    private double[][] hasilPembobotan;
    Praprocess praproses = new Praprocess();
    
    public WeightingPOS(String filePath) throws IOException{
        dr = new DocumentReader(filePath);
        prepareCountWeight();
    }

    private void prepareCountWeight() throws IOException {
        
        try{
            dr.readTweetSet();
            tweetList = dr.getTweetList();
            


            for(int i=0; i< tweetList.size();i++) {
                Tweet tweet = tweetList.get(i);
                
                praproses.PraWithPOSTag(tweet);

                tweet.setTermlist(praproses.getCurrentTokenList());

            }
            globaltermlist = praproses.getTokenList();
            System.out.println(globaltermlist);
            

        }catch(FileNotFoundException ex){
            
        }catch(IOException ex){
            
        }
    }
    
    public void doPembobotan(){

        hasilPembobotan = new double[globaltermlist.getTotalTerm()][tweetList.size()];
        for(int i=0; i<tweetList.size(); i++){
            String sdocs[] = tweetList.get(i).getTermList().toStringArray();
            System.out.println(tweetList.get(i).getContentTweet() + " : " + Arrays.toString(tweetList.get(i).getTermList().toStringArray()));
            
            int w=0;
            
            for(int j=0; j<hasilPembobotan.length; j++){
                
                hasilPembobotan[j][i] = tf(sdocs, globaltermlist.getTermAt(j).getTerm()) * 
                        idf(tweetList, globaltermlist.getTermAt(j).getTerm());
                
//                ------------------------Print out pembobotan-----------------------------
                
             System.out.print(globaltermlist.getTermAt(j).getTerm() + ": ");
                System.out.print(tf(sdocs, globaltermlist.getTermAt(j).getTerm()) + " * " 
                        + idf(tweetList, globaltermlist.getTermAt(j).getTerm()) + " = ");
                System.out.println(hasilPembobotan[j][i]);
                
//--------------------------------------------------------------------------

            }
            System.out.print("\n");
        }
        System.out.println("-------------Pembobotan selesai--------------");
//            for(int i=0; i<globaltermlist.getTotalTerm(); i++){
//                System.out.println(globaltermlist.getTermAt(i).getTerm());
//            }
    }
    
    
    public double[][] getHasilPembobotan(){
        return hasilPembobotan;
    }
    
    public List<Tweet> getTweetList(){
        return tweetList;
    }
    
    public TermList getGlobalTermList(){
        return globaltermlist;
    }
    
    static double tf(String[] tweet, String term){
        double n = 0; double total;
        int w=0;
        double isi [] = new double[tweet.length];
        for(String s: tweet){
            if(s.equalsIgnoreCase(term)){
                n++;
            }
        }
        
        if(term.contains("JJ")){
            w = 5;            
        } else if(term.contains("RB")){
            w = 4;
        } else if(term.contains("VB")){
            w = 3;  
        } else if(term.contains("NEG")){
            w = 2;  
        } else if(term.contains("NN")){
            w = 1;  
        }
        
        total = n * w;
        
        return total;
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
}
