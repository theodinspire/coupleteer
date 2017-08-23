package com.theodinspire.coupleteer.data;

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
    
    public K getRandomKeyForValueWithDefault(V value, K defaultKey) {
        Counter<K> counter = new Counter<>();
        
        for (K key : getKeys()) {
            Counter<V> valueCounter = getCounter(key);
            if (valueCounter.getKeySet().contains(value)) {
                counter.countWithWeight(key, valueCounter.getCount(value));
            }
        }
        
        if (counter.getSize() > 0) return counter.getRandom();
        else return defaultKey;
    }
}
