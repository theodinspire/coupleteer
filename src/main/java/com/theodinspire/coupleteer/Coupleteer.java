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
        int flSyllablesLeft = 11; //  Iambic pentameter with possible feminine ending
        StringBuilder firstLine = new StringBuilder();
        String flPreviousPartOfSpeech = Tags.getStartOfLine();
        Word lastWord = null;
    
        while (flSyllablesLeft > 1) { //  Final syllable OPTIONAL
            Word word = null;
            int pickNewPOSCountdown = DEFAULT_ATTEMPTS;
            //  Get first next part of speech candidate
            String nextPartOfSpeech = fore.getCounter(flPreviousPartOfSpeech).getRandom();
        
            while (word == null) {
                --pickNewPOSCountdown;
                boolean shouldHaveInitialAccent = flSyllablesLeft % 2 == 0;
                /*  For clarity: if the number of syllables remaining are odd
                    the next syllable should be unaccented, if even, accented   */
                
                String candidateString = words.getCounter(nextPartOfSpeech).getRandom();
                Set<Word> options = rhymeDictionary.getWordsByString(candidateString);
            
                for (Word option : options) {
                    Rhythm rhythm = option.getRhythm();
                    if (rhythm.size() == 0 || (rhythm.size() == 1 && PARTICLES.contains(nextPartOfSpeech))
                        || (rhythm.getFirst() > 0 == shouldHaveInitialAccent && rhythm.size() <= flSyllablesLeft)) {
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
                    nextPartOfSpeech = fore.getCounter(flPreviousPartOfSpeech).getRandom();
                    pickNewPOSCountdown = DEFAULT_ATTEMPTS;
                }
            }
        
            flPreviousPartOfSpeech = nextPartOfSpeech;
            firstLine.append(word).append(" ");
        
            lastWord = word;
            flSyllablesLeft -= word.getRhythm().size();
        }
    
        //  Second line!
        StringBuilder secondLine = new StringBuilder();
        int slSyllablesLeft = 11 - flSyllablesLeft;
        /*  If feminine ending was made there would be no syllables left,
            and so we must have a second line with a eleven syllables.
            If not, there'd be one remaining, and new line has ten syllables.   */
        List<Word> rhymes = new ArrayList<>(rhymeDictionary.get(lastWord.getRhyme()));
        Word rhyme;
        
        if (rhymes.size() == 1) {
            rhyme = lastWord;
        } else {
            int pickIndex;
            int lastWordIndex = rhymes.indexOf(lastWord);
            do {
                pickIndex = Utilities.getRandomInt(rhymes.size());
            } while (pickIndex == lastWordIndex);
            
            rhyme = rhymes.get(pickIndex);
        }
        
    
        slSyllablesLeft -= rhyme.getRhythm().size();
        Stack<Word> wordStack = new Stack<>();
        wordStack.push(rhyme);
        
        //  Assign a part of speech to the rhyming word, or if none are available, use Singular Proper Noun
        String slPreviousPartOfSpeech = words.getRandomKeyForValueWithDefault(rhyme.toString(), Tags.NNP);
    
        //  Works rather like a back to front version of the above
        while (slSyllablesLeft > 0) {
            Word word = null;
            int pickNewPOSCountdown = DEFAULT_ATTEMPTS;
            String nextPartOfSpeech = back.getCounter(slPreviousPartOfSpeech).getRandom();
        
            while (word == null) {
                --pickNewPOSCountdown;
                boolean lastAccent = flSyllablesLeft % 2 == 0;
                String str = words.getCounter(nextPartOfSpeech).getRandom();
            
                Set<Word> options = rhymeDictionary.getWordsByString(str);
            
                for (Word option : options) {
                    Rhythm rhythm = option.getRhythm();
                    if (rhythm.size() == 0 || (rhythm.size() == 1 && PARTICLES.contains(nextPartOfSpeech))
                        || (rhythm.getLast() > 0 == lastAccent && rhythm.size() <= slSyllablesLeft)) {
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
                    nextPartOfSpeech = back.getCounter(slPreviousPartOfSpeech).getRandom();
                    pickNewPOSCountdown = DEFAULT_ATTEMPTS;
                }
            }
        
            slPreviousPartOfSpeech = nextPartOfSpeech;
            wordStack.push(word);
        
            slSyllablesLeft -= word.getRhythm().size();
        }
    
        while (!wordStack.isEmpty()) secondLine.append(wordStack.pop()).append(" ");
        
        return new Couplet(firstLine.toString(), secondLine.toString());
    }
}
