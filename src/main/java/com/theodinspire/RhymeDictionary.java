package com.theodinspire;

import java.io.*;
import java.util.*;

/**
 * Created by Eric T Cormack on 21 May 2017.
 *
 */
public class RhymeDictionary extends HashMap<Rhyme, Set<Rhymable>> implements Serializable {
    private HashSet<Word> words = new HashSet<>();
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
    
    public Set<Word> getWordsByString(String str) {
        Set<Word> set = new HashSet<>();
        for (Word word : words) if (word.toString().equalsIgnoreCase(str)) set.add(word);
        return set;
    }
    
    //  Methods
    public void add(Word word) {
        if (iambsOnly && !word.isIambic()) return;
        
        words.add(word);
        
        if (keySet().contains(word.getRhyme())) {
            get(word.getRhyme()).add(word);
        } else {
            Set<Rhymable> set = new HashSet<>();
            set.add(word.getRhyme());
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
                if (line.length() == 0) continue;
                lines.add(new LinkedList<>(Arrays.asList(line.split("\\s+"))));
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        return lines;
    }
    
    //  Main
    public static void main(String[] args) {
        RhymeDictionary rhymer = new RhymeDictionary("/data/smallDict.txt");
        
//        for (Rhyme rhyme : rhymer.keySet()) {
//            System.out.println(rhyme + ": " + rhymer.get(rhyme).size());
//        }
        System.out.println(rhymer.size());
        
//        try {
//            rhymer.writeToFile("rhymingDictionary.ser");
//        } catch (IOException e) {
//            System.out.println(e.getLocalizedMessage());
//            e.printStackTrace();
//        }
    }
}
