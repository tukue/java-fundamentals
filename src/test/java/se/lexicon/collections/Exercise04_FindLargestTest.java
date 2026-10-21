package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise04_FindLargestTest {

    @Test
    void findLargest_givenNumbers_returns91() {
        assertEquals(91, Exercise04_FindLargest.findLargest(List.of(14, 3, 78, 45, 22, 91, 7, 56)));
    }

    @Test
    void findLargest_singleElement_returnsThatElement() {
        assertEquals(42, Exercise04_FindLargest.findLargest(List.of(42)));
    }

    @Test
    void findLargest_negativeNumbers_returnsHighest() {
        assertEquals(-1, Exercise04_FindLargest.findLargest(List.of(-10, -3, -1, -7)));
    }

    @Test
    void findLargest_emptyList_throws() {
        assertThrows(IllegalArgumentException.class, () -> Exercise04_FindLargest.findLargest(List.of()));
    }
}
