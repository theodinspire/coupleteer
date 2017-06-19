package com.theodinspire.coupleteer;

import java.io.*;
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
    static public int getRandomInt(int max) { return rando.nextInt(max); }
    
    //  IO
    static public BufferedReader getBufferedResourceReader(String path) throws IOException {
        return new BufferedReader(new InputStreamReader(Utilities.class.getResourceAsStream(path)));
    }
    
    static public BufferedReader getBufferedFileReader(String path) throws IOException {
        return new BufferedReader(new FileReader(path));
    }
    
    static public BufferedReader getBufferedReader(String path) throws IOException {
        try {
            return getBufferedResourceReader(path);
        } catch (Exception e) {
            return getBufferedFileReader(path);
        }
    }
    
    static public List<String> getResourceFiles(String resourcePath) {
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
        try {
            File file = new File("");
            System.out.println(file.getCanonicalPath());
        } catch (Exception e) { e.printStackTrace(); }
    }
}
