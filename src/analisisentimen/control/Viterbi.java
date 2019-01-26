/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;


import analisisentimen.entity.Sentence;
import analisisentimen.entity.Tag;
import analisisentimen.entity.Word;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Asus
 */
public class Viterbi {
    HMMProb hmmProb = new HMMProb();
    List<Tag> tagList;
    private List<Sentence> sentenceList = new ArrayList<Sentence>();
    private Sentence sentence = new Sentence();
    List<Tag> viterbiPath = new ArrayList<Tag>();
    List<Word> word = new ArrayList<Word>();
    
    public void posTagger() {
        hmmProb.prepareCountHolderMaps();
        List<Tag> tagList = convertKeySetToList(hmmProb);

        runViterbi(tagList);
    }
    
    public List<Sentence> getSentenceList() {
        return sentenceList;
    }
    
    public void prepareCountViterbi(String[] token){
    
        Sentence sentence  = new Sentence();
        sentence.getWords().add(new Word(Constants.SENTENCE_START));
        for(int i=0; i<token.length; i++){
            
            sentence.getWords().add(new Word(token[i]));
      
        }
        sentence.getWords().add(new Word(Constants.SENTENCE_END));
        
        sentenceList.add(sentence);
        
    }
    
    
    public void runViterbi(List<Tag> tagList) {

        try {

            for (Sentence sentence : getSentenceList()) {

                int sentenceLength = sentence.getWords().size();
                int tagListLength = tagList.size();
                
                //System.out.println("isi sentence : " + " " + sentence.getWords()+" "+sentenceLength);              
                //System.out.println("isi tag : " + " " + tagList+tagListLength);

                Double[][] viterbiTable = new Double[tagListLength][sentenceLength];

                List<Tag> viterbiPath = new ArrayList<Tag>();

                // calculate first transition (first column in table)
                for (int t = 1; t < tagListLength; t++) {

                    Double tagTransitionProb = hmmProb.transitionProb(tagList.get(0), tagList.get(t));
                    Double wordTagLikelihoodProb = hmmProb.emitionProb(sentence.getWords().get(1), tagList.get(t));

                    viterbiTable[t][1] = tagTransitionProb * wordTagLikelihoodProb;

                }

                for (int w = 2; w < sentenceLength; w++) {

                    for (int t = 1; t < tagListLength; t++) {

                        Double[] probs = new Double[tagListLength];

                        for (int i = 1; i < tagListLength; i++) {

                            if (viterbiTable[i][w-1] != null) {

                                Double tagTransitionProb = hmmProb.transitionProb(tagList.get(i), tagList.get(t));

                                Double wordTagLikelihoodProb = hmmProb.emitionProb(sentence.getWords().get(w), tagList.get(t));

                                probs[i] = viterbiTable[i][w-1] * tagTransitionProb * wordTagLikelihoodProb;

                            } else {

                                probs[i] = 0.0;

                            }
                        }

                        int argmax = argmax(probs);

                        viterbiTable[t][w] = probs[argmax];
                    }
                }
                
                generateViterbiPath(tagList, sentenceLength, tagListLength, viterbiTable);
                wordSentence(sentence);
                
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    private void generateViterbiPath(List<Tag> tagList, int sentenceLength, int tagListLength, Double[][] viterbiTable) {

        for (int w = 1; w < sentenceLength; w++) {

            Double[] probs = new Double[tagListLength];

            for (int t = 1; t < tagListLength; t++) {

                probs[t] = viterbiTable[t][w];
            }

            int index = argmax(probs);

            viterbiPath.add(tagList.get(index));

        }

        viterbiPath.add(0, new Tag(Constants.SENTENCE_START));

//        System.out.println("test sentence words: " + sentence.getWords());
//        System.out.println("vibertiPath        : " + viterbiPath);
//        System.out.println("\n" + "\n");

    }
    
    private void wordSentence(Sentence sentence) {
        
        word = sentence.getWords();
        
    }
    
    public List<Word> getListWord(){
        
        return word;
    }
    
    public List<Tag> getListTag(){
        
        return viterbiPath;
    }
    
    private static Integer argmax(Double[] arr) {

        Double max = arr[1];

        Integer argmax = 1;

        for (int i=2; i<arr.length; i++) {

            if (arr[i] > max) {

                max = arr[i];
                argmax = i;

            }
        }

        return argmax;
    }

    public List<Tag> convertKeySetToList(HMMProb hmmProb) {

        Set set = hmmProb.getTagMap().keySet();

        List<Tag> tagList = new ArrayList<Tag>();

        tagList.addAll(set);

        tagList.remove(new Tag(Constants.SENTENCE_START));
        tagList.remove(new Tag(Constants.SENTENCE_END));

        tagList.add(0, new Tag(Constants.SENTENCE_START));
        tagList.add(tagList.size(), new Tag(Constants.SENTENCE_END));

        return tagList;
    }

    
}
