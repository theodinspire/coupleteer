package com.theodinspire.coupleteer;

/**
 * Created by corms on 6/20/17.
 */
public class Couplet {
    private String first;
    private String second;
    
    public Couplet(String first, String second) {
        this.first = first;
        this.second = second;
    }
    
    public String toStringWithDelimiter(String delimiter) {
        return first + delimiter + second;
    }
    
    public String getFirst() {
        return first;
    }
    
    public String getSecond() {
        return second;
    }
    
    @Override
    public String toString() { return toStringWithDelimiter("\n"); }
}
