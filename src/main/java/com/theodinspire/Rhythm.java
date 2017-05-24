package com.theodinspire;

import java.util.LinkedList;

/**
 * Created by corms on 5/23/17.
 */
public class Rhythm extends LinkedList<Integer> {
    @Override
    public int hashCode() {
        int hash = 0;
        for (int i : this) hash = hash * 47 + i;
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!o.getClass().isAssignableFrom(Rhythm.class)) return false;
        Rhythm that = (Rhythm) o;
        if (this.size() != that.size()) return false;
        for (int i = 0; i < size(); ++i) if (this.get(i) != that.get(i)) return false;
        return true;
    }
}
