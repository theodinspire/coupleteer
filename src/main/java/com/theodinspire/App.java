package com.theodinspire;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        RhymeDictionary rhymer = new RhymeDictionary("/data/smalldict.txt", false);
    
        Set<Rhythm> rhythmSet = new HashSet<>();
        
        Set<Word> words = rhymer.getWords();
        System.out.println("Word count: " + words.size());
        
        for (Word word : words) {
            Rhythm rhythm = word.getRhythm();
            rhythmSet.add(rhythm);
        }
        
        for (Word word : rhymer.getWordsByString("effect")) System.out.println(word);
    
//        List<Rhythm> rhythms = new LinkedList<>();
//        rhythms.addAll(rhythmSet);
//        rhythms.sort(Comparator.comparingInt(LinkedList::size));
//
//        for (Rhythm rhythm : rhythms) {
//            rhythm.forEach(System.out::print);
//            System.out.println();
//        }
    }
}
