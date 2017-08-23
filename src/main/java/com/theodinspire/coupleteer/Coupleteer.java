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
        rhymeDictionary = handler.getRhymeDictionary();
        words = handler.getWordEmissor();
        partsOfSpeech = handler.getPosEmissor();
        fore = handler.getForeBigram();
        back = handler.getBackBigram();
    }
    
    //  Members
    WordHoardHandler handler = WordHoardHandler.pullFromFile();
    RhymeDictionary rhymeDictionary;
    Emissor<String, String> words;
    Emissor<String, String> partsOfSpeech;
    Emissor<String, String> fore;
    Emissor<String, String> back;
    
    static final int DEFAULT_ATTEMPTS = 100;
    static final Set<String> PARTICLES = new HashSet<>(
            Arrays.asList(new String[] { Tags.CC, Tags.DET, Tags.IN, Tags.MD, Tags.PRP, Tags.PRPS, Tags.RP, Tags.TO }));
    
    
    //  Get couplets
    public Couplet getCouplet() {
        //  Build the first line
        int syllablesLeft = 11; //  Iambic pentameter with possible feminine ending
        String previousPartOfSpeech = Tags.getStartOfLine();
        StringBuilder firstLine = new StringBuilder();
        Word lastWord = null;
    
        while (syllablesLeft > 1) { //  Final syllable OPTIONAL
            Word word = null;
            int pickNewPOSCountdown = DEFAULT_ATTEMPTS;
            //  Get first next part of speech candidate
            String nextPartOfSpeech = fore.getCounter(previousPartOfSpeech).getRandom();
        
            while (word == null) {
                --pickNewPOSCountdown;
                boolean shouldHaveInitialAccent = syllablesLeft % 2 == 0;
                /*  For clarity: if the number of syllables remaining are odd
                    the next syllable should be unaccented, if even, accented   */
                
                String candidateString = words.getCounter(nextPartOfSpeech).getRandom();
                Set<Word> options = rhymeDictionary.getWordsByString(candidateString);
            
                for (Word option : options) {
                    Rhythm rhythm = option.getRhythm();
                    if (rhythm.size() == 0 || (rhythm.size() == 1 && PARTICLES.contains(nextPartOfSpeech))
                        || (rhythm.getFirst() > 0 == shouldHaveInitialAccent && rhythm.size() <= syllablesLeft)) {
                        /*  For clarity: If option does not have any syllables,
                            OR if the option only has one and the chosen POS could be a particle
                            OR, finally, if the option matches the required rhythm pattern
                                while being shorter than the remaining syllable count
                            Use the option for the next word
                        */
                        word = option;
                        break;
                    }
                }
            
                //  If we've not yet found a suitable candidate, pick a new part of speech
                if (pickNewPOSCountdown == 0) {
                    nextPartOfSpeech = fore.getCounter(previousPartOfSpeech).getRandom();
                    pickNewPOSCountdown = DEFAULT_ATTEMPTS;
                }
            }
        
            previousPartOfSpeech = nextPartOfSpeech;
            firstLine.append(word).append(" ");
        
            lastWord = word;
            syllablesLeft -= word.getRhythm().size();
        }
    
        //  Second line!
        StringBuilder secondLine = new StringBuilder();
        syllablesLeft = 11 - syllablesLeft;
        /*  If feminine ending was made there would be no syllables left,
            and so we must have a second line with a eleven syllables.
            If not, there'd be one remaining, and new line has ten syllables.   */
        List<Word> rhymes = new ArrayList<>(rhymeDictionary.get(lastWord.getRhyme()));
        Word rhyme;
        int attempts = DEFAULT_ATTEMPTS;
    
        do {
            rhyme = rhymes.get(Utilities.getRandomInt(rhymes.size()));
            --attempts;
        }
        while (!partsOfSpeech.getKeys().contains(rhyme.toString())
               && rhyme.equals(lastWord) && attempts > 0);
            /*  For clarity: Repeat attempt while the rhyme is not a Part of Speech tag,
                nor the last word unless we've run out of replacement attempts
             */
    
        syllablesLeft -= rhyme.getRhythm().size();
        Stack<Word> wordStack = new Stack<>();
        wordStack.push(rhyme);
        
        //  Assign a part of speech to the rhyming word, or if none are available, use Singular Proper Noun
        previousPartOfSpeech = words.getRandomKeyForValueWithDefault(rhyme.toString(), Tags.NNP);
    
        //  Works rather like a back to front version of the above
        while (syllablesLeft > 0) {
            Word word = null;
            int pickNewPOSCountdown = DEFAULT_ATTEMPTS;
            String nextPartOfSpeech = back.getCounter(previousPartOfSpeech).getRandom();
        
            while (word == null) {
                --pickNewPOSCountdown;
                boolean lastAccent = syllablesLeft % 2 == 0;
                String str = words.getCounter(nextPartOfSpeech).getRandom();
            
                Set<Word> options = rhymeDictionary.getWordsByString(str);
            
                for (Word option : options) {
                    Rhythm rhythm = option.getRhythm();
                    if (rhythm.size() == 0 || (rhythm.size() == 1 && PARTICLES.contains(nextPartOfSpeech))
                        || (rhythm.getLast() > 0 == lastAccent && rhythm.size() <= syllablesLeft)) {
                        /*  For clarity: If option does not have any syllables,
                            OR if the option only has one and the chosen POS could be a particle
                            OR, finally, if the option matches the required rhythm pattern
                                while being shorter than the remaining syllable count
                            Use the option for the next word
                        */
                        word = option;
                        break;
                    }
                }
    
                //  If we've not yet found a suitable candidate, pick a new part of speech
                if (pickNewPOSCountdown == 0) {
                    nextPartOfSpeech = back.getCounter(previousPartOfSpeech).getRandom();
                    pickNewPOSCountdown = DEFAULT_ATTEMPTS;
                }
            }
        
            previousPartOfSpeech = nextPartOfSpeech;
            wordStack.push(word);
        
            syllablesLeft -= word.getRhythm().size();
        }
    
        while (!wordStack.isEmpty()) secondLine.append(wordStack.pop()).append(" ");
        
        return new Couplet(firstLine.toString(), secondLine.toString());
    }
}
