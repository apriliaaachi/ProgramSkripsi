/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Sentence implements Serializable {
    
    private List<Word> words = new ArrayList<Word>();
    private List<Tag> tags = new ArrayList<Tag>();

    public List<Word> getWords() {
        return words;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
