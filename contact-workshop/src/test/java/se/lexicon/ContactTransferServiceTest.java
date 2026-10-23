package se.lexicon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactTransferServiceTest {

    private static final String FIRST_NAME = "Alex";
    private static final String LAST_NAME = "Smith";
    private static final String PHONE = "1234567890";
    private static final String EMAIL_DOMAIN = "example.com";
    private static final String EMAIL = emailFor(FIRST_NAME, LAST_NAME);

    private static final String OTHER_FIRST_NAME = "Alice";
    private static final String OTHER_LAST_NAME = "Bob";
    private static final String OTHER_PHONE = "0987654321";
    private static final String OTHER_EMAIL = emailFor(OTHER_FIRST_NAME, OTHER_LAST_NAME);

    private static final String THIRD_FIRST_NAME = "Sam";
    private static final String THIRD_LAST_NAME = "Taylor";
    private static final String THIRD_PHONE = "111222333";
    private static final String THIRD_EMAIL = emailFor(THIRD_FIRST_NAME, THIRD_LAST_NAME);

    private static final String FOURTH_FIRST_NAME = "Kim";
    private static final String FOURTH_LAST_NAME = "Lee";
    private static final String FOURTH_PHONE = "444555666";
    private static final String FOURTH_EMAIL = emailFor(FOURTH_FIRST_NAME, FOURTH_LAST_NAME);

    private static String emailFor(String firstName, String lastName) {
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + EMAIL_DOMAIN;
    }

    @TempDir
    Path tempDir;

    private ContactRepository repository;
    private ContactService service;
    private ContactTransferService transfer;

    @BeforeEach
    void setUp() {
        repository = new ContactRepository();
        service = new ContactService(repository);
        transfer = new ContactTransferService(service);
    }

    @Test
    void exportTo_createsMissingFolderAndWritesCsv() {
        assertTrue(service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL).ok());
        var target = tempDir.resolve("new-folder").resolve("export.csv");

        assertTrue(transfer.exportTo(target, service.list()));

        var lines = readLines(target);
        assertEquals("id,firstName,lastName,phone,email", lines.get(0));
        assertEquals("1,Alex,Smith,1234567890,alex.smith@example.com", lines.get(1));
    }

    @Test
    void exportTo_writesCrlfLineSeparators() throws Exception {
        var target = tempDir.resolve("crlf.csv");

        assertTrue(transfer.exportTo(target, List.of(
                new Contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL))));

        var content = Files.readString(target);
        assertTrue(content.endsWith("\r\n"));
        assertEquals(3, content.split("\r\n", -1).length);
    }

    @Test
    void exportTo_reportsFailureWithoutCrash() throws Exception {
        var fileInTheWay = Files.createFile(tempDir.resolve("occupied"));

        assertFalse(transfer.exportTo(fileInTheWay.resolve("export.csv"), List.of()));
    }

    @Test
    void exportThenImport_roundTripsSameContacts() {
        assertTrue(service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL).ok());
        assertTrue(service.add(OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL).ok());
        var file = tempDir.resolve("roundtrip.csv");
        assertTrue(transfer.exportTo(file, service.list()));

        var freshService = new ContactService(new ContactRepository());
        var outcome = new ContactTransferService(freshService).importFrom(file);

        assertTrue(outcome.warnings().isEmpty());
        assertEquals(2, outcome.contacts().size());
        assertEquals(2, freshService.list().size());
        var first = freshService.list().get(0);
        assertEquals(1, first.getId());
        assertEquals(FIRST_NAME, first.getFirstName());
        assertEquals(LAST_NAME, first.getLastName());
        assertEquals(PHONE, first.getPhone());
        assertEquals(EMAIL, first.getEmail());
        assertEquals(OTHER_EMAIL, freshService.list().get(1).getEmail());
    }

    @Test
    void importFrom_skipsEmailAlreadyInApp() throws Exception {
        assertTrue(service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL).ok());
        var file = tempDir.resolve("duplicate.csv");
        Files.writeString(file, """
                id,firstName,lastName,phone,email
                9,Alice,Bob,0987654321,alex.smith@example.com
                10,Sam,Taylor,111222333,sam.taylor@example.com
                """);

        var outcome = transfer.importFrom(file);

        assertEquals(1, outcome.contacts().size());
        assertEquals(THIRD_EMAIL, outcome.contacts().get(0).getEmail());
        assertTrue(outcome.warnings().stream().anyMatch(w -> w.contains("already in use")));
        assertEquals(2, service.list().size());
    }

    @Test
    void importFrom_keepsFreeIdsAndGeneratesNewOnesForTakenIds() throws Exception {
        assertTrue(service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL).ok());
        var file = tempDir.resolve("ids.csv");
        Files.writeString(file, """
                id,firstName,lastName,phone,email
                1,Alice,Bob,0987654321,alice.bob@example.com
                7,Sam,Taylor,111222333,sam.taylor@example.com
                """);

        var outcome = transfer.importFrom(file);

        assertEquals(2, outcome.contacts().size());
        var renumbered = outcome.contacts().stream()
                .filter(c -> c.getEmail().equals(OTHER_EMAIL)).findFirst().orElseThrow();
        assertEquals(2, renumbered.getId());
        var kept = outcome.contacts().stream()
                .filter(c -> c.getEmail().equals(THIRD_EMAIL)).findFirst().orElseThrow();
        assertEquals(7, kept.getId());
        assertTrue(service.add(FOURTH_FIRST_NAME, FOURTH_LAST_NAME, FOURTH_PHONE, FOURTH_EMAIL).ok());
        assertEquals(8, service.list().get(3).getId());
    }

    @Test
    void importFrom_missingFileReportsErrorAndLeavesContactsUnchanged() {
        assertTrue(service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL).ok());

        var outcome = transfer.importFrom(tempDir.resolve("nope.csv"));

        assertTrue(outcome.contacts().isEmpty());
        assertTrue(outcome.warnings().stream().anyMatch(w -> w.contains("nope.csv")));
        assertEquals(1, service.list().size());
    }

    @Test
    void importFrom_missingColumnImportsNothing() throws Exception {
        var file = tempDir.resolve("broken.csv");
        Files.writeString(file, "id,firstName,lastName,email\n1,Alex,Smith,alex.smith@example.com\n");

        var outcome = transfer.importFrom(file);

        assertTrue(outcome.contacts().isEmpty());
        assertTrue(outcome.warnings().contains("Missing column: phone"));
        assertTrue(service.list().isEmpty());
    }

    @Test
    void importFrom_emptyFileWarnsAndImportsNothing() throws Exception {
        var file = tempDir.resolve("empty.csv");
        Files.writeString(file, "");

        var outcome = transfer.importFrom(file);

        assertTrue(outcome.contacts().isEmpty());
        assertTrue(outcome.warnings().contains("File had no rows"));
        assertTrue(service.list().isEmpty());
    }

    @Test
    void importFrom_combinesParseWarningsWithSkippedDuplicates() throws Exception {
        assertTrue(service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL).ok());
        var file = tempDir.resolve("mixed.csv");
        Files.writeString(file, """
                id,firstName,lastName,phone,email
                2,Alex,Smith,1234567890,alex.smith@example.com
                3,Sam,Taylor,123-456-abc,sam.taylor@example.com
                4,Kim,Lee,444555666,kim.lee@example.com
                """);

        var outcome = transfer.importFrom(file);

        assertEquals(1, outcome.contacts().size());
        assertEquals(FOURTH_EMAIL, outcome.contacts().get(0).getEmail());
        assertEquals(2, outcome.warnings().size());
        assertTrue(outcome.warnings().stream().anyMatch(w -> w.contains("digits only")));
        assertTrue(outcome.warnings().stream().anyMatch(w -> w.contains("already in use")));
    }

    @Test
    void importFrom_headerOnlyFileImportsNothing() throws Exception {
        var file = tempDir.resolve("header.csv");
        Files.writeString(file, "id,firstName,lastName,phone,email\r\n");

        var outcome = transfer.importFrom(file);

        assertTrue(outcome.contacts().isEmpty());
        assertTrue(outcome.warnings().isEmpty());
    }

    @Test
    void importFrom_allowsDuplicateRowsInsideTheFileOnlyOnce() throws Exception {
        var file = tempDir.resolve("self-dup.csv");
        Files.writeString(file, """
                id,firstName,lastName,phone,email
                1,Alex,Smith,1234567890,alex.smith@example.com
                2,Alice,Bob,0987654321,ALEX.SMITH@EXAMPLE.COM
                """);

        var outcome = transfer.importFrom(file);

        assertEquals(1, outcome.contacts().size());
        assertEquals(EMAIL, outcome.contacts().get(0).getEmail());
        assertTrue(outcome.warnings().stream().anyMatch(w -> w.contains("already in use")));
    }

    private static List<String> readLines(Path file) {
        try {
            return Files.readAllLines(file);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
