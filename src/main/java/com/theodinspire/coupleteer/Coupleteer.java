package com.theodinspire.coupleteer;

import com.theodinspire.coupleteer.volumes.*;
import com.theodinspire.coupleteer.words.*;
import com.theodinspire.coupleteer.data.*;

import java.util.*;

/**
 * Created by corms on 6/4/17.
 */
public class Coupleteer {
    //  Singleton!
    static private Coupleteer instance = new Coupleteer();
    
    public static Coupleteer getInstance() { return instance; }
    
    private Coupleteer() {
        dictionary = handler.getRhymeDictionary();
        words = handler.getWordEmissor();
        posses = handler.getPosEmissor();
        fore = handler.getForeBigram();
        back = handler.getBackBigram();
    }
    
    //  Members
    WordHoardHandler handler = WordHoardHandler.pullFromFile();
    RhymeDictionary dictionary;
    Emissor<String, String> words;
    Emissor<String, String> posses;
    Emissor<String, String> fore;
    Emissor<String, String> back;
    
    static final int DEFAULT_ATTEMPTS = 100;
    static final Set<String> PARTICLES = new HashSet<>(
            Arrays.asList(new String[] { Tags.CC, Tags.DET, Tags.IN, Tags.MD, Tags.PRP, Tags.PRPS, Tags.RP, Tags.TO }));
    
    
    //  Get couplets
    public String[] getCouplet() {
        String[] couplet = new String[2];
        
        //  First Line!
        int syllablesLeft = 11;
        String oldpos = Tags.getStartOfLine();
        StringBuilder line = new StringBuilder();
        Word lastWord = null;
    
        while (syllablesLeft > 0) {
            Word word = null;
            int pickNewPos = DEFAULT_ATTEMPTS;
            String newpos = fore.getCounter(oldpos).getRandom();
        
            if (!newpos.matches("\\w+\\$?")) {
                word = new DummyWord(newpos);
            }
        
            while (word == null) {
                --pickNewPos;
                boolean firstAccent = syllablesLeft % 2 == 0;
                String str = words.getCounter(newpos).getRandom();
            
                Set<Word> options = dictionary.getWordsByString(str);
            
                for (Word option : options) {
                    Rhythm rhythm = option.getRhythm();
                    if (rhythm.size() == 0 || (rhythm.size() == 1 && PARTICLES.contains(newpos))
                        || (rhythm.getFirst() > 0 == firstAccent && rhythm.size() <= syllablesLeft)) {
                        word = option;
                        break;
                    }
                }
            
                if (pickNewPos == 0) {
                    newpos = fore.getCounter(oldpos).getRandom();
                    pickNewPos = DEFAULT_ATTEMPTS;
                }
            }
        
            oldpos = newpos;
            line.append(word).append(" ");
        
            lastWord = word;
            syllablesLeft -= word.getRhythm().size();
            if (syllablesLeft == 1) break;
        }
        //  End of first line
        couplet[0] = toInitialCapitalCase(line.toString());
        
        line = new StringBuilder();
    
        //  Second line!
        syllablesLeft = 11 - syllablesLeft; // If feminine ending was made
        // there would be no syllables left, and so we must make another
        List<Word> rhymes = new ArrayList<>(dictionary.get(lastWord.getRhyme()));
        Word rhyme;
        int attempts = DEFAULT_ATTEMPTS;
    
        do {
            rhyme = rhymes.get(Utilities.getRandomInt(rhymes.size()));
            --attempts;
        }
        while (!posses.getKeys().contains(rhyme.toString())
               && rhyme.equals(lastWord) && attempts > 0);
    
        syllablesLeft -= rhyme.getRhythm().size();
        Stack<Word> wordStack = new Stack<>();
        wordStack.push(rhyme);
    
        oldpos = posses.getCounter(lastWord.toString()).getRandom();
    
        while (syllablesLeft > 0) {
            Word word = null;
            int pickNewPos = DEFAULT_ATTEMPTS;
            String newpos = back.getCounter(oldpos).getRandom();
        
            if (!newpos.matches("\\w+\\$?")) {
                word = new DummyWord(newpos);
            }
        
            while (word == null) {
                --pickNewPos;
                boolean lastAccent = syllablesLeft % 2 == 0;
                String str = words.getCounter(newpos).getRandom();
            
                Set<Word> options = dictionary.getWordsByString(str);
            
                for (Word option : options) {
                    Rhythm rhythm = option.getRhythm();
                    if (rhythm.size() == 0 || (rhythm.size() == 1 && PARTICLES.contains(newpos))
                        || (rhythm.getLast() > 0 == lastAccent && rhythm.size() <= syllablesLeft)) {
                        word = option;
                        break;
                    }
                }
            
                if (pickNewPos == 0) {
                    newpos = back.getCounter(oldpos).getRandom();
                    pickNewPos = DEFAULT_ATTEMPTS;
                }
            }
        
            oldpos = newpos;
            wordStack.push(word);
        
            syllablesLeft -= word.getRhythm().size();
        }
    
        while (!wordStack.isEmpty()) line.append(wordStack.pop()).append(" ");
        
        couplet[1] = toInitialCapitalCase(line.toString());
        
        return couplet;
    }
    
    private String toInitialCapitalCase(String string) {
        int index = -1;
        for (int i = 0; i < string.length(); ++i) {
            if (Character.isAlphabetic(string.charAt(i))) {
                index = i + 1; // Seeking index after first letter
                break;
            }
        }
        
        if (index < 0) return string;
        else return string.substring(0, index).toUpperCase() + string.substring(index);
    }
}
