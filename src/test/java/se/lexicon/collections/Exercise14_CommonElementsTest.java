package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise14_CommonElementsTest {

    @Test
    void commonElements_returnsSharedValuesOnce() {
        var common = Exercise14_CommonElements.commonElements(Exercise14_CommonElements.listA(),
                Exercise14_CommonElements.listB());
        assertEquals(List.of(3, 9), List.copyOf(common));
    }

    @Test
    void commonElements_noOverlap_returnsEmptySet() {
        assertTrue(Exercise14_CommonElements.commonElements(List.of(1, 2), List.of(3, 4)).isEmpty());
    }

    @Test
    void commonElements_duplicatesInInput_resultHasNoDuplicates() {
        var common = Exercise14_CommonElements.commonElements(List.of(5, 5, 5), List.of(5, 5));
        assertEquals(1, common.size());
    }
}
