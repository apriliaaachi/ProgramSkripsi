/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import analisisentimen.entity.Sentence;
import analisisentimen.entity.Tag;
import analisisentimen.entity.Word;
import analisisentimen.entity.Tweet;

/**
 *
 * @author Asus
 */
public class DocumentReader {
    
    private List<Tweet> tweetList = new ArrayList<Tweet>();
    private String filePath;

    
    public DocumentReader(String filePath){
        this.filePath = filePath;
    }
    
    public void readTweetSet() throws FileNotFoundException, IOException {     
        tweetList = new ArrayList<>();
        File file = new File(filePath);
        System.out.println(filePath);
        
        
        if (file != null){
            BufferedReader in = null;

            in = new BufferedReader(new FileReader(file));
            
            String contentTweet = new String();
            int classSentiment = 0;
                   
            String s = null;
            while((s = in.readLine()) != null){
                contentTweet = s.substring(2, s.length());
                classSentiment = Character.getNumericValue(s.charAt(0)); 
                
                
                Tweet myTweet = new Tweet(contentTweet, classSentiment);                   
                tweetList.add(myTweet);
                classSentiment = 0;
                
            }
//            for(int i=0; i< tweetList.size();i++) {
//                System.out.println("index ke-" + i + ":" + tweetList.get(i).getClassSentiment() + " " + tweetList.get(i).getContentTweet());
////                
//            }
            in.close();
        }       
    }
    
    public List<Tweet> getTweetList(){
        return tweetList;
    }

}
