package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise17_Top3MostFrequentTest {

    @Test
    void countAll_countsEveryWord() {
        var all = Exercise17_Top3MostFrequent.countAll(Exercise17_Top3MostFrequent.words());
        assertEquals(4, all.stream().filter(w -> w.word().equals("java")).findFirst().orElseThrow().count());
        assertEquals(3, all.stream().filter(w -> w.word().equals("python")).findFirst().orElseThrow().count());
        assertEquals(2, all.stream().filter(w -> w.word().equals("c++")).findFirst().orElseThrow().count());
        assertEquals(2, all.stream().filter(w -> w.word().equals("ruby")).findFirst().orElseThrow().count());
    }

    @Test
    void topN_returnsThreeWordsMostFrequentFirst() {
        var top = Exercise17_Top3MostFrequent.topN(Exercise17_Top3MostFrequent.words(), 3);
        assertEquals(List.of("java", "python", "c++"),
                top.stream().map(WordCount::word).toList());
        assertEquals(List.of(4, 3, 2),
                top.stream().map(WordCount::count).toList());
    }

    @Test
    void topN_countsAreDescending() {
        var top = Exercise17_Top3MostFrequent.topN(Exercise17_Top3MostFrequent.words(), 4);
        for (var i = 1; i < top.size(); i++) {
            assertTrue(top.get(i - 1).count() >= top.get(i).count());
        }
    }

    @Test
    void topN_requestingMoreThanAvailable_returnsAll() {
        assertEquals(4, Exercise17_Top3MostFrequent.topN(Exercise17_Top3MostFrequent.words(), 10).size());
    }
}
