package se.johantiden.myfeed.util;

import java.util.HashMap;
import java.util.Map;

public final class Maps2 {

    public static <K, V> Map<K, V> newHashMap(K key, V value) {
        Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

}
