package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Optional02_LongestConsecutiveSequenceTest {

    @Test
    void longestConsecutive_sampleInput_returnsFour() {
        assertEquals(4, Optional02_LongestConsecutiveSequence.longestConsecutive(
                Optional02_LongestConsecutiveSequence.sample()));
    }

    @Test
    void longestConsecutive_emptyList_returnsZero() {
        assertEquals(0, Optional02_LongestConsecutiveSequence.longestConsecutive(List.of()));
    }

    @Test
    void longestConsecutive_singleElement_returnsOne() {
        assertEquals(1, Optional02_LongestConsecutiveSequence.longestConsecutive(List.of(42)));
    }

    @Test
    void longestConsecutive_ignoresDuplicates() {
        assertEquals(3, Optional02_LongestConsecutiveSequence.longestConsecutive(List.of(1, 1, 2, 2, 3, 3)));
    }

    @Test
    void longestConsecutive_negativeNumbers() {
        assertEquals(3, Optional02_LongestConsecutiveSequence.longestConsecutive(List.of(-2, -1, 0, 5)));
    }

    @Test
    void longestConsecutive_sortedDescending_stillFindsSequence() {
        assertEquals(5, Optional02_LongestConsecutiveSequence.longestConsecutive(List.of(9, 5, 4, 3, 2, 1)));
    }

    @Test
    void longestConsecutive_noConsecutiveValues_returnsOne() {
        assertEquals(1, Optional02_LongestConsecutiveSequence.longestConsecutive(List.of(10, 30, 50, 70)));
    }

    @Test
    void longestConsecutive_picksLongestOfSeveralRuns() {
        assertEquals(4, Optional02_LongestConsecutiveSequence.longestConsecutive(
                List.of(1, 2, 10, 11, 12, 13, 100)));
    }
}
