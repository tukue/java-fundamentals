package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise13_MostFrequentWordTest {

    @Test
    void countWords_countsEachWord() {
        var counts = Exercise13_MostFrequentWord.countWords(Exercise13_MostFrequentWord.words());
        assertEquals(3, counts.get("cat"));
        assertEquals(4, counts.get("dog"));
        assertEquals(1, counts.get("bird"));
        assertEquals(1, counts.get("fish"));
    }

    @Test
    void mostFrequent_returnsDogWithCount4() {
        var result = Exercise13_MostFrequentWord.mostFrequent(Exercise13_MostFrequentWord.words());
        assertEquals("dog", result.word());
        assertEquals(4, result.count());
    }

    @Test
    void mostFrequent_singleWord_returnsIt() {
        var result = Exercise13_MostFrequentWord.mostFrequent(List.of("solo"));
        assertEquals(new WordCount("solo", 1), result);
    }
}
