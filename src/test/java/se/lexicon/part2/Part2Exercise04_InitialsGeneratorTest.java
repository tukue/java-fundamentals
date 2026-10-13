package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise04_InitialsGeneratorTest {
    @Test
    void generateInitials_TwoNames_returnsJD() {
        assertEquals("J.D.", Part2Exercise04_InitialsGenerator.generateInitials("john doe"));
    }

    @Test
    void generateInitials_ThreeNames_returnsThreeInitials() {
        assertEquals("A.B.C.", Part2Exercise04_InitialsGenerator.generateInitials("alice bob carter"));
    }

    @Test
    void generateInitials_ExtraSpaces_stillWorks() {
        assertEquals("A.H.", Part2Exercise04_InitialsGenerator.generateInitials("  alice   hill  "));
    }
}
