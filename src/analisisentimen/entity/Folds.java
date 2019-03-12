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
    
    public List<Tweet> getTrainingSet(int foldIteration, List<Tweet> listTweet)
    {
        int begin = foldIteration * listTweet.size() / fold;
        int end = (foldIteration + 1) * listTweet.size() / fold;
        List<Tweet> trainingSet = new ArrayList<>(listTweet.subList(0, begin));
        trainingSet.addAll(listTweet.subList(end, listTweet.size()));
        return trainingSet;
    }
    
    public List<Tweet> getListTweetLimit(int limit, List<Tweet> tweetList){
        List<Tweet> listTweet = new ArrayList<>(tweetList.subList(0, limit));
        
        return listTweet;
    }
    
    public List<Tweet> getTestSet(int foldIteration, List<Tweet> listTweet)
    {
        int begin = foldIteration * listTweet.size() / fold;
        int end = (foldIteration + 1) * listTweet.size() / fold;
        return new ArrayList<>(listTweet.subList(begin, end));
    }
    
    public int getFolds()
    {
        return fold;
    }
    
    
}
