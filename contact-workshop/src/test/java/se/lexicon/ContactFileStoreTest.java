package se.lexicon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContactFileStoreTest {

    private static final String FIRST_NAME = "Alex";
    private static final String LAST_NAME = "Smith";
    private static final String PHONE = "1234567890";
    private static final String EMAIL = "alex.smith@example.com";

    private static final String OTHER_FIRST_NAME = "Alice";
    private static final String OTHER_LAST_NAME = "Bob";
    private static final String OTHER_PHONE = "0987654321";
    private static final String OTHER_EMAIL = "alice.bob@example.com";

    @TempDir
    Path tempDir;

    private Contact contact(int id, String first, String last, String phone, String email) {
        return new Contact(id, first, last, phone, email);
    }

    @Test
    void saveThenLoad_roundTripsAllContacts() {
        var store = new ContactFileStore(tempDir.resolve("contacts.txt"));
        var contacts = List.of(
                contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL),
                contact(2, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL));

        store.save(contacts);
        var loaded = store.load();

        assertEquals(2, loaded.size());
        var first = loaded.get(0);
        assertEquals(1, first.getId());
        assertEquals(FIRST_NAME, first.getFirstName());
        assertEquals(LAST_NAME, first.getLastName());
        assertEquals(PHONE, first.getPhone());
        assertEquals(EMAIL, first.getEmail());
        assertEquals(OTHER_EMAIL, loaded.get(1).getEmail());
    }

    @Test
    void load_returnsEmptyListWhenFileDoesNotExist() {
        var store = new ContactFileStore(tempDir.resolve("missing.txt"));

        assertTrue(store.load().isEmpty());
    }

    @Test
    void load_skipsMalformedLines() throws IOException {
        var file = tempDir.resolve("contacts.txt");
        Files.writeString(file,
                "1\tAlex\tSmith\t1234567890\talex.smith@example.com\n" +
                "not-a-contact-line\n" +
                "2\tAlice\tBob\t0987654321\talice.bob@example.com\n",
                StandardCharsets.UTF_8);
        var store = new ContactFileStore(file);

        var loaded = store.load();

        assertEquals(2, loaded.size());
        assertEquals(FIRST_NAME, loaded.get(0).getFirstName());
        assertEquals(OTHER_FIRST_NAME, loaded.get(1).getFirstName());
    }

    @Test
    void save_rejectsInvalidFieldsWithoutOverwritingFile() throws IOException {
        var store = new ContactFileStore(tempDir.resolve("contacts.txt"));
        var valid = contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL);
        store.save(List.of(valid));
        var previousContents = Files.readString(store.getFile());

        for (var separator : List.of("\t", "\n", "\r")) {
            for (int field = 0; field < 4; field++) {
                var values = new String[]{FIRST_NAME, LAST_NAME, PHONE, EMAIL};
                values[field] += separator + "extra";
                var invalid = contact(2, values[0], values[1], values[2], values[3]);

                assertThrows(IllegalArgumentException.class, () -> store.save(List.of(valid, invalid)));
                assertEquals(previousContents, Files.readString(store.getFile()));
            }
        }
    }

    @Test
    void save_overwritesPreviousContentsInsteadOfAppending() {
        var store = new ContactFileStore(tempDir.resolve("contacts.txt"));

        store.save(List.of(contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL)));
        store.save(List.of(contact(2, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL)));

        var loaded = store.load();
        assertEquals(1, loaded.size());
        assertEquals(2, loaded.get(0).getId());
    }

    @Test
    void save_writesEmptyFileForEmptyList() {
        var store = new ContactFileStore(tempDir.resolve("contacts.txt"));

        store.save(List.of());

        assertTrue(Files.exists(store.getFile()));
        assertTrue(store.load().isEmpty());
    }
    @Test
    void save_reportsFailureWhenTargetIsDirectory() throws IOException {
        var directory = Files.createDirectory(tempDir.resolve("contacts.txt"));
        var store = new ContactFileStore(directory);
        assertFalse(store.save(List.of(contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL))));
        assertTrue(Files.isDirectory(directory));
    }

}
