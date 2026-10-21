package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise15_TwoSumTest {

    @Test
    void twoSum_givenNumbersAndTarget13_findsPair() {
        var pair = Exercise15_TwoSum.twoSum(Exercise15_TwoSum.numbers(), 13);
        assertEquals(2, pair.size());
        assertEquals(13, pair.get(0) + pair.get(1));
    }

    @Test
    void twoSum_usesNumbersFromTheList() {
        var numbers = Exercise15_TwoSum.numbers();
        var pair = Exercise15_TwoSum.twoSum(numbers, 13);
        assertTrue(numbers.containsAll(pair));
    }

    @Test
    void twoSum_noPairExists_returnsEmptyList() {
        assertTrue(Exercise15_TwoSum.twoSum(List.of(1, 2, 3), 100).isEmpty());
    }

    @Test
    void twoSum_pairWithSameNumberTwice_findsIt() {
        assertEquals(List.of(6, 6), Exercise15_TwoSum.twoSum(List.of(6, 6), 12));
    }

    @Test
    void twoSum_multipleValidPairs_returnsOneOfThem() {
        var pair = Exercise15_TwoSum.twoSum(List.of(3, 6, 9), 12);
        assertEquals(2, pair.size());
        assertEquals(12, pair.get(0) + pair.get(1));
    }
}
