package com.theodinspire;

import java.io.Serializable;
import java.util.*;

/**
 * Created by corms on 5/23/17.
 */
public class Emissor<K, V> implements Serializable {
    Map<K, Counter<V>> map = new HashMap<>();
    
    public void count(K key, V value) {
        Counter<V> counter;
        
        if ((counter = map.get(key)) == null) {
            counter = new Counter<>();
            map.put(key, counter);
        }
        
        counter.count(value);
    }
    
    public double getConditionalProbability(K key, V value) {
        if (map.containsKey(key)) return map.get(key).getProbability(value);
        else return 0;
    }
    
    public int length() { return map.size(); }
    
    public int totalCount() {
        int total = 0;
        for (K key : map.keySet() ) { total += map.get(key).total(); }
        return total;
    }
    
    public Counter<V> getCounter(K key) {
        return map.get(key);
    }
    
    public Set<K> getKeys() { return map.keySet(); }
    public List<K> getKeysAsList() {
        return new LinkedList<>(getKeys());
    }
}
