/*
 * Collection choice: LinkedHashMap with accessOrder = true
 *
 * - "Think about it": which collection maintains insertion order AND gives
 *   control over eviction? LinkedHashMap, subclassed in two places:
 *     1) accessOrder = true  -> every get() re-inserts the entry at the end,
 *        so the map is always ordered least-recently-used -> most-recently-used.
 *     2) removeEldestEntry() -> called after each put(), returns true to drop
 *        the head (the least recently used entry) once size exceeds capacity.
 * - Both hooks make get() and put() O(1): no scanning, no re-sorting.
 * - get() returns Optional<String> so "not found" is explicit; the caller
 *   maps it to the -1 the exercise asks for with orElse("-1").
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Optional03_LRUCache {

    private final int capacity;
    private final LinkedHashMap<Integer, String> cache;

    public Optional03_LRUCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be at least 1");
        }
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > Optional03_LRUCache.this.capacity;
            }
        };
    }

    public void put(int key, String value) {
        cache.put(key, value);
    }

    public Optional<String> get(int key) {
        return Optional.ofNullable(cache.get(key));
    }

    public int size() {
        return cache.size();
    }

    public int capacity() {
        return capacity;
    }

    public List<Integer> keysLeastToMostRecent() {
        return new ArrayList<>(cache.keySet());
    }

    public static void main(String[] args) {
        var lru = new Optional03_LRUCache(3);
        lru.put(1, "A");
        lru.put(2, "B");
        lru.put(3, "C");
        System.out.println("put(1..3)      -> " + lru.keysLeastToMostRecent());

        System.out.println("get(1)         -> " + lru.get(1).orElse("-1"));
        System.out.println("  now order    -> " + lru.keysLeastToMostRecent());

        lru.put(4, "D");
        System.out.println("put(4, D)      -> " + lru.keysLeastToMostRecent() + " (2 was evicted)");

        System.out.println("get(2)         -> " + lru.get(2).orElse("-1"));
        System.out.println("size           -> " + lru.size() + "/" + lru.capacity());
    }
}
