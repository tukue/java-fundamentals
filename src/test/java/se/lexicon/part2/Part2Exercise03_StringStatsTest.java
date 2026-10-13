package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise03_StringStatsTest {
    @Test
    void countStats_Code42_returns2220() {
        assertArrayEquals(new int[]{2, 2, 2, 0}, Part2Exercise03_StringStats.countStats("Code42"));
    }

    @Test
    void countStats_Empty_returnsZeros() {
        assertArrayEquals(new int[]{0, 0, 0, 0}, Part2Exercise03_StringStats.countStats(""));
    }

    @Test
    void countStats_OnlyDigits_countsCorrectly() {
        assertArrayEquals(new int[]{0, 0, 4, 0}, Part2Exercise03_StringStats.countStats("1234"));
    }

    @Test
    void countStats_Punctuation_ignored() {
        assertArrayEquals(new int[]{1, 1, 0, 0}, Part2Exercise03_StringStats.countStats("Hi!"));
    }

    @Test
    void countStats_AllVowels_countsOnlyVowels() {
        assertArrayEquals(new int[]{10, 0, 0, 0}, Part2Exercise03_StringStats.countStats("aeiouAEIOU"));
    }
}
