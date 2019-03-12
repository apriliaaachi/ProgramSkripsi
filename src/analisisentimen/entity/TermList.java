/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class TermList {
    private List<Term> listTerm;
    
    public TermList(){
        listTerm = new ArrayList<>();
    }
    
    public TermList(List<Term> listTerm){
        this.listTerm = listTerm;
    }
    
    public void addTerm(Term term){
        listTerm.add(term);
    }
    
    public Term getTermAt(int idx){
        return listTerm.get(idx);
    }
    
    public int getTotalTerm(){
        return listTerm.size();
    }
    
    public String[] toStringArray(){
        String[] temp = new String[listTerm.size()];
        for(int i=0; i<temp.length; i++){
            temp[i] = listTerm.get(i).getTerm();
        }
        return temp;
    }
    
    public Term checkTerm(String strTerm){
        Term term = null;
        for(int i=0; i<listTerm.size(); i++){
            if(strTerm.equals(listTerm.get(i).getTerm())){
                term = listTerm.get(i);
                break;
            }
        }
        return term;
    }
}
