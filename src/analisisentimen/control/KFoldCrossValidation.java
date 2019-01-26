/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Tweet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Asus
 */
public class KFoldCrossValidation {
     private List<Tweet> tweetList;
    private int folds;

    public KFoldCrossValidation(int folds, List<Tweet> tweetList) {
        this.tweetList = tweetList;
        this.folds = folds;
    }
    
    public List<Tweet> getTweetList()
    {
        return tweetList;
    }
    
    public void shuffle(Random random, List<Tweet> tweetList)
    {
        Collections.shuffle(tweetList, random);
    }
    
    public int size()
    {

        return tweetList.size();
    }
    
    public void addAll(Collection<? extends Tweet> c)
    {
        tweetList.addAll(c);
    }
    
    public List<Tweet> getTrainingSet(int foldIteration)
    {
        int begin = foldIteration * size() / folds;
        int end = (foldIteration + 1) * size() / folds;
        List<Tweet> trainingSet = new ArrayList<>(tweetList.subList(0, begin));
        trainingSet.addAll(tweetList.subList(end, size()));
        return trainingSet;
    }
    
    public List<Tweet> getTestSet(int foldIteration)
    {
        int begin = foldIteration * size() / folds;
        int end = (foldIteration + 1) * size() / folds;
        return new ArrayList<>(tweetList.subList(begin, end));
    }
    
    public int getFolds()
    {
        return folds;
    }
    
    
}
