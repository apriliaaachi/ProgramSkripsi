/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import java.util.HashMap;
import java.util.Map;
import analisisentimen.entity.Tag;
import analisisentimen.entity.TagsBigram;
import analisisentimen.entity.WordTagBigram;
import analisisentimen.entity.Word;
import analisisentimen.entity.Sentence;



/**
 *
 * @author Asus
 */
public class HMMProb {
    private Map<TagsBigram, Integer> tagsBigramMap = new HashMap<TagsBigram, Integer>();
    private Map<WordTagBigram, Integer> wordTagBigramMap = new HashMap<WordTagBigram, Integer>();
    private Map<Tag, Integer> tagMap = new HashMap<Tag, Integer>();


    public double transitionProb(Tag previousTag, Tag currentTag) {

        Integer bigramCount = tagsBigramMap.get(new TagsBigram(previousTag, currentTag));
        Integer previousCount = tagMap.get(previousTag);

        if (bigramCount == null) {

            return  ((1.0/tagsBigramMap.size()) / (previousCount + tagMap.size()));
        }

        return ((double) bigramCount + 1.0) / (previousCount + tagMap.size());

    }

    public double emitionProb(Word word, Tag tag) {

        Integer bigramCount = wordTagBigramMap.get(new WordTagBigram(word, tag));
        Integer tagCount = tagMap.get(tag);

        if (bigramCount == null ) {

            return ((1.0/wordTagBigramMap.size()) / (tagCount + tagMap.size()));
             
        }

        return ((double) bigramCount + 1.0) / (tagCount + tagMap.size());

    }
    
    public void prepareCountHolderMaps() {

        DocumentReader dr = new DocumentReader();

        dr = dr.createDataTrainObject();

        tagMap = dr.getTagMap();

        createTagsBigramMap(dr);

        createWordTagBigramMap(dr);

    }
    
    public Map<Tag, Integer> getTagMap() {

        return tagMap;
    }

    private void createTagsBigramMap(DocumentReader dr) {

        for (Sentence sentence : dr.getSentenceList()) {

            for (int i = 0; i < sentence.getTags().size() - 1; i++) {

                Tag previousTag = sentence.getTags().get(i);
                Tag currentTag = sentence.getTags().get(i + 1);

                TagsBigram tagsBigram = new TagsBigram(previousTag, currentTag);

                if (tagsBigramMap.containsKey(tagsBigram)) {

                    tagsBigramMap.put(tagsBigram, tagsBigramMap.get(tagsBigram) + 1);

                } else {

                    tagsBigramMap.put(tagsBigram, 1);
                }
            }
        }

//        "C(ti-1, ti) holder map is created.."
    }

    private void createWordTagBigramMap(DocumentReader dr) {

        for (Sentence sentence : dr.getSentenceList()) {

            for (int i = 0; i < sentence.getWords().size(); i++) {

                Word word = sentence.getWords().get(i);
                Tag tag = sentence.getTags().get(i);

                WordTagBigram wordTagBigram = new WordTagBigram(word, tag);

                if (wordTagBigramMap.containsKey(wordTagBigram)) {

                    wordTagBigramMap.put(wordTagBigram, wordTagBigramMap.get(wordTagBigram) + 1);

                } else {

                    wordTagBigramMap.put(wordTagBigram, 1);
                }

            }
        }

//        "C(ti, wi) holder map is created.."
    }

}
