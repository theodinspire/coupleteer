package com.theodinspire;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by corms on 5/23/17.
 */
public class Emissor<K, V> {
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
}
