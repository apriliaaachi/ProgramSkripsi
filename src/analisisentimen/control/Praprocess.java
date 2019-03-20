/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Sentence;
import analisisentimen.entity.Tag;
import analisisentimen.entity.Term;
import analisisentimen.entity.TermList;
import analisisentimen.entity.Tweet;
import analisisentimen.entity.Word;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author Asus
 */
public class Praprocess {
    
    private TermList listToken;
    private TermList listTokenCurrent;
    private Sentence sentence;
    private List<Tweet> tweetList;
    private List<Sentence> sentenceList = new ArrayList<>();
    
    
    public Praprocess() {
        listToken = new TermList();
    }
    
    public void PraWithoutPOSTag(Tweet tweetList) throws IOException {
        String[] p = removePunctuation(tweetList.getContentTweet().toLowerCase()).split("\\s+");
        String[] u = removeUniqueCharacter(p);
        String[] n = dictionaryFormalize(u);
        String[] s = stemming1(n);
        
	listTokenCurrent = new TermList();

        for(int i=0; i<s.length; i++){
            if(s[i].length()>1) {
		if(!findStopWord(s[i])) {
                    s[i] = s[i].toLowerCase();
                    listTokenCurrent.addTerm(new Term(s[i]));
                    
                    Term token = listToken.checkTerm(s[i]);
                    if(token==null) {
			listToken.addTerm(new Term(s[i]));
                    }
		}
            }
        }
    }
    

    public void PraWithPOSTag (Tweet tweetList) throws IOException{
        Viterbi viterbi = new Viterbi();
        String[] p = removePunctuation(tweetList.getContentTweet().toLowerCase()).split("\\s+");
        String[] u = removeUniqueCharacter(p);
        String[] n = dictionaryFormalize(u); 
        
        viterbi.prepareCountViterbi(n);
        viterbi.posTagger();
        String[] s = stemming2(viterbi.getListWord(), viterbi.getListTag());
       
//        cetak(s);
        
	listTokenCurrent = new TermList();

        for(int i=0; i<s.length; i++){
            if(s[i].length()>1) {
                if(!findStopWord(s[i])) {
                    if((s[i].contains("NN")&&!s[i].contains("NNP")&&!s[i].contains("NND"))||
                        s[i].contains("RB")||
                        s[i].contains("VB")||
                        s[i].contains("JJ")) {

                        listTokenCurrent.addTerm(new Term(s[i]));
                        Term token = listToken.checkTerm(s[i]);
                        if(token==null) {
                            listToken.addTerm(new Term(s[i]));
                        }
                    }
                }
                	
            }
        }
    }
    
    private String[] removeUniqueCharacter(String[] token){
          
          List<String> list = new ArrayList<String>(Arrays.asList(token));
          List<Integer> temp = new ArrayList<Integer>();
          int index;
          
          for(int i=0; i<list.size(); i++){
              if(list.get(i).contains("#")||
                 list.get(i).contains("@")||
                 list.get(i).contains("http")||
                 list.get(i).contains("https")||
                 list.get(i).contains("www")||
                 list.get(i).contains("cc")||
                 list.get(i).contains("via")||
                 list.get(i).contains("cont")||
                 list.get(i).contains("com")) {
                  
                 temp.add(i);
                 
              }

          } 
            
        for(int k=temp.size()-1;k>=0;k--){
            index = temp.get(k);
            list.remove(index);

        }
        
        token = list.toArray(new String[0]);
          
        return token;
    }
    
    
    private String[] dictionaryFormalize(String[] token) throws IOException {
        
        DocumentReader dr = new DocumentReader();
        String[][] kamusFormal = dr.readKamusFormal();
        
        for(int i=0;i<token.length;i++){
            for(int j=0;j<kamusFormal.length;j++){
                if(token[i].equals(kamusFormal[j][0])){

                    token[i] = token[i].toLowerCase().replaceAll(token[i], kamusFormal[j][1]);
                }
            }
        }

        return token;
    }
    
    private String removePunctuation(String tweet){
        String P = "[!\"$%&'()*\\+,.;:/<=>?\\[\\]^~_\\`{|}…0987654321]";
        return tweet.replaceAll(P, "");
    }

    private String[] stemming1 (String[] token) throws IOException{
        Stemming stem = new Stemming();
        
        String st = "";
        String[] result;
        for(int i=0; i<token.length; i++){
            st += stem.kataDasar(token[i])+ "\n";
        }
        result = st.split("\n");
        return result;

    }
    
    private String[] stemming2 (List<Word> token, List<Tag> tag){
        Stemming stem = new Stemming();
        
        String[] st = new String[token.size()];
        String[] result = new String[st.length];
        
        for (int i=0; i<token.size(); i++){
            
            st[i] = stem.kataDasar(token.get(i).toString());
            result[i] = st[i] + "/" + tag.get(i);
            //System.out.println(combine[i]);
        }
        
        return result;
    }
    
    public boolean findStopWord(String str) {
        List<String>listStopword = new ArrayList<String>();
        DocumentReader dr = new DocumentReader();
        
        listStopword = dr.readStopWord();
        
        boolean ada=false;
        for(int i=0; i<listStopword.size(); i++) {
            if(listStopword.get(i).contains(str)) {
                ada=true;
                break;
            }
            
        }
        //System.out.println(ada);
	return ada;
    }
    
    public TermList getCurrentTokenList() {
	return listTokenCurrent;
    }

    public TermList getTokenList() {
	return listToken;
    }
    
    public void cetak(String[] token){
        String result = "";
        
        for(int i=0; i<token.length; i++){
            result += token[i]+ " ";
        }
        
        System.out.println(result);
    }
    
        
}
