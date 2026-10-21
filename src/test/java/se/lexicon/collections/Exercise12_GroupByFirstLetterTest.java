package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Exercise12_GroupByFirstLetterTest {

    @Test
    void groupByFirstLetter_createsThreeGroups() {
        var groups = Exercise12_GroupByFirstLetter.groupByFirstLetter(Exercise12_GroupByFirstLetter.names());
        assertEquals(3, groups.size());
        assertTrue(groups.containsKey('A'));
        assertTrue(groups.containsKey('B'));
        assertTrue(groups.containsKey('C'));
    }

    @Test
    void groupByFirstLetter_groupsCorrectly() {
        Map<Character, List<String>> groups =
                Exercise12_GroupByFirstLetter.groupByFirstLetter(Exercise12_GroupByFirstLetter.names());
        assertEquals(List.of("Alice", "Anna", "Amanda"), groups.get('A'));
        assertEquals(List.of("Bob", "Brian"), groups.get('B'));
        assertEquals(List.of("Charlie", "Carl"), groups.get('C'));
    }

    @Test
    void groupByFirstLetter_emptyInput_returnsEmptyMap() {
        assertTrue(Exercise12_GroupByFirstLetter.groupByFirstLetter(List.of()).isEmpty());
    }
}
