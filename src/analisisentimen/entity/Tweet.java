/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.entity;

/**
 *
 * @author Asus
 */
public class Tweet {
    private String contentTweet;
    private TermList termList;
    private int classSentiment;
    
    
    
    public Tweet(String contentTweet, int classSentiment){
        this.contentTweet = contentTweet;
        this.classSentiment = classSentiment;
    }
    
    public String getContentTweet(){
        return contentTweet;
    }
    
    public void setTermlist(TermList termlist){
        this.termList = termlist;
    }
    
    public TermList getTermList(){
        return termList;
    }
    
    public int getClassSentiment(){
        return classSentiment;
    }
}
