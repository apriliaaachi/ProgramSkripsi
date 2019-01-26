/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisentimen.control;

import analisisentimen.entity.Sentence;
import analisisentimen.entity.Tag;
import analisisentimen.entity.Word;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Asus
 */
public class Corpus {
    private List<Sentence> sentenceList = new ArrayList<Sentence>();
    private Map<Tag, Integer> tagMap = new HashMap<Tag, Integer>();
    private Map<Word, Map<Tag, Integer>> wordTagCountMap = new HashMap<Word, Map<Tag, Integer>>();
    
    private static Map<Character, String[]> KamusKDid;
    
    public List<Sentence> getSentenceList() {
        return sentenceList;
    }
    
    public Map<Tag, Integer> getTagMap() {
        return tagMap;
    }

    public void readTrainPOS(String fileName) {

        BufferedReader br = null;

        try {

            String fileLine;

            br = new BufferedReader(new FileReader(fileName));

            while ((fileLine = br.readLine()) != null) {

                Sentence sentence  = new Sentence();
                sentence.getWords().add(new Word(Constants.SENTENCE_START));
                sentence.getTags().add(new Tag(Constants.SENTENCE_START));

                do {

                    //take words as one part, they may include more than one word
                    String word = fileLine.substring(0, fileLine.lastIndexOf(Constants.WORD_TAG_SEPERATOR));
                    sentence.getWords().add(new Word(word.toLowerCase()));

                    String tag = fileLine.substring(fileLine.lastIndexOf(Constants.WORD_TAG_SEPERATOR) + 1);
                    //tags may be like tag1|tag2, take first one as analysis design
                    if (tag.contains(Constants.MULTI_TAG_SEPERATOR)) {

                        String tagFirst = tag.substring(0, tag.lastIndexOf(Constants.MULTI_TAG_SEPERATOR));
                        sentence.getTags().add(new Tag(tagFirst));

                    } else {

                        sentence.getTags().add(new Tag(tag));
                    }

                    //sentence.getTags().add(new Tag(tag));

                } while (!(fileLine = br.readLine()).equals(Constants.SENTENCE_SEPERATOR));

                sentence.getWords().add(new Word(Constants.SENTENCE_END));
                sentence.getTags().add(new Tag(Constants.SENTENCE_END));

                sentenceList.add(sentence);

            }

//            " is read and sentences are created.."

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createTagMap(String fileName) {

        for (Sentence sentence : sentenceList) {
            for (Tag tag : sentence.getTags()) {
                if (tagMap.containsKey(tag)) {
                    tagMap.put(tag, tagMap.get(tag) + 1);
                } else {
                    tagMap.put(tag, 1);
                }
            }
        }
        
        //System.out.println("Size of tag map is: " + tagMap.size());
        //System.out.println("tag set of " + fileName + ": " + tagMap.keySet());
         
    }

    public Tag retrieveMostFreqTagForWord(Word word) {

        if (wordTagCountMap.containsKey(word)) {
            Map<Tag, Integer> map = wordTagCountMap.get(word);
            ValueComparator valueComparator = new ValueComparator(map);
            TreeMap<Tag, Integer> treeMap = new TreeMap<Tag, Integer>(valueComparator);
            treeMap.putAll(map);

            return treeMap.firstKey();

        } else {

            return new Tag("NN");
        }

    }

    public Corpus createDataTrainObject() {

        Corpus trainData = new Corpus();
        trainData.readTrainPOS(Constants.POS_TRAIN_FILE);
        trainData.createTagMap(Constants.POS_TRAIN_FILE);

        return trainData;
    } 
    
    public static Map<Character, String[]> getKamusKDid() {
        return KamusKDid;
    }
    
    private static List<Character> indexAlphabet;

    public static List<Character> getIndexAlphabet() {
        return indexAlphabet;
    }
    
    public static void initKamusKDid() {
        // init Kamus
        String pathKamusKDid = Constants.STEMMING;
        File FileFolder = new File(pathKamusKDid);
        List<String> Files = new ArrayList<String>(listFiles(FileFolder));
        Map<Character, String[]> tmpKamusKDid = new LinkedHashMap<Character, String[]>();
        try {
            char ix = 'a';
            for (String File : Files) {
                ArrayList<String> tmp = new ArrayList<String>();
                String line;
                BufferedReader reader = new BufferedReader(new FileReader(pathKamusKDid+File));
                while ((line = reader.readLine()) != null) {
                    tmp.add(line);
                }
                tmpKamusKDid.put(ix, tmp.toArray(new String[tmp.size()]));
                ix++;
            }
            String[] s = {"`"};
            tmpKamusKDid.put('`', s);
            KamusKDid = tmpKamusKDid;
        }
        catch(IOException e){
        }
        // init Index
        List<Character> tmpIndexAlphabet = new ArrayList<Character>();
        for(char c='a'; c<='z'; c++) {
            tmpIndexAlphabet.add(c);
        }
        tmpIndexAlphabet.add('`');
        indexAlphabet = tmpIndexAlphabet;
    }
    
    // list all Files found in folder
    private static ArrayList<String> listFiles(final File pathFolder) {
        // * Retrieve all files inside a Folder
        ArrayList<String> results = new ArrayList<String>();
        for (final File fileEntry : pathFolder.listFiles()) {
            if (fileEntry.isDirectory()) {
                 listFiles(fileEntry);
            } 
            else {
                results.add(fileEntry.getName());
            }
        }
        return results;
    } 
    
    public String[][] readKamusFormal() throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(Constants.BAHASA_BAKU_TIDAK_BAKU));
        
        Object tableLine[] = in.lines().toArray();
        int baris = tableLine.length;
        int kolom = tableLine[0].toString().trim().split(" ").length;
        
        String [][] normalize = new String[baris][kolom];
        for(int i=0; i<baris; i++) {
            String line = tableLine[i].toString().trim();
            String dataRow [] = line.split(" ");
            for(int j=0; j<kolom; j++) {
                normalize[i][j]=dataRow[j];
                //System.out.println(normalize[i][j]);

            }
        }
        
        return normalize;
        
    }
    
    public List<String> readStopWord() {
        List<String>lststopword = new ArrayList<String>();
		
        try {

            BufferedReader in = new BufferedReader(new FileReader(Constants.STOPWORD_LIST));
            StringBuffer sb = new StringBuffer();
            String s = null;
            while((s = in.readLine()) != null){
                sb.append(s);
            }
            lststopword.addAll(Arrays.asList(sb.toString().split(";")));

	} catch(FileNotFoundException ex) {
            
            System.out.println("File not found");
            
	} catch(IOException ex){
                   
            System.out.println("IOexception"); 
	}
        
        return lststopword;
    }
    
}
