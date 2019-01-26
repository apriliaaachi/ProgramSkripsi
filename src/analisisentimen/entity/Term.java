/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.entity;

import java.util.List;

/**
 *
 * @author Asus
 */
public class Term {
    private String term;
    List<Tweet> tweet;
    
    
    public Term(String term){
        this.term = term;
    }
    
    public void setTerm(String term){
        this.term = term;
    }
    
    public String getTerm(){
        return this.term;
    }
}
