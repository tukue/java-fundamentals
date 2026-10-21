package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Exercise11_StudentGradesTest {

    @Test
    void createGrades_hasAtLeastSixStudents() {
        assertTrue(Exercise11_StudentGrades.createGrades().size() >= 6);
    }

    @Test
    void gradeOf_existingStudent_returnsGrade() {
        assertEquals(92, Exercise11_StudentGrades.gradeOf(Exercise11_StudentGrades.createGrades(), "Alice"));
    }

    @Test
    void gradeOf_unknownStudent_returnsNull() {
        assertNull(Exercise11_StudentGrades.gradeOf(Exercise11_StudentGrades.createGrades(), "Zoe"));
    }

    @Test
    void sortedByGrade_isDescending() {
        var sorted = Exercise11_StudentGrades.sortedByGrade(Exercise11_StudentGrades.createGrades());
        assertEquals("Olivia", sorted.get(0).getKey());
        assertEquals(99, sorted.get(0).getValue());
        assertEquals("Bob", sorted.get(sorted.size() - 1).getKey());
        for (var i = 1; i < sorted.size(); i++) {
            assertTrue(sorted.get(i - 1).getValue() >= sorted.get(i).getValue());
        }
    }

    @Test
    void sortedByGrade_keepsAllStudents() {
        List<Map.Entry<String, Integer>> sorted =
                Exercise11_StudentGrades.sortedByGrade(Exercise11_StudentGrades.createGrades());
        assertEquals(Exercise11_StudentGrades.createGrades().size(), sorted.size());
    }
}
