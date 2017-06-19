package com.theodinspire.coupleteer.volumes;

import com.theodinspire.coupleteer.*;
import com.theodinspire.coupleteer.words.*;
import com.theodinspire.coupleteer.data.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by corms on 5/23/17.
 */
public class Vocabulary {
    Set<Word> words = new HashSet<>();
    IndexedSets<String, Word> wordSets = new IndexedSets<>();
    RhymeDictionary rhymeDictionary = new RhymeDictionary(false);
    
    public Vocabulary() {
        try (BufferedReader reader = Utilities.getBufferedReader("/data/cmudict-0.7b.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                Word word = new Word(line);
                words.add(word);
                wordSets.add(word.toString(), word);
                rhymeDictionary.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static public void main(String[] args) {
    
    }
}
