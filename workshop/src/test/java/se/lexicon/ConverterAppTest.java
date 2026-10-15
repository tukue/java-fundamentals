package se.lexicon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConverterAppTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private String runApp(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ConverterApp.main(new String[0]);
        return outContent.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    // --- Conversion formula tests ---

    @Test
    void sekToUsd_dividesByRate() {
        assertEquals(1.0, ConverterApp.sekToUsd(10.87), 1e-9);
    }

    @Test
    void sekToUsd_thousandCrowns() {
        assertEquals(91.9963, ConverterApp.sekToUsd(1000), 0.0001);
    }

    @Test
    void sekToEur_dividesByRate() {
        assertEquals(88.3392, ConverterApp.sekToEur(1000), 0.0001);
    }

    @Test
    void conversions_zeroAmount_returnsZero() {
        assertEquals(0.0, ConverterApp.sekToUsd(0), 1e-9);
        assertEquals(0.0, ConverterApp.sekToEur(0), 1e-9);
    }

    // --- Console flow tests ---

    @Test
    void exitImmediately_printsMenuAndGoodbye() {
        String output = runApp("2\n");

        assertTrue(output.contains("      LEXICON CONVERTER APP"));
        assertTrue(output.contains("1. Currency Converter"));
        assertTrue(output.contains("2. Exit"));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    @Test
    void convertSekToEur_printsFormattedResult() {
        String output = runApp("1\n2\n1000\nno\n");

        assertTrue(output.contains("--- Currency Converter ---"));
        assertTrue(output.contains("Result: 1000.00 SEK = 88.34 EUR"));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    @Test
    void convertSekToUsd_printsFormattedResult() {
        String output = runApp("1\n1\n10.87\nno\n");

        assertTrue(output.contains("Result: 10.87 SEK = 1.00 USD"));
    }

    @Test
    void yesContinuesToSecondConversion() {
        String output = runApp("1\n1\n10.87\nyes\n1\n2\n113.20\nno\n");

        assertTrue(output.contains("Result: 10.87 SEK = 1.00 USD"));
        assertTrue(output.contains("Result: 113.20 SEK = 10.00 EUR"));
        assertEquals(2, countOccurrences(output, "LEXICON CONVERTER APP"));
    }

    // --- Input validation tests (Challenge 1) ---

    @Test
    void invalidMenuNumber_reprompts() {
        String output = runApp("9\n2\n");

        assertTrue(output.contains("Invalid choice. Please enter a number between 1 and 2."));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    @Test
    void nonNumericMenuInput_reprompts() {
        String output = runApp("abc\n2\n");

        assertTrue(output.contains("Invalid input. Please enter a number."));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    @Test
    void invalidDirection_reprompts() {
        String output = runApp("1\n5\n2\n1000\nno\n");

        assertTrue(output.contains("Invalid choice. Please enter a number between 1 and 2."));
        assertTrue(output.contains("Result: 1000.00 SEK = 88.34 EUR"));
    }

    @Test
    void negativeAmount_reprompts() {
        String output = runApp("1\n1\n-5\n10\nno\n");

        assertTrue(output.contains("Invalid value. Currency amount cannot be negative."));
        assertTrue(output.contains("Result: 10.00 SEK = 0.92 USD"));
    }

    @Test
    void nonNumericAmount_reprompts() {
        String output = runApp("1\n1\nabc\n10\nno\n");

        assertTrue(output.contains("Invalid input. Please enter a number."));
        assertTrue(output.contains("Result: 10.00 SEK = 0.92 USD"));
    }

    @Test
    void invalidContinueAnswer_reprompts() {
        String output = runApp("1\n1\n0\nmaybe\nno\n");

        assertTrue(output.contains("Please enter yes or no."));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    private int countOccurrences(String text, String needle) {
        int count = 0;
        int index = text.indexOf(needle);
        while (index != -1) {
            count++;
            index = text.indexOf(needle, index + needle.length());
        }
        return count;
    }
}
