package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Exercise03_PhoneBookTest {

    @Test
    void createPhoneBook_hasAtLeastFourContacts() {
        assertTrue(Exercise03_PhoneBook.createPhoneBook().size() >= 4);
    }

    @Test
    void lookup_existingName_returnsNumber() {
        var book = Exercise03_PhoneBook.createPhoneBook();
        assertEquals("070-1112233", Exercise03_PhoneBook.lookup(book, "Anna"));
    }

    @Test
    void lookup_unknownName_returnsNull() {
        var book = Exercise03_PhoneBook.createPhoneBook();
        assertNull(Exercise03_PhoneBook.lookup(book, "Zoe"));
    }
}
