package com.theodinspire.coupleteer;

import java.io.Serializable;

/**
 * Created by corms on 5/21/17.
 */
public class Rhyme extends Rhymable implements Comparable<Rhyme>, Serializable {
    //static final long serialVersionUID = "Rhyme 170526".hashCode();
    
    public Rhyme(String string, Rhythm rhythm) {
        self = string;
        this.rhythm = rhythm;
    }
    
    @Override
    public Rhyme getRhyme() { return this; }
    
    @Override
    public int compareTo(Rhyme that) {
        return this.self.compareTo(that.self);
    }
}
