package se.lexicon.exceptionhandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Exercise6_ThrowingExceptionsTest {
    @Test
    void validateEmail_withValidAddress_returnsAddress() {
        assertEquals("learner@example.com",
                Exercise6_ThrowingExceptions.validateEmail("learner@example.com"));
    }

    @Test
    void validateEmail_withInvalidAddress_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Exercise6_ThrowingExceptions.validateEmail("invalid-email"));
        assertEquals("Please enter a valid email address.", exception.getMessage());
    }
}
