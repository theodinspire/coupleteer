package com.theodinspire.coupleteer;

import java.util.*;

/**
 * Created by corms on 5/23/17.
 */
public class IndexedSets<K, V> implements Map<K, Set<V>> {
    Map<K, Set<V>> theMap = new HashMap<>();
    
    //  The useful ones
    public void add(K key, V item) {
        if (theMap.containsKey(key)) theMap.get(key).add(item);
        else {
            Set<V> set = new HashSet<>();
            set.add(item);
            theMap.put(key, set);
        }
    }
    
    public void addAll(K key, Collection<? extends V> lection) {
        if (theMap.containsKey(key)) theMap.get(key).addAll(lection);
        else {
            Set<V> set = new HashSet<>();
            set.addAll(lection);
            theMap.put(key, set);
        }
    }
    
    //  The updated ones
    @Override
    public Set<V> put(K key, Set<V> value) {
        addAll(key, value);
        return get(key);
    }
    
    @Override
    public void putAll(Map<? extends K, ? extends Set<V>> m) {
    
    }
    
    //  The wrapper ones
    @Override
    public int size() { return theMap.size(); }
    
    @Override
    public boolean isEmpty() { return theMap.isEmpty(); }
    
    @Override
    public boolean containsKey(Object key) { return theMap.containsKey(key); }
    
    @Override
    public boolean containsValue(Object value) { return theMap.containsValue(value); }
    
    @Override
    public Set<V> get(Object key) { return theMap.get(key); }
    
    @Override
    public Set<V> remove(Object key) { return theMap.remove(key); }
    
    @Override
    public void clear() { theMap.clear(); }
    
    @Override
    public Set<K> keySet() { return theMap.keySet(); }
    
    @Override
    public Collection<Set<V>> values() { return theMap.values(); }
    
    @Override
    public Set<Entry<K, Set<V>>> entrySet() { return theMap.entrySet(); }
}
