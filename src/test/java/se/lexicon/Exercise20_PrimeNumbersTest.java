package se.lexicon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Exercise20_PrimeNumbersTest {
    @Test
    void isPrime_2_returnsTrue() {
        assertTrue(Exercise20_PrimeNumbers.isPrime(2));
    }

    @Test
    void isPrime_3_returnsTrue() {
        assertTrue(Exercise20_PrimeNumbers.isPrime(3));
    }

    @Test
    void isPrime_4_returnsFalse() {
        assertFalse(Exercise20_PrimeNumbers.isPrime(4));
    }

    @Test
    void isPrime_1_returnsFalse() {
        assertFalse(Exercise20_PrimeNumbers.isPrime(1));
    }

    @Test
    void isPrime_0_returnsFalse() {
        assertFalse(Exercise20_PrimeNumbers.isPrime(0));
    }

    @Test
    void isPrime_negative_returnsFalse() {
        assertFalse(Exercise20_PrimeNumbers.isPrime(-5));
    }

    @Test
    void primesUpTo50() {
        var expected = "2 3 5 7 11 13 17 19 23 29 31 37 41 43 47";
        var sb = new StringBuilder();
        var first = true;
        for (var i = 2; i <= 50; i++) {
            if (Exercise20_PrimeNumbers.isPrime(i)) {
                if (!first) sb.append(" ");
                sb.append(i);
                first = false;
            }
        }
        assertEquals(expected, sb.toString());
    }
}