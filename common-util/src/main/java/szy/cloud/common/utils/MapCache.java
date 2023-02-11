package szy.cloud.common.utils;

import java.util.HashMap;
import java.util.Map;

// 用作全局缓存，替代redis
public class MapCache {

    private static final Map<String, Object> cache = new HashMap<>();

    public static void put(String key, Object value){
        cache.put(key, value);
    }

    public static <T> T get(String key){
        if (cache.containsKey(key)){
            return (T)cache.get(key);
        }
        return null;
    }

    // debug用
    public static String getAll(){
        return cache.toString();
    }

}
