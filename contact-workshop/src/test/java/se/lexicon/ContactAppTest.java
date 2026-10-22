package se.lexicon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ContactAppTest {
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
        var contacts = "1\tAlex\tSmith\t0123\talex@example.com\n";
        Files.writeString(tempDir.resolve("contacts.txt"), contacts);

        var output = runApp("5\n1\n");

        assertTrue(output.contains("Delete cancelled."));
        assertTrue(output.contains("Saved 1 contact(s)"));
        assertEquals(contacts, Files.readString(tempDir.resolve("contacts.txt")));
    }

    @Test
    void save_warnsWithoutOverwritingWhenContactHasUnsafeField() throws Exception {
        var original = "1\tAlex\tSmith\t0123\talex@example.com\n";
        var file = tempDir.resolve("contacts.txt");
        Files.writeString(file, original);
        var app = new ContactApp();
        var repositoryField = ContactApp.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        var repository = (ContactRepository) repositoryField.get(app);
        repository.add("Alex\tJane", "Smith", "0123", "alex@example.com");
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
        assertEquals(original, Files.readString(file));
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
}
