package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise06_WordCounterTest {
    @Test
    void totalWords_MotivatingText_returns6() {
        assertEquals(6, Part2Exercise06_WordCounter.totalWords("Keep going keep growing keep shining"));
    }

    @Test
    void countOccurrences_KeepCaseInsensitive_returns3() {
        assertEquals(3, Part2Exercise06_WordCounter.countOccurrences("Keep going keep growing keep shining", "keep"));
        assertEquals(3, Part2Exercise06_WordCounter.countOccurrences("Keep going keep growing keep shining", "KEEP"));
    }

    @Test
    void totalWords_Empty_returns0() {
        assertEquals(0, Part2Exercise06_WordCounter.totalWords("   "));
    }

    @Test
    void totalWords_ExtraSpaces_countsCorrectly() {
        assertEquals(3, Part2Exercise06_WordCounter.totalWords("  keep   going strong  "));
    }

    @Test
    void countOccurrences_MissingWord_returns0() {
        assertEquals(0, Part2Exercise06_WordCounter.countOccurrences("Keep going keep growing", "quit"));
    }
}
