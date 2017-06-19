package com.theodinspire.coupleteer.words;

import java.io.Serializable;

/**
 * Created by corms on 5/21/17.
 */
public abstract class Rhymable implements Serializable {
    protected Rhythm rhythm = new Rhythm();
    protected String self;
    
    //  Getters
    abstract public Rhyme getRhyme();
    
    public Rhythm getRhythm() { return rhythm; }
    
    public int syllableCount() { return rhythm.size(); }
    
    //  Methods
    public boolean isIambic() {
        boolean previousZero = true;
        
        for (int i : rhythm) {
            boolean isZero = i == 0;
            if (!previousZero && !isZero) return false;
            previousZero = isZero;
        }
        
        return true;
    }
    
    @Override
    public String toString() { return self; }
    
    @Override
    public int hashCode() {
        return self.hashCode() + rhythm.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().isAssignableFrom(Rhymable.class)) {
            return false;
        } else {
            Rhymable that = (Rhymable) obj;
            return this.hashCode() == that.hashCode();
        }
    }
}
