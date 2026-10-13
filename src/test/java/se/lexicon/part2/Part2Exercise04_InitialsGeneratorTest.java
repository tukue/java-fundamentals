package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise04_InitialsGeneratorTest {
    @Test
    void generateInitials_TwoWords_returnsJP() {
        assertEquals("J.P.", Part2Exercise04_InitialsGenerator.generateInitials("java programming"));
    }

    @Test
    void generateInitials_ThreeNames_returnsThreeInitials() {
        assertEquals("L.I.D.", Part2Exercise04_InitialsGenerator.generateInitials("lorem ipsum dolor"));
    }

    @Test
    void generateInitials_ExtraSpaces_stillWorks() {
        assertEquals("H.W.", Part2Exercise04_InitialsGenerator.generateInitials("  hello   world  "));
    }
}
