package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise06_WordFrequencyTest {

    @Test
    void countWords_givenWords_countsCorrectly() {
        var counts = Exercise06_WordFrequency.countWords(Exercise06_WordFrequency.words());
        assertEquals(3, counts.get("apple"));
        assertEquals(2, counts.get("banana"));
        assertEquals(2, counts.get("orange"));
        assertEquals(1, counts.get("grape"));
    }

    @Test
    void countWords_isSortedAlphabetically() {
        var counts = Exercise06_WordFrequency.countWords(Exercise06_WordFrequency.words());
        assertEquals(List.of("apple", "banana", "grape", "orange"), List.copyOf(counts.keySet()));
    }

    @Test
    void countWords_emptyInput_returnsEmptyMap() {
        assertTrue(Exercise06_WordFrequency.countWords(List.of()).isEmpty());
    }
}
