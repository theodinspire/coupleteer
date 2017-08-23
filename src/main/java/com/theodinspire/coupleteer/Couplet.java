package com.theodinspire.coupleteer;

/**
 * Created by corms on 6/20/17.
 */
public class Couplet {
    private String first;
    private String second;
    
    public Couplet(String first, String second) {
        this.first = toInitialCapitalCase(first);
        this.second = toInitialCapitalCase(second);
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
