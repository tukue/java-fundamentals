package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise02_UniqueCitiesTest {

    @Test
    void uniqueCities_removesDuplicates() {
        var unique = Exercise02_UniqueCities.uniqueCities(Exercise02_UniqueCities.cityNames());
        assertEquals(5, unique.size());
    }

    @Test
    void uniqueCities_keepsAllDistinctCities() {
        var unique = Exercise02_UniqueCities.uniqueCities(Exercise02_UniqueCities.cityNames());
        assertTrue(unique.containsAll(List.of("Stockholm", "Gothenburg", "Malmö", "Uppsala", "Lund")));
    }

    @Test
    void uniqueCities_inputWithNoDuplicates_returnsSameSize() {
        var unique = Exercise02_UniqueCities.uniqueCities(List.of("Oslo", "Copenhagen"));
        assertEquals(2, unique.size());
    }
}
