package se.lexicon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Exercise18_SumOfDigitsTest {
    @Test
    void sumOfDigits_1234_returns10() {
        assertEquals(10, Exercise18_SumOfDigits.sumOfDigits(1234));
    }

    @Test
    void sumOfDigits_9_returns9() {
        assertEquals(9, Exercise18_SumOfDigits.sumOfDigits(9));
    }

    @Test
    void sumOfDigits_305_returns8() {
        assertEquals(8, Exercise18_SumOfDigits.sumOfDigits(305));
    }
}