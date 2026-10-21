package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Optional03_LRUCacheTest {

    @Test
    void put_thenGet_returnsValue() {
        var cache = new Optional03_LRUCache(3);
        cache.put(1, "A");
        assertEquals("A", cache.get(1).orElseThrow());
    }

    @Test
    void get_missingKey_returnsMinusOne() {
        var cache = new Optional03_LRUCache(3);
        assertEquals("-1", cache.get(99).orElse("-1"));
    }

    @Test
    void capacityOfThree_evictsLeastRecentlyUsed() {
        var cache = new Optional03_LRUCache(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.put(4, "D");

        assertEquals(3, cache.size());
        assertEquals(List.of(2, 3, 4), cache.keysLeastToMostRecent());
        assertTrue(cache.get(1).isEmpty());
    }

    @Test
    void successfulGet_countsAsUse_andProtectsFromEviction() {
        var cache = new Optional03_LRUCache(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.get(1);
        cache.put(4, "D");

        assertEquals(List.of(3, 1, 4), cache.keysLeastToMostRecent());
        assertTrue(cache.get(1).isPresent());
        assertTrue(cache.get(2).isEmpty());
    }

    @Test
    void fullExerciseTrace_matchesExample() {
        var cache = new Optional03_LRUCache(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        assertEquals("A", cache.get(1).orElse("-1"));
        cache.put(4, "D");

        assertEquals(List.of(3, 1, 4), cache.keysLeastToMostRecent());
        assertEquals("C", cache.get(3).orElse("-1"));
        assertEquals("A", cache.get(1).orElse("-1"));
        assertEquals("D", cache.get(4).orElse("-1"));
        assertEquals("-1", cache.get(2).orElse("-1"));
    }

    @Test
    void put_existingKey_updatesValueAndRefreshesRecency() {
        var cache = new Optional03_LRUCache(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.put(1, "A2");
        cache.put(4, "D");

        assertEquals("A2", cache.get(1).orElseThrow());
        assertTrue(cache.get(2).isEmpty());
        assertEquals(3, cache.size());
    }

    @Test
    void size_neverExceedsCapacity() {
        var cache = new Optional03_LRUCache(2);
        for (var key = 1; key <= 10; key++) {
            cache.put(key, "V" + key);
            assertTrue(cache.size() <= 2);
        }
        assertEquals(2, cache.size());
    }

    @Test
    void invalidCapacity_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Optional03_LRUCache(0));
    }
}
