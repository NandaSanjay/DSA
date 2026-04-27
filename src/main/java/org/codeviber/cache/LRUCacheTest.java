package codeviber.cache;

public class LRUCacheTest {

    public static void main(String[] args) {
        LRUCache<Integer,Integer> cache = new LRUCache<Integer,Integer>(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.putCache(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        cache.get(1);

        System.out.println(cache);
        cache.put(6, 6);
        System.out.println(cache);
        cache.put(7, 7);
        System.out.println(cache);
    }
}
