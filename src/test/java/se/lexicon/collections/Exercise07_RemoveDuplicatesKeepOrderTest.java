package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise07_RemoveDuplicatesKeepOrderTest {

    @Test
    void removeDuplicates_removesAllDuplicates() {
        var result = Exercise07_RemoveDuplicatesKeepOrder.removeDuplicates(Exercise07_RemoveDuplicatesKeepOrder.numbers());
        assertEquals(6, result.size());
    }

    @Test
    void removeDuplicates_preservesOriginalOrder() {
        var result = Exercise07_RemoveDuplicatesKeepOrder.removeDuplicates(Exercise07_RemoveDuplicatesKeepOrder.numbers());
        assertEquals(List.of(4, 7, 2, 9, 1, 3), result);
    }

    @Test
    void removeDuplicates_noDuplicates_returnsSameElements() {
        assertEquals(List.of(1, 2, 3), Exercise07_RemoveDuplicatesKeepOrder.removeDuplicates(List.of(1, 2, 3)));
    }
}
