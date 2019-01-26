/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.entity;

import java.util.Objects;

/**
 *
 * @author Asus
 */
public class TagsBigram {
    private Tag previousTag;
    private Tag currentTag;

    public TagsBigram(Tag previousTag, Tag currentTag) {
        this.previousTag = previousTag;
        this.currentTag = currentTag;
    }

    @Override
    public String toString() {
        return previousTag + "_" + currentTag;
    }

    @Override
    public int hashCode() {
        int result = previousTag.hashCode();
        result = 31 * result + currentTag.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagsBigram)) return false;

        TagsBigram that = (TagsBigram) o;

        return currentTag.equals(that.currentTag) && previousTag.equals(that.previousTag);
    }
    
    
}
