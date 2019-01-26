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
public class Word {
    private String word;
    private String[] word2;

    public Word(String word) {
        this.word = word;
    }

    public Word(String[] word) {
        this.word2 = word;
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;

        Word word1 = (Word) o;

        return word.equals(word1.word);
    }
    
    @Override
    public int hashCode() {
        return word.hashCode();
    }
    
    
    
    

    
    
    
}
