package com.theodinspire.coupleteer;

import java.io.*;
import java.util.*;

/**
 * Created by Eric T Cormack on 21 May 2017.
 *
 */
public class RhymeDictionary extends HashMap<Rhyme, Set<Word>> implements Serializable {
    private static final String DEFAULT_SOURCE_PATH = "/data/cmudict-0.7b.txt";
    private static final String STORED_PATH = "/data/rhymingDictionary.ser";
    
    private HashSet<Word> words = new HashSet<>();
    private HashSet<String> wordsAsStrings = new HashSet<>();
    private boolean iambsOnly = false;
    
    public RhymeDictionary() {
        super();
    }
    
    public RhymeDictionary(boolean excludeNonIambs) {
        this();
        iambsOnly = excludeNonIambs;
    }
    
    public RhymeDictionary(String filepath) {
        this();
        this.train(filepath);
    }
    
    public RhymeDictionary(String filepath, boolean excludeNonIambs) {
        this();
        iambsOnly = excludeNonIambs;
        this.train(filepath);
    }
    
    //  Getters
    public HashSet<Word> getWords() { return words; }
    
    public HashSet<String> getWordsAsStrings() { return wordsAsStrings; }
    
    public Set<Word> getWordsByString(String str) {
        Set<Word> set = new HashSet<>();
        for (Word word : words) if (word.toString().equalsIgnoreCase(str)) set.add(word);
        return set;
    }
    
    //  Methods
    public void add(Word word) {
        if (iambsOnly && !word.isIambic()) return;
        
        words.add(word);
        wordsAsStrings.add(word.toString());
        
        if (keySet().contains(word.getRhyme())) {
            get(word.getRhyme()).add(word);
        } else {
            Set<Word> set = new HashSet<>();
            //set.add(word.getRhyme());
            set.add(word);
            put(word.getRhyme(), set);
        }
    }
    
    public void addAll(Collection<? extends Word> words) {
        for (Word word : words) add(word);
    }
    
    public boolean writeToFile(String filepath) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filepath));
        stream.writeObject(this);
        stream.close();
        return true;
    }
    
    public void train(String filepath) {
        List<LinkedList<String>> lines = readInCMUFile(filepath);
    
        for (LinkedList<String> line : lines) {
            this.add(new Word(line));
        }
    }
    
    static private List<LinkedList<String>> readInCMUFile(String filepath) {
        List<LinkedList<String>> lines = new LinkedList<>();
    
        try (BufferedReader reader = Utilities.getBufferedReader(filepath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0 || line.substring(0, 3).equalsIgnoreCase(";;;")) continue;
                lines.add(new LinkedList<>(Arrays.asList(line.split("\\s+"))));
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        return lines;
    }
    
    //  Get stored Dictionary
    public static RhymeDictionary pullFromFile() {
        try (ObjectInputStream ois =
                new ObjectInputStream(
                        RhymeDictionary.class.getResourceAsStream(STORED_PATH))) {
            
            Object object = ois.readObject();
            
            if (object.getClass().equals(RhymeDictionary.class)) {
                return (RhymeDictionary) object;
            } else return new RhymeDictionary(DEFAULT_SOURCE_PATH);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return new RhymeDictionary(DEFAULT_SOURCE_PATH);
        }
    }
    
    //  Main
    public static void main(String[] args) {
        RhymeDictionary rhymer = new RhymeDictionary("/data/cmudict-0.7b.txt", true);
        
//        for (Rhyme rhyme : rhymer.keySet()) {
//            System.out.println(rhyme + ": " + rhymer.get(rhyme).size());
//        }
        System.out.println("Rhymes: " + rhymer.size());
        System.out.println("Words:  " + rhymer.getWords().size());
        System.out.println("Word Set type: " + rhymer.getWords().getClass());
        
        try {
            rhymer.writeToFile("rhymingDictionary.ser");
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
