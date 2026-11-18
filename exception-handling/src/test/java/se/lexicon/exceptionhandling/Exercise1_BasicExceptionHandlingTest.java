package se.lexicon.exceptionhandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Exercise1_BasicExceptionHandlingTest {
    @Test
    void divide_withNonzeroDenominator_returnsQuotient() {
        assertEquals(4, Exercise1_BasicExceptionHandling.divide(20, 5));
    }

    @Test
    void divide_withZeroDenominator_throwsArithmeticException() {
        assertThrows(ArithmeticException.class,
                () -> Exercise1_BasicExceptionHandling.divide(10, 0));
    }
}
