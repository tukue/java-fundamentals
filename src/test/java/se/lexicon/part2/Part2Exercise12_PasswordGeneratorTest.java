package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise12_PasswordGeneratorTest {
    @Test
    void generatePassword_Length10_returns10Chars() {
        assertEquals(10, Part2Exercise12_PasswordGenerator.generatePassword(10).length());
    }

    @Test
    void generatePassword_OnlyUsesCharset() {
        var password = Part2Exercise12_PasswordGenerator.generatePassword(50);
        assertTrue(password.matches("[A-Za-z0-9]+"));
    }

    @Test
    void generatePassword_Length0_returnsEmpty() {
        assertEquals("", Part2Exercise12_PasswordGenerator.generatePassword(0));
    }

    @Test
    void generatePassword_NegativeLength_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> Part2Exercise12_PasswordGenerator.generatePassword(-1));
    }
}
