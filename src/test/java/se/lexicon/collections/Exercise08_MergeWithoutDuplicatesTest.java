package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise08_MergeWithoutDuplicatesTest {

    @Test
    void merge_containsAllElementsFromBothLists() {
        var merged = Exercise08_MergeWithoutDuplicates.merge(Exercise08_MergeWithoutDuplicates.listA(),
                Exercise08_MergeWithoutDuplicates.listB());
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), List.copyOf(merged));
    }

    @Test
    void merge_totalCountIsSeven() {
        var merged = Exercise08_MergeWithoutDuplicates.merge(Exercise08_MergeWithoutDuplicates.listA(),
                Exercise08_MergeWithoutDuplicates.listB());
        assertEquals(7, merged.size());
    }

    @Test
    void merge_disjointLists_keepsEverything() {
        var merged = Exercise08_MergeWithoutDuplicates.merge(List.of(1, 2), List.of(3));
        assertEquals(3, merged.size());
    }
}
