package com.theodinspire.coupleteer.words;

import java.io.Serializable;
import java.util.*;

/**
 * Created by corms on 5/21/17.
 */
public class Word extends Rhymable implements Serializable {
    //static final long serialVersionUID = "Word 170526".hashCode();
    private Rhyme rhyme;
    
    public Word(String self) { this.self = self; }
    
    public Word(LinkedList<String> cmuEntry) {
        this.self = cmuEntry.poll().toLowerCase().replaceAll("\\(\\d+\\)", "");
        rhythm = new Rhythm();
        
        StringBuilder rhymeBuilder = new StringBuilder();
        Rhythm rhymeRhythm = new Rhythm();
        
        boolean inRhyme = false;
        for (String sound : cmuEntry) {
            if (sound.matches("[A-Z]+\\d")) {
                String[] bits = sound.split("(?=(\\d+))");
                int weight = Integer.parseInt(bits[1]);
                
                rhythm.add(weight);
                
                if (weight > 0) {
                    rhymeBuilder = new StringBuilder();
                    rhymeRhythm.clear();
                    rhymeBuilder.append(bits[0]);
                } else {
                    rhymeBuilder.append(bits[0].toLowerCase());
                }
                
                rhymeRhythm.add(weight);
            } else {
                rhymeBuilder.append(sound.toLowerCase());
            }
        }
        
        rhyme = new Rhyme(rhymeBuilder.toString(), rhymeRhythm);
    }
    
    @Override
    public Rhyme getRhyme() { return rhyme; }
    
    //  Main
    public static void main(String[] args) {
        LinkedList<String> bookLine = new LinkedList<>(Arrays.asList("BOOK  B UH1 K".split("\\s+")));
        LinkedList<String> lookLine = new LinkedList<>(Arrays.asList("LOOK  L UH1 K".split("\\s+")));
        LinkedList<String> cliticLine = new LinkedList<>(Arrays.asList("'S  Z".split("\\s+")));
        
        Word book = new Word(bookLine);
        Word look = new Word(lookLine);
        Word clitic = new Word(cliticLine);
        
        System.out.println(book.getRhyme().hashCode());
        System.out.println(look.getRhyme().hashCode());
        System.out.println(look.getRhyme().compareTo(book.getRhyme()));
    
        Set<Rhymable> rhymes = new HashSet<>();
        rhymes.add(book.getRhyme());
        rhymes.add(look.getRhyme());
        System.out.println(rhymes.size());
        
        System.out.println(clitic.getRhythm().size());
    }
}
