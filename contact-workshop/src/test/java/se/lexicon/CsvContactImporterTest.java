package se.lexicon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvContactImporterTest {

    private static final String FIRST_NAME = "Alex";
    private static final String LAST_NAME = "Smith";
    private static final String PHONE = "1234567890";
    private static final String EMAIL_DOMAIN = "example.com";
    private static final String EMAIL = emailFor(FIRST_NAME, LAST_NAME);

    private static final String OTHER_FIRST_NAME = "Alice";
    private static final String OTHER_LAST_NAME = "Bob";
    private static final String OTHER_PHONE = "0987654321";
    private static final String OTHER_EMAIL = emailFor(OTHER_FIRST_NAME, OTHER_LAST_NAME);

    private static String emailFor(String firstName, String lastName) {
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + EMAIL_DOMAIN;
    }

    private final CsvContactImporter importer = new CsvContactImporter();

    private static List<String> csv(String... rows) {
        return List.of(rows);
    }

    @Test
    void parse_importsValidRowsWithSameFields() {
        var outcome = importer.parse(csv(
                "id,firstName,lastName,phone,email",
                "1,Alex,Smith,1234567890,alex.smith@example.com",
                "2,Alice,Bob,0987654321,alice.bob@example.com"));

        assertTrue(outcome.warnings().isEmpty());
        assertEquals(2, outcome.contacts().size());
        var first = outcome.contacts().get(0);
        assertEquals(1, first.getId());
        assertEquals(FIRST_NAME, first.getFirstName());
        assertEquals(LAST_NAME, first.getLastName());
        assertEquals(PHONE, first.getPhone());
        assertEquals(EMAIL, first.getEmail());
        assertEquals(OTHER_EMAIL, outcome.contacts().get(1).getEmail());
    }

    @Test
    void parse_mapsColumnsByNameWhenReordered() {
        var outcome = importer.parse(csv(
                "email,phone,lastName,id,firstName",
                "alex.smith@example.com,1234567890,Smith,5,Alex"));

        assertTrue(outcome.warnings().isEmpty());
        var contact = outcome.contacts().get(0);
        assertEquals(5, contact.getId());
        assertEquals(FIRST_NAME, contact.getFirstName());
        assertEquals(LAST_NAME, contact.getLastName());
        assertEquals(PHONE, contact.getPhone());
        assertEquals(EMAIL, contact.getEmail());
    }

    @ParameterizedTest
    @ValueSource(strings = {"id", "firstName", "lastName", "phone", "email"})
    void parse_reportsMissingColumnByName(String missing) {
        var header = CsvContactExporter.COLUMNS.stream()
                .filter(column -> !column.equals(missing))
                .collect(Collectors.joining(","));

        var outcome = importer.parse(csv(header, "1,Alex,Smith,1234567890,alex.smith@example.com"));

        assertTrue(outcome.contacts().isEmpty());
        assertTrue(outcome.warnings().contains("Missing column: " + missing));
    }

    @Test
    void parse_skipsRowWithBadPhoneAndImportsTheRest() {
        var outcome = importer.parse(csv(
                "id,firstName,lastName,phone,email",
                "1,Alex,Smith,123-456-abc,alex.smith@example.com",
                "2,Alice,Bob,0987654321,alice.bob@example.com"));

        assertEquals(1, outcome.contacts().size());
        assertEquals(OTHER_FIRST_NAME, outcome.contacts().get(0).getFirstName());
        assertEquals(1, outcome.warnings().size());
        assertTrue(outcome.warnings().get(0).startsWith("Row 2:"));
        assertTrue(outcome.warnings().get(0).contains("digits only"));
    }

    static Stream<Arguments> rowsFailingValidation() {
        return Stream.of(
                Arguments.of("1,,Smith,1234567890,alex.smith@example.com",
                        "Row 2: first name is required"),
                Arguments.of("1,Alex,,1234567890,alex.smith@example.com",
                        "Row 2: last name is required"),
                Arguments.of("1,Alex,Smith,,alex.smith@example.com",
                        "Row 2: phone number is required"),
                Arguments.of("1,Alex,Smith,abc,alex.smith@example.com",
                        "Row 2: phone number must contain digits only"),
                Arguments.of("1,123,Smith,1234567890,alex.smith@example.com",
                        "Row 2: first name must be text, not numbers"),
                Arguments.of("1,Alex,456,1234567890,alex.smith@example.com",
                        "Row 2: last name must be text, not numbers"),
                Arguments.of("1,Alex,Smith,1234567890,   ",
                        "Row 2: email is required"),
                Arguments.of("1,Alex,Smith,1234567890,alex.smith@example",
                        "Row 2: email must have a valid address format"),
                Arguments.of("1,Alex\tJane,Smith,1234567890,alex.smith@example.com",
                        "Row 2: contact fields must not contain tabs or line breaks"),
                Arguments.of("1,Alex\nJane,Smith,1234567890,alex.smith@example.com",
                        "Row 2: contact fields must not contain tabs or line breaks"),
                Arguments.of("1,Alex\rJane,Smith,1234567890,alex.smith@example.com",
                        "Row 2: contact fields must not contain tabs or line breaks"));
    }

    @ParameterizedTest
    @MethodSource("rowsFailingValidation")
    void parse_skipsEveryRowThatFailsAValidationRuleWithWarning(String row, String expectedWarning) {
        var outcome = importer.parse(csv("id,firstName,lastName,phone,email", row));

        assertTrue(outcome.contacts().isEmpty());
        assertEquals(List.of(expectedWarning), outcome.warnings());
    }

    @Test
    void parse_skipsRowWithUnclosedQuote() {
        var outcome = importer.parse(csv(
                "id,firstName,lastName,phone,email",
                "1,\"Alex,Smith,1234567890,alex.smith@example.com",
                "2,Alice,Bob,0987654321,alice.bob@example.com"));

        assertEquals(1, outcome.contacts().size());
        assertEquals(OTHER_FIRST_NAME, outcome.contacts().get(0).getFirstName());
        assertTrue(outcome.warnings().get(0).startsWith("Row 2:"));
        assertTrue(outcome.warnings().get(0).contains("malformed quoting"));
    }

    @Test
    void parse_reportsEmptyFile() {
        var outcome = importer.parse(List.of());

        assertTrue(outcome.contacts().isEmpty());
        assertTrue(outcome.warnings().contains("File had no rows"));
    }

    @Test
    void parse_headerOnlyFileImportsNothingWithoutWarnings() {
        var outcome = importer.parse(csv("id,firstName,lastName,phone,email"));

        assertTrue(outcome.contacts().isEmpty());
        assertTrue(outcome.warnings().isEmpty());
    }

    @Test
    void parse_skipsBlankLines() {
        var outcome = importer.parse(csv(
                "id,firstName,lastName,phone,email",
                "",
                "1,Alex,Smith,1234567890,alex.smith@example.com"));

        assertEquals(1, outcome.contacts().size());
        assertTrue(outcome.warnings().isEmpty());
    }

    @Test
    void parse_trimsQuotedFields() {
        var outcome = importer.parse(csv(
                "id,firstName,lastName,phone,email",
                "1,\" Alex \",\" Smith \",\" 1234567890 \",\" alex.smith@example.com \""));

        var contact = outcome.contacts().get(0);
        assertEquals(FIRST_NAME, contact.getFirstName());
        assertEquals(LAST_NAME, contact.getLastName());
        assertEquals(PHONE, contact.getPhone());
        assertEquals(EMAIL, contact.getEmail());
    }

    @Test
    void parse_handlesDoubledQuotesAndCommasInsideQuotedFields() {
        var outcome = importer.parse(csv(
                "id,firstName,lastName,phone,email",
                "1,\"He said \"\"hi\"\"\",\"Smith, John\",1234567890,alex.smith@example.com"));

        assertTrue(outcome.warnings().isEmpty());
        var contact = outcome.contacts().get(0);
        assertEquals("He said \"hi\"", contact.getFirstName());
        assertEquals("Smith, John", contact.getLastName());
    }

    @Test
    void parse_treatsBlankZeroOrNonNumericIdAsNoPreferredId() {
        var outcome = importer.parse(csv(
                "id,firstName,lastName,phone,email",
                "abc,Alex,Smith,1234567890,alex.smith@example.com",
                ",Alice,Bob,0987654321,alice.bob@example.com",
                "-3,Alex,Smith,1234567890,alex.smith@example.com",
                "0,Alice,Bob,0987654321,alice.bob@example.com"));

        assertTrue(outcome.warnings().isEmpty());
        assertEquals(4, outcome.contacts().size());
        assertTrue(outcome.contacts().stream().allMatch(contact -> contact.getId() == 0));
    }

    @Test
    void parse_skipsRowWithTooFewFields() {
        var outcome = importer.parse(csv(
                "id,firstName,lastName,phone,email",
                "1,Alex,Smith",
                "2,Alice,Bob,0987654321,alice.bob@example.com"));

        assertEquals(1, outcome.contacts().size());
        assertTrue(outcome.warnings().get(0).startsWith("Row 2:"));
        assertTrue(outcome.warnings().get(0).contains("wrong number of fields"));
    }
}
