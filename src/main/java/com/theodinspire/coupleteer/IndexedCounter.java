package com.theodinspire.coupleteer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by corms on 5/23/17.
 */
public class IndexedCounter<K, I> {
    Map<K, Counter<I>> map = new HashMap<>();
}
