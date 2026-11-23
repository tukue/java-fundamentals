package se.lexicon.exceptionhandling;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Exercise5_FinallyBlockTest {
    @Test
    void divideAndReport_withNonzeroDenominator_printsResultAndFinallyMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        Exercise5_FinallyBlock.divideAndReport(20, 4, new PrintStream(output));

        String result = output.toString();
        assertTrue(result.contains("Result: 5"));
        assertTrue(result.contains("Division attempt finished."));
    }

    @Test
    void divideAndReport_withZeroDenominator_printsErrorAndFinallyMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        Exercise5_FinallyBlock.divideAndReport(10, 0, new PrintStream(output));

        String result = output.toString();
        assertTrue(result.contains("Cannot divide by zero."));
        assertTrue(result.contains("Division attempt finished."));
    }
}
