package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise16_SecondLargestTest {

    @Test
    void secondLargest_givenNumbers_returns78() {
        assertEquals(78, Exercise16_SecondLargest.secondLargest(Exercise16_SecondLargest.numbers()));
    }

    @Test
    void secondLargest_duplicatesOfLargest_ignored() {
        assertEquals(10, Exercise16_SecondLargest.secondLargest(List.of(10, 20, 20, 20)));
    }

    @Test
    void secondLargest_allSameValues_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Exercise16_SecondLargest.secondLargest(List.of(5, 5, 5)));
    }

    @Test
    void secondLargest_tooFewNumbers_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Exercise16_SecondLargest.secondLargest(List.of(5)));
    }
}
