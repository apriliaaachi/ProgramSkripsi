/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 *
 * @author Asus
 */
public class Normalization {
    private DocumentReader dr;
    
    public Normalization() {
        
    }
    
    public String[] removeUniqueCharacter(String[] token){
          
          List<String> list = new ArrayList<String>(Arrays.asList(token));
          List<Integer> temp = new ArrayList<Integer>();
          int index;
          
          int i=0;
          for(i=0; i<list.size(); i++){
              if(list.get(i).contains("#")||
                 list.get(i).contains("@")||
                 list.get(i).contains("http")||
                 list.get(i).contains("https")||
                 list.get(i).contains("www")||
                 list.get(i).contains("com")) {
                  
                 temp.add(i);
                 
              }

          } 
            
        int k;
        for(k=temp.size()-1;k>=0;k--){
            index = temp.get(k);
            list.remove(index);
        }
        
        token = list.toArray(new String[0]);
          
        return token;
    }
    
    
    public String[] dictionaryFormalize(String[] token) throws IOException {
        
        Corpus corpus = new Corpus();
        String[][] kamusFormal = corpus.readKamusFormal();
        
        for(int i=0;i<token.length;i++){
            for(int j=0;j<kamusFormal.length;j++){
                if(token[i].equals(kamusFormal[j][0])){

                    token[i] = token[i].toLowerCase().replaceAll(token[i], kamusFormal[j][1]);
                }
            }
        }

        return token;
    }
    
    public String removePunctuation(String tweet){
        // Filter Punctuation
        String result = tweet;
        String P = "[!\"$%&'()*\\+,./;<=>?\\[\\]^~_\\`{|}…0987654321]";
        return result.replaceAll(P, "");
    }

    
    
}
    

