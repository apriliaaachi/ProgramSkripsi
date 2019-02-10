/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.entity;

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
public class Folds {
    private List<Tweet> tweetList;
    private int fold;

    public Folds(int fold, List<Tweet> tweetList) {
        this.tweetList = tweetList;
        this.fold = fold;
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
        int begin = foldIteration * size() / fold;
        int end = (foldIteration + 1) * size() / fold;
        List<Tweet> trainingSet = new ArrayList<>(tweetList.subList(0, begin));
        trainingSet.addAll(tweetList.subList(end, size()));
        return trainingSet;
    }
    
    public List<Tweet> getTestSet(int foldIteration)
    {
        int begin = foldIteration * size() / fold;
        int end = (foldIteration + 1) * size() / fold;
        return new ArrayList<>(tweetList.subList(begin, end));
    }
    
    public int getFolds()
    {
        return fold;
    }
    
    
}
