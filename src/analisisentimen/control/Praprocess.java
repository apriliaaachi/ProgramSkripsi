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


/**
 *
 * @author Asus
 */
public class Praprocess {
    
    TermList listToken;
    TermList listTokenCurrent;
    Sentence sentence;
    List<Tweet>tweetList;
    List<Sentence> sentenceList = new ArrayList<Sentence>();
    

	
    public Praprocess() {
	//Corpus corpus = new Corpus();
        listToken = new TermList();
	//corpus.readStopWord();
    }

    public void setTokenList(TermList tokenList) {
	listToken = tokenList;
    }
    
    public List<Sentence> getSentenceList() {
        return sentenceList;
    }
    
    public void PraWithoutPOSTag(Tweet tweetList) throws IOException {
        
        Normalization normalisasi = new Normalization();    
        String[] r = normalisasi.removePunctuation(tweetList.getContentTweet().toLowerCase()).split("\\s+");
        String[] u = normalisasi.removeUniqueCharacter(r);
        String[] n = normalisasi.dictionaryFormalize(u);
        String[] s = Stemming(n);

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
        Normalization normalisasi = new Normalization(); 
        Viterbi viterbi = new Viterbi();
        String[] r = normalisasi.removePunctuation(tweetList.getContentTweet().toLowerCase()).split("\\s+");
        String[] u = normalisasi.removeUniqueCharacter(r);
        String[] n = normalisasi.dictionaryFormalize(u);       
        viterbi.prepareCountViterbi(n); 
        viterbi.posTagger();
        
        System.out.println("test sentence words: " + viterbi.getListWord()+viterbi.getListWord().size());
        System.out.println("vibertiPath        : " + viterbi.getListTag());
        System.out.println("\n");
        

        Stemming stem = new Stemming();
        String[] st = new String[viterbi.getListWord().size()];
        int i,j,k;
        
        for (i=0; i<viterbi.getListWord().size(); i++){
            
            st[i] = stem.Stem(viterbi.getListWord().get(i).toString());
            //System.out.println(st[i]);
        }
        
        String[] tag = new String[viterbi.getListTag().size()+10];
        
        for(j=0; j<viterbi.getListTag().size(); j++){
            tag[j] = stem.Stem(viterbi.getListTag().get(j).toString());
            //System.out.println(tag[j]);
            
        }
        
        
        String[] combine = new String[st.length];
        for (k=0; k<st.length; k++){
            combine[k] = st[k] + "-" + tag[k];
            //System.out.println(combine[k]);
        }


	listTokenCurrent = new TermList();

        for(int l=0; l<combine.length; l++){
            if(combine[l].length()>1) {
		if((combine[l].contains("NN")&&!combine[l].contains("NNP"))||
                   combine[l].contains("RB")||
                   combine[l].contains("NEG")||
                   combine[l].contains("VB")||
                   combine[l].contains("JJ")) {
                    
                    //combine[l] = combine[l];
                    listTokenCurrent.addTerm(new Term(combine[l]));
                    Term tkn = listToken.checkTerm(combine[l]);
                    if(tkn==null) {
			listToken.addTerm(new Term(combine[l]));
                    }
		}
            }
        }
    }

    public String[] Stemming (String[] token){
        Stemming stem = new Stemming();
        
        String hasilStem = "";
        String[] resultS;
        for(int i=0; i<token.length; i++){
            hasilStem += stem.Stem(token[i])+ "\n";
        }
        resultS = hasilStem.split("\n");
        return resultS;
    }
    
    public boolean findStopWord(String str) {
        List<String>listStopword = new ArrayList<String>();
        Corpus corpus = new Corpus();
        
        listStopword = corpus.readStopWord();
        
        boolean ada=false;
        for(int i=0; i<listStopword.size(); i++) {
            if(listStopword.get(i).equals(str)) {
                ada=true;
                break;
            }
        }
//      System.out.println(ada);
	return ada;
    }
    
    public TermList getCurrentTokenList() {
	return listTokenCurrent;
    }

    public TermList getTokenList() {
	return listToken;
    }
    
    
    
        
}
