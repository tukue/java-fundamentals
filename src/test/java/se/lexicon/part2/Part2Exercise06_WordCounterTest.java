package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise06_WordCounterTest {
    @Test
    void totalWords_ExampleSentence_returns10() {
        assertEquals(10, Part2Exercise06_WordCounter.totalWords("To be or not to be that is the question"));
    }

    @Test
    void countOccurrences_BeCaseInsensitive_returns2() {
        assertEquals(2, Part2Exercise06_WordCounter.countOccurrences("To be or not to be that is the question", "be"));
        assertEquals(2, Part2Exercise06_WordCounter.countOccurrences("To be or not to be that is the question", "BE"));
    }

    @Test
    void totalWords_Empty_returns0() {
        assertEquals(0, Part2Exercise06_WordCounter.totalWords("   "));
    }
}
