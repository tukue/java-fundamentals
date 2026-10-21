package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Optional05_SublistWithTargetSumTest {

    private static final List<Integer> SAMPLE = Optional05_SublistWithTargetSum.sample();

    private static List<List<Integer>> values(List<Optional05_SublistWithTargetSum.Sublist> sublists) {
        return sublists.stream().map(Optional05_SublistWithTargetSum.Sublist::values).toList();
    }

    @Test
    void sample_findsTheThreeRealMatches() {
        var found = values(Optional05_SublistWithTargetSum.findSublists(SAMPLE, 6));
        assertEquals(List.of(
                List.of(1, 2, 3),
                List.of(4, 2),
                List.of(1, 5)), found);
    }

    @Test
    void sample_startAndEndIndexesAreCorrect() {
        var found = Optional05_SublistWithTargetSum.findSublists(SAMPLE, 6);
        assertEquals(List.of(0, 3, 5), found.stream().map(Optional05_SublistWithTargetSum.Sublist::start).toList());
        assertEquals(List.of(2, 4, 6), found.stream().map(Optional05_SublistWithTargetSum.Sublist::end).toList());
    }

    @Test
    void exerciseSuggestions_thatDoNotSumToSix_areRejected() {
        var found = values(Optional05_SublistWithTargetSum.findSublists(SAMPLE, 6));
        assertFalse(found.contains(List.of(3, 4, 2, 1)), "3+4+2+1 = 10, not 6");
        assertFalse(found.contains(List.of(2, 1, 5)), "2+1+5 = 8, not 6");
        assertFalse(found.contains(List.of(3, 4, 2)), "3+4+2 = 9, not 6");
    }

    @Test
    void everyReportedSublist_sumsToTarget_andMatchesItsIndexes() {
        var numbers = SAMPLE;
        for (var sublist : Optional05_SublistWithTargetSum.findSublists(numbers, 6)) {
            assertEquals(6, sublist.sum());
            assertEquals(numbers.subList(sublist.start(), sublist.end() + 1), sublist.values());
        }
    }

    @Test
    void noMatch_returnsEmptyList() {
        assertTrue(Optional05_SublistWithTargetSum.findSublists(List.of(1, 2, 3), 100).isEmpty());
    }

    @Test
    void singleElementEqualingTarget_isFound() {
        var found = Optional05_SublistWithTargetSum.findSublists(List.of(7, 1, 7), 7);
        assertEquals(2, found.size());
        assertEquals(List.of(0, 2), found.stream().map(Optional05_SublistWithTargetSum.Sublist::start).toList());
        assertEquals(List.of(0, 2), found.stream().map(Optional05_SublistWithTargetSum.Sublist::end).toList());
    }

    @Test
    void negativeNumbers_areHandled() {
        var found = values(Optional05_SublistWithTargetSum.findSublists(List.of(1, -1, 2, -2, 3), 0));
        assertEquals(List.of(List.of(1, -1), List.of(1, -1, 2, -2), List.of(2, -2)), found);
    }

    @Test
    void zeroTarget_withZeros_findsEveryZeroWindow() {
        var found = values(Optional05_SublistWithTargetSum.findSublists(List.of(0, 1, 0), 0));
        assertEquals(List.of(List.of(0), List.of(0)), found);
    }

    @Test
    void contiguousSegments_only() {
        var numbers = new ArrayList<>(List.of(9, 9, 6, 9));
        var found = Optional05_SublistWithTargetSum.findSublists(numbers, 6);
        assertEquals(1, found.size());
        assertEquals(List.of(6), found.get(0).values());
        assertEquals(2, found.get(0).start());
        assertEquals(2, found.get(0).end());
    }

    @Test
    void printSublists_writesEachMatch() {
        var out = new java.io.ByteArrayOutputStream();
        var original = System.out;
        System.setOut(new java.io.PrintStream(out));
        try {
            Optional05_SublistWithTargetSum.printSublists(SAMPLE, 6);
        } finally {
            System.setOut(original);
        }
        var printed = out.toString();
        assertTrue(printed.contains("Index 0-2: [1, 2, 3]"));
        assertTrue(printed.contains("Index 3-4: [4, 2]"));
        assertTrue(printed.contains("Index 5-6: [1, 5]"));
    }
}
