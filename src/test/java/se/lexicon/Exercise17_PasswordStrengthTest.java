package se.lexicon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Exercise17_PasswordStrengthTest {
    @Test
    void password_Hello7_2Rules_Medium() {
        assertEquals(2, rulesMet("Hello7"));
        assertEquals("Medium", rating("Hello7"));
    }

    @Test
    void password_HelloWorld3_3Rules_Strong() {
        assertEquals(3, rulesMet("HelloWorld3"));
        assertEquals("Strong", rating("HelloWorld3"));
    }

    @Test
    void password_hi_1Rule_Weak() {
        assertEquals(1, rulesMet("hi"));
        assertEquals("Weak", rating("hi"));
    }

    @Test
    void password_HELLO3_2Rules_Medium() {
        assertEquals(2, rulesMet("HELLO3"));
        assertEquals("Medium", rating("HELLO3"));
    }

    private int rulesMet(String password) {
        var count = 0;
        if (password.length() >= 8) count++;
        var hasLetter = false;
        var hasDigit = false;
        for (var i = 0; i < password.length(); i++) {
            var ch = password.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) hasLetter = true;
            if (ch >= '0' && ch <= '9') hasDigit = true;
        }
        if (hasLetter) count++;
        if (hasDigit) count++;
        return count;
    }

    private String rating(String password) {
        return switch (rulesMet(password)) {
            case 3 -> "Strong";
            case 2 -> "Medium";
            default -> "Weak";
        };
    }
}