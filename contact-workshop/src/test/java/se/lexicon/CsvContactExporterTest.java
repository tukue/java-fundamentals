package se.lexicon;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvContactExporterTest {

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

    private final CsvContactExporter exporter = new CsvContactExporter();

    @Test
    void extension_isCsv() {
        assertEquals("csv", exporter.extension());
    }

    @Test
    void lines_writesHeaderAndOneRowPerContact() {
        var lines = exporter.lines(List.of(
                new Contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL),
                new Contact(2, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL)));

        assertEquals(List.of(
                "id,firstName,lastName,phone,email",
                "1,Alex,Smith,1234567890,alex.smith@example.com",
                "2,Alice,Bob,0987654321,alice.bob@example.com"), lines);
    }

    @Test
    void lines_quotesFieldContainingComma() {
        var lines = exporter.lines(
                List.of(new Contact(1, FIRST_NAME, "Smith, Jr", PHONE, EMAIL)));

        assertEquals("1,Alex,\"Smith, Jr\",1234567890,alex.smith@example.com", lines.get(1));
    }

    @Test
    void lines_doublesInnerQuotes() {
        var lines = exporter.lines(
                List.of(new Contact(1, FIRST_NAME, "Smith\"s", PHONE, EMAIL)));

        assertEquals("1,Alex,\"Smith\"\"s\",1234567890,alex.smith@example.com", lines.get(1));
    }

    @Test
    void lines_writesOnlyHeaderForEmptyList() {
        assertEquals(List.of("id,firstName,lastName,phone,email"), exporter.lines(List.of()));
    }
}
