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
        String output = runApp("4\n");

        assertTrue(output.contains("      LEXICON CONVERTER APP"));
        assertTrue(output.contains("1. Currency Converter"));
        assertTrue(output.contains("2. BMI Converter"));
        assertTrue(output.contains("3. Data Storage Converter"));
        assertTrue(output.contains("4. Exit"));
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
        String output = runApp("9\n4\n");

        assertTrue(output.contains("Invalid choice. Please enter a number between 1 and 4."));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    @Test
    void nonNumericMenuInput_reprompts() {
        String output = runApp("abc\n4\n");

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
    void nanAmount_reprompts() {
        String output = runApp("1\n1\nNaN\n10\nno\n");

        assertTrue(output.contains("Invalid input. Please enter a number."));
        assertTrue(output.contains("Result: 10.00 SEK = 0.92 USD"));
    }

    @Test
    void infiniteAmount_reprompts() {
        String output = runApp("1\n1\nInfinity\n10\nno\n");

        assertTrue(output.contains("Invalid input. Please enter a number."));
        assertTrue(output.contains("Result: 10.00 SEK = 0.92 USD"));
    }

    @Test
    void invalidContinueAnswer_reprompts() {
        String output = runApp("1\n1\n0\nmaybe\nno\n");

        assertTrue(output.contains("Please enter yes or no."));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    // --- BMI Converter tests ---

    @Test
    void bmi_typicalAdult() {
        assertEquals(22.9, ConverterApp.bmi(70, 175), 0.05);
    }

    @Test
    void bmi_zeroHeightHandledByValidationNotFormula() {
        assertEquals(Double.POSITIVE_INFINITY, ConverterApp.bmi(70, 0), 1e-9);
    }

    @Test
    void bmiCategory_boundaries() {
        assertEquals("Underweight", ConverterApp.bmiCategory(18.4));
        assertEquals("Normal", ConverterApp.bmiCategory(18.5));
        assertEquals("Normal", ConverterApp.bmiCategory(24.9));
        assertEquals("Overweight", ConverterApp.bmiCategory(25.0));
        assertEquals("Overweight", ConverterApp.bmiCategory(29.9));
        assertEquals("Elevated", ConverterApp.bmiCategory(30.0));
    }

    @Test
    void bmiConverter_printsFormattedResult() {
        String output = runApp("2\n70\n175\nno\n");

        assertTrue(output.contains("--- BMI Converter ---"));
        assertTrue(output.contains("Result: BMI = 22.9 (Normal)"));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    @Test
    void bmiConverter_zeroWeight_reprompts() {
        String output = runApp("2\n0\n70\n175\nno\n");

        assertTrue(output.contains("Invalid value. Weight must be greater than zero."));
        assertTrue(output.contains("Result: BMI = 22.9 (Normal)"));
    }

    @Test
    void bmiConverter_zeroHeight_reprompts() {
        String output = runApp("2\n70\n0\n175\nno\n");

        assertTrue(output.contains("Invalid value. Height must be greater than zero."));
        assertTrue(output.contains("Result: BMI = 22.9 (Normal)"));
    }

    // --- Data Storage Converter tests ---

    @Test
    void convertStorage_sameUnit_returnsSameValue() {
        assertEquals(5.0, ConverterApp.convertStorage(5, 1, 1), 1e-9);
    }

    @Test
    void convertStorage_mbToKb_multipliesBy1024() {
        assertEquals(1024.0, ConverterApp.convertStorage(1, 2, 1), 1e-9);
    }

    @Test
    void convertStorage_gbToBytes() {
        assertEquals(1073741824.0, ConverterApp.convertStorage(1, 4, 1), 1e-6);
    }

    @Test
    void convertStorage_bytesToMb_dividesBy1024Squared() {
        assertEquals(0.0009765625, ConverterApp.convertStorage(1024, 1, 3), 1e-12);
    }

    @Test
    void storageConverter_printsFormattedResult() {
        String output = runApp("3\n2\n1\n1\nno\n");

        assertTrue(output.contains("--- Data Storage Converter ---"));
        assertTrue(output.contains("Result: 1.00 KB = 1024.00 Bytes"));
        assertTrue(output.endsWith("Goodbye For now!\n"));
    }

    @Test
    void storageConverter_gbToTb_printsFormattedResult() {
        String output = runApp("3\n4\n5\n2048\nno\n");

        assertTrue(output.contains("Result: 2048.00 GB = 2.00 TB"));
    }

    @Test
    void storageConverter_invalidUnit_reprompts() {
        String output = runApp("3\n9\n2\n1\n10\nno\n");

        assertTrue(output.contains("Invalid choice. Please enter a number between 1 and 5."));
        assertTrue(output.contains("Result: 10.00 KB = 10240.00 Bytes"));
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
