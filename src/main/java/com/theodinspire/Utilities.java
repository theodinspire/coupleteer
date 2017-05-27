package com.theodinspire;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by corms on 5/21/17.
 */

public class Utilities {
    private Utilities() { }
    
    static private Random rando = new Random();
    
    //  Random
    static int getRandomInt(int max) { return rando.nextInt(max); }
    
    //  IO
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
    
    static List<String> getResourceFiles(String resourcePath) {
        List<String> list = new ArrayList<>();
        
        try (BufferedReader rdr = getBufferedResourceReader(resourcePath)) {
            String fileName;
            
            while ((fileName = rdr.readLine()) != null) {
                list.add(resourcePath + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    //  Main
    public static void main(String[] args) {
        String string = "o'ermaster't";
        String[] split = string.split("(?='\\w+$)");
        
        System.out.println(split.length);
        for (String str : split) System.out.println(str);
    }
}
