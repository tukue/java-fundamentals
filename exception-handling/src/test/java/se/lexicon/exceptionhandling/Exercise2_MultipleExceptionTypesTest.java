package se.lexicon.exceptionhandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Exercise2_MultipleExceptionTypesTest {
    @Test
    void validate_withInRangeInteger_returnsValue() throws Exception {
        assertEquals(42, Exercise2_MultipleExceptionTypes.validate("42"));
    }

    @Test
    void validate_withNonInteger_throwsNumberFormatException() {
        assertThrows(NumberFormatException.class,
                () -> Exercise2_MultipleExceptionTypes.validate("hello"));
    }

    @Test
    void validate_withOutOfRangeInteger_throwsCustomException() {
        assertThrows(Exercise2_MultipleExceptionTypes.OutOfRangeException.class,
                () -> Exercise2_MultipleExceptionTypes.validate("101"));
    }
}
