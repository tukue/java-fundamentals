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

    @Test
    void isPalindrome_SingleChar_returnsTrue() {
        assertTrue(Part2Exercise05_PalindromeChecker.isPalindrome("A"));
    }

    @Test
    void isPalindrome_TwoDifferentChars_returnsFalse() {
        assertFalse(Part2Exercise05_PalindromeChecker.isPalindrome("ab"));
    }

    @Test
    void isPalindrome_Null_returnsFalse() {
        assertFalse(Part2Exercise05_PalindromeChecker.isPalindrome(null));
    }
}
