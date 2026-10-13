package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise03_StringStatsTest {
    @Test
    void countStats_Java101IsFun_returns5533() {
        assertArrayEquals(new int[]{5, 5, 3, 3}, Part2Exercise03_StringStats.countStats("Java 101 is fun"));
    }

    @Test
    void countStats_Empty_returnsZeros() {
        assertArrayEquals(new int[]{0, 0, 0, 0}, Part2Exercise03_StringStats.countStats(""));
    }

    @Test
    void countStats_OnlyDigitsAndSpaces_countsCorrectly() {
        assertArrayEquals(new int[]{0, 0, 3, 1}, Part2Exercise03_StringStats.countStats("123 4"));
    }
}
