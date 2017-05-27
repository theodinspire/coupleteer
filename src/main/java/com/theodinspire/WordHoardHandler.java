package com.theodinspire;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

/**
 * Created by corms on 5/24/17.
 */
public class WordHoardHandler extends DefaultHandler implements Serializable {
    private static final String DEFAULT_SOURCE_DIR = "/texts/";
    private static final String STORED_PATH = "/data/wordHoard.ser";
    
    //  Memory
    private String currentFile = "";
    
    private String previousPOS = "";
    private String currentPOS[] = {};
    
    //  State
    private boolean currentLineIsMetered = false;
    private boolean isWord = false;
    private boolean isPunc = false;
    
    //  Emissors
    private Emissor<String, String> foreBigram = new Emissor<>();
    private Emissor<String, String> backBigram = new Emissor<>();
    private Emissor<String, String> wordEmissor = new Emissor<>();
    private Emissor<String, String> posEmissor = new Emissor<>();
    
    public Emissor<String, String> getForeBigram()  { return foreBigram; }
    public Emissor<String, String> getBackBigram()  { return backBigram; }
    public Emissor<String, String> getWordEmissor() { return wordEmissor; }
    public Emissor<String, String> getPosEmissor()  { return posEmissor; }
    
    static private final String BOL = Tags.getStartOfLine();
    static private final String EOL = Tags.getEndOfLine();
    
    public static final String WORD_TAG = "w";
    public static final String PUNC_TAG = "punc";
    public static final String LINE_TAG = "wordhardtaggedline";
    
    private transient RhymeDictionary rhymeDictionary;
    private transient boolean dictionaryNotNull;
    
    //  Init
    public WordHoardHandler() {
        super();
        rhymeDictionary = RhymeDictionary.pullFromFile();
        dictionaryNotNull = rhymeDictionary != null;
        train();
    }
    
    //  Test
    
    public void train() {
        List<String> filelist = Utilities.getResourceFiles(DEFAULT_SOURCE_DIR);
        System.out.println("I'm training from the Shakespeare!");
    
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
    
        try {
            SAXParser parser = factory.newSAXParser();
        
            for (String file : filelist) {
                InputStream stream = Utilities.class.getResourceAsStream(file);
            
                currentFile = file;
                parser.parse(stream, this);
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    private void countPOS(String a, String b) {
        if (a.equalsIgnoreCase("") || b.equalsIgnoreCase("")) {
            System.out.println(a + "." + b);
        }
        
        if (!b.equalsIgnoreCase(EOL)) foreBigram.count(a, b);
        if (!a.equalsIgnoreCase(BOL)) backBigram.count(b, a);
    }
    
    private void countWord(String pos, String word) {
        if (!dictionaryNotNull || rhymeDictionary.getWordsAsStrings().contains(word) || (!word.matches("") && word.matches("\\W+"))) {
            wordEmissor.count(pos, word);
            posEmissor.count(word, pos);
        }
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        rhymeDictionary = RhymeDictionary.pullFromFile();
        dictionaryNotNull = rhymeDictionary != null;
    }
    
    public RhymeDictionary getRhymeDictionary() { return rhymeDictionary; }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase("w")) {
            String pos = attributes.getValue("pos");
            isWord = true;
            previousPOS = currentPOS[currentPOS.length - 1];
            currentPOS = Tags.convertWoardHoardTag(pos);
            
//        } else if (localName.equalsIgnoreCase("punc")) {
//            isPunc = true;
            
        } else if (localName.equalsIgnoreCase("wordhoardtaggedline")) {
            currentLineIsMetered = attributes.getValue("prosodic") == null;
            currentPOS = new String[] { BOL };
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equalsIgnoreCase(WORD_TAG)) {
            isWord = false;
        } else if (localName.equalsIgnoreCase(PUNC_TAG)) {
            isPunc = false;
        } else if (localName.equalsIgnoreCase(LINE_TAG)) {
            if (currentLineIsMetered) countPOS(currentPOS[ currentPOS.length - 1], EOL);
            currentLineIsMetered = false;
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentLineIsMetered && isWord) {
            String word = new String(ch, start, length).toLowerCase();
            word = word.trim();
            
            //  Pure lexemes
            if (currentPOS.length == 1) {
                if (word.equalsIgnoreCase("to")) {
                    currentPOS = new String[] { Tags.TO };
                }
                
                countPOS(previousPOS, currentPOS[0]);
                countWord(currentPOS[0], word);
                //  Contractions!
            } else if (currentPOS.length == 2) {
                String[] words;
                
                if (word.matches("[^']+")) word += "'";
                
                if (word.matches("^'t.+")) words = word.split("(?<='t)");
                else if (word.matches(".+'\\w+")) words = word.split("(?='\\w+$)");
                else if (word.matches(".+'")) words = word.split("(?='$)");
                else if (word.matches("'.+")) words = word.split("(?<=^')");
                else words = new String[] {};
                
                countPOS(previousPOS, currentPOS[0]);
                countPOS(currentPOS[0], currentPOS[1]);
                
                countWord(currentPOS[0], words[0]);
                countWord(currentPOS[1], words[1]);
            }
            
            //  Punctuation
        } else if (currentLineIsMetered && isPunc) {
            String punc = new String(ch, start, length).replaceAll("\\s+", "");
            
            if (!punc.equalsIgnoreCase("")) {
                //  Because currentPOS is set in the <w> tag
                previousPOS = currentPOS[currentPOS.length - 1];
                currentPOS = new String[] { punc };
    
                countPOS(previousPOS, currentPOS[0]);
                countWord(currentPOS[0], currentPOS[0]);
                
            }
        }
    }
    
    //  IO
    public boolean writeToFile(String filepath) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filepath));
        stream.writeObject(this);
        stream.close();
        return true;
    }
    
    public static WordHoardHandler pullFromFile() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             WordHoardHandler.class.getResourceAsStream(STORED_PATH))) {
            
            Object object = ois.readObject();
            
            if (object.getClass().equals(WordHoardHandler.class)) {
                return (WordHoardHandler) object;
            } else {
                System.out.println("Cast failure");
                return new WordHoardHandler();
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return new WordHoardHandler();
        }
    }
    
    //  Main
    public static void main(String[] args) {
        WordHoardHandler handler = //WordHoardHandler.pullFromFile();
            new WordHoardHandler();
        
        Emissor<String, String> emissor = handler.getWordEmissor();
        List<String> list = new LinkedList<>(emissor.getKeys());
        list.sort(Comparator.naturalOrder());
        
        for (String item : list) {
            System.out.println(item + " -> " + emissor.getCounter(item).size);
        }
        
        System.out.println();
        System.out.println(emissor.totalCount());
    
        try {
            handler.writeToFile("wordHoard.ser");
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        
//        System.out.print(handler.getWordEmissor().length());
//
//        for (String word : handler.getWordEmissor().getCounter("n2-acp").getKeySet()) {
//            System.out.print(word);
//        }
    }
}
