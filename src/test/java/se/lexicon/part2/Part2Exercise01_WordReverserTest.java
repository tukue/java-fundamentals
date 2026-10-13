package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise01_WordReverserTest {
    @Test
    void reverse_Stockholm_returnsReversed() {
        assertEquals("mlohkcotS", Part2Exercise01_WordReverser.reverse("Stockholm"));
    }

    @Test
    void reverse_Empty_returnsEmpty() {
        assertEquals("", Part2Exercise01_WordReverser.reverse(""));
    }

    @Test
    void reverse_SingleChar_returnsSame() {
        assertEquals("a", Part2Exercise01_WordReverser.reverse("a"));
    }

    @Test
    void reverse_MixedCaseAndDigits_preservesCharacters() {
        assertEquals("321bA", Part2Exercise01_WordReverser.reverse("Ab123"));
    }

    @Test
    void reverse_Palindrome_returnsSame() {
        assertEquals("level", Part2Exercise01_WordReverser.reverse("level"));
    }
}
