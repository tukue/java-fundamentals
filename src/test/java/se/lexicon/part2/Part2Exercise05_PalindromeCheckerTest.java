package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise05_PalindromeCheckerTest {
    @Test
    void isPalindrome_Racecar_returnsTrue() {
        assertTrue(Part2Exercise05_PalindromeChecker.isPalindrome("Racecar"));
    }

    @Test
    void isPalindrome_Java_returnsFalse() {
        assertFalse(Part2Exercise05_PalindromeChecker.isPalindrome("Java"));
    }

    @Test
    void isPalindrome_MixedCase_returnsTrue() {
        assertTrue(Part2Exercise05_PalindromeChecker.isPalindrome("LeVeL"));
    }
}
