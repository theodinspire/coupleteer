package com.theodinspire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Eric T Cormack on 21 May 2017.
 *
 */
public class RhymeBuilder {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/smalldict.txt"))) {
            while (reader.ready()) {
                print(reader.readLine());
            }
        } catch (IOException e) {
            print(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    
    static private void print(String string) {
        System.out.println(string);
    }
}
