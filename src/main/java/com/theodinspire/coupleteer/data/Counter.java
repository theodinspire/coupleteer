package com.theodinspire.coupleteer.data;

import com.theodinspire.coupleteer.*;

import java.io.Serializable;
import java.util.*;

/**
 * Created by corms on 5/23/17.
 */

public class Counter<T> implements Serializable {
    protected Map<T, Integer> map = new HashMap<>();
    protected int size = 0;
    
    public Counter() { }
    
    public boolean count(T key) {
        return countWithWeight(key, 1);
    }
    
    public boolean countWithWeight(T key, int weight) {
        size += weight;
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + weight);
            return true;
        } else {
            map.put(key, weight);
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
    
    public int getSize() { return size; }
}
