package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise04_InitialsGeneratorTest {
    @Test
    void generateInitials_TwoNames_returnsMJ() {
        assertEquals("M.J.", Part2Exercise04_InitialsGenerator.generateInitials("mehrdad javan"));
    }

    @Test
    void generateInitials_ThreeNames_returnsThreeInitials() {
        assertEquals("A.M.K.", Part2Exercise04_InitialsGenerator.generateInitials("anna maria karlsson"));
    }

    @Test
    void generateInitials_ExtraSpaces_stillWorks() {
        assertEquals("A.B.", Part2Exercise04_InitialsGenerator.generateInitials("  anna   berg  "));
    }
}
