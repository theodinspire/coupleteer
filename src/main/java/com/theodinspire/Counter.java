package com.theodinspire;

import java.util.*;

/**
 * Created by corms on 5/23/17.
 */

public class Counter<T> {
    protected Map<T, Integer> map = new HashMap<>();
    protected int size = 0;
    protected int base = 1;
    
    public Counter() { }
    
    public boolean count(T key) {
        ++size;
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + base);
            return true;
        } else {
            map.put(key, base);
            return false;
        }
    }
    
    public int getCount(T key) { return map.getOrDefault(key, 0); }
    
    public int total() { return size; }
    
    public Set<T> getKeySet() { return map.keySet(); }
    
    public List<T> getKeysAsList() {
        return new LinkedList<>(getKeySet());
    }
    
    public void addCounts(Counter<T> toAdd) {
        for (T key : toAdd.map.keySet()) {
            int addedCount = toAdd.map.get(key);
            map.put(key, addedCount + map.getOrDefault(key, 0));
            size += addedCount;
        }
    }
    
    public double getProbability(T key) { return (double) getCount(key) / (double) total(); }
    
    public T getRandom() {
        int threshold = Utilities.getRandomInt(total());
        
        for (T key : map.keySet()) {
            threshold -= getCount(key);
            if (threshold <= 0) return key;
        }
        
        return null;
    }
}
