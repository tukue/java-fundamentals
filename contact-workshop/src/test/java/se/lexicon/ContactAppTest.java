package se.lexicon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ContactAppTest {

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

    private static final String CONTACT_LINE =
            "1\tAlex\tSmith\t1234567890\talex.smith@example.com\n";

    @TempDir
    Path tempDir;

    private String runApp(String input) throws Exception {
        var java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
        var classes = Path.of(ContactApp.class.getProtectionDomain().getCodeSource()
                .getLocation().toURI()).toString();
        var outputFile = tempDir.resolve("output.txt");
        var process = new ProcessBuilder(java, "-cp", classes, ContactApp.class.getName())
                .directory(tempDir.toFile()).redirectErrorStream(true)
                .redirectOutput(outputFile.toFile()).start();
        try {
            try (var stdin = process.getOutputStream()) {
                stdin.write(input.getBytes(StandardCharsets.UTF_8));
            }
            assertTrue(process.waitFor(10, TimeUnit.SECONDS), "App did not exit on EOF");
            var output = Files.readString(outputFile);
            assertEquals(0, process.exitValue(), output);
            return output;
        } finally {
            process.destroyForcibly();
        }
    }

    @Test
    void search_exitsCleanlyWhenQueryInputEnds() throws Exception {
        var output = runApp("3\n");
        assertTrue(output.contains("Search name or phone:"));
        assertTrue(output.contains("Saved 0 contact(s)"));
    }

    @Test
    void delete_cancelsWhenConfirmationInputEnds() throws Exception {
        Files.writeString(tempDir.resolve("contacts.txt"), CONTACT_LINE);

        var output = runApp("5\n1\n");

        assertTrue(output.contains("Delete cancelled."));
        assertTrue(output.contains("Saved 1 contact(s)"));
        assertEquals(CONTACT_LINE, Files.readString(tempDir.resolve("contacts.txt")));
    }

    @Test
    void save_warnsWithoutOverwritingWhenContactHasUnsafeField() throws Exception {
        var file = tempDir.resolve("contacts.txt");
        Files.writeString(file, CONTACT_LINE);
        var app = new ContactApp();
        var repositoryField = ContactApp.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        var repository = (ContactRepository) repositoryField.get(app);
        repository.add("Alex\tJane", LAST_NAME, PHONE, EMAIL);
        var storeField = ContactApp.class.getDeclaredField("fileStore");
        storeField.setAccessible(true);
        storeField.set(app, new ContactFileStore(file));
        var save = ContactApp.class.getDeclaredMethod("saveContacts");
        save.setAccessible(true);
        var output = new java.io.ByteArrayOutputStream();
        var previousOut = System.out;
        try (var capture = new java.io.PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            assertDoesNotThrow(() -> save.invoke(app));
        } finally {
            System.setOut(previousOut);
        }
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("contacts were not saved"));
        assertFalse(output.toString(StandardCharsets.UTF_8).contains("Saved 1 contact(s)"));
        assertEquals(CONTACT_LINE, Files.readString(file));
    }

    @Test
    void exit_reportsWriteFailureWithoutClaimingSuccess() throws Exception {
        Files.createDirectory(tempDir.resolve("contacts.txt"));
        var output = runApp("0\n");
        assertTrue(output.contains("Warning: could not read"));
        assertTrue(output.contains("Warning: could not write"));
        assertTrue(output.contains("Goodbye!"));
        assertFalse(output.contains("Saved 0 contact(s)"));
    }

    @Test
    void export_writesCsvWithHeaderUsingDefaultName() throws Exception {
        Files.writeString(tempDir.resolve("contacts.txt"), CONTACT_LINE);

        var output = runApp("6\n\n0\n");

        assertTrue(output.contains("Exported 1 contact(s)"));
        var exports = listExports();
        assertEquals(1, exports.size());
        var lines = Files.readAllLines(exports.get(0));
        assertEquals("id,firstName,lastName,phone,email", lines.get(0));
        assertEquals("1,Alex,Smith,1234567890,alex.smith@example.com", lines.get(1));
    }

    @Test
    void export_addsCsvSuffixToPathWithoutIt() throws Exception {
        var output = runApp("6\nbackup\n0\n");

        assertTrue(output.contains("backup.csv"));
        assertTrue(Files.exists(tempDir.resolve("backup.csv")));
    }

    @Test
    void import_readsCsvAndSavesImportedContacts() throws Exception {
        var csv = tempDir.resolve("incoming.csv");
        Files.writeString(csv, """
                id,firstName,lastName,phone,email
                3,Alice,Bob,0987654321,alice.bob@example.com
                """);

        var output = runApp("7\n" + csv.toAbsolutePath() + "\n0\n");

        assertTrue(output.contains("Imported 1 contact(s)"));
        assertTrue(Files.readString(tempDir.resolve("contacts.txt"))
                .contains("3\tAlice\tBob\t0987654321\talice.bob@example.com"));
    }

    @Test
    void import_reportsMissingFileWithoutCrashing() throws Exception {
        var output = runApp("7\n" + tempDir.resolve("missing.csv").toAbsolutePath() + "\n0\n");

        assertTrue(output.contains("Warning:"));
        assertTrue(output.contains("Imported 0 contact(s)"));
        assertTrue(output.contains("Goodbye!"));
    }

    private java.util.List<Path> listExports() throws Exception {
        try (var stream = Files.list(tempDir)) {
            return stream
                    .filter(p -> p.getFileName().toString().startsWith("contacts-export-"))
                    .filter(p -> p.getFileName().toString().endsWith(".csv"))
                    .toList();
        }
    }
}
