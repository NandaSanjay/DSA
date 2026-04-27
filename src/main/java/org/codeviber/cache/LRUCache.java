package codeviber.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<k, v> extends LinkedHashMap<k, v> {
    private int maxSize;

    public LRUCache(int maxSize) {
        super(maxSize, 0.75F, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<k, v> eldest) {
        return size() > maxSize;
    }

    public v getCache(Object key) {
        return super.getOrDefault(key, null);
    }

    public v putCache(k key, v value) {
        return super.put(key, value);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
