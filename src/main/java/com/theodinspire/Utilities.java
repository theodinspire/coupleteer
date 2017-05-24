package com.theodinspire;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by corms on 5/21/17.
 */

public class Utilities {
    private Utilities() { }
    
    static private Random rando = new Random();
    
    static BufferedReader getBufferedResourceReader(String path) throws IOException {
        return new BufferedReader(new InputStreamReader(Utilities.class.getResourceAsStream(path)));
    }
    
    static BufferedReader getBufferedFileReader(String path) throws IOException {
        return new BufferedReader(new FileReader(path));
    }
    
    static BufferedReader getBufferedReader(String path) throws IOException {
        try {
            return getBufferedResourceReader(path);
        } catch (Exception e) {
            return getBufferedFileReader(path);
        }
    }
    
    static int getRandomInt(int max) { return rando.nextInt(max); }
    
    public static void main(String[] args) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        
        try {
            SAXParser parser = factory.newSAXParser();
            InputStream stream = Utilities.class.getResourceAsStream("/texts/son.xml");
            
            parser.parse(stream, new DefaultHandler());
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
