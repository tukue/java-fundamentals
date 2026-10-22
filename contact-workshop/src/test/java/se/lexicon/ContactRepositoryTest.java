package se.lexicon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactRepositoryTest {

    private ContactRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ContactRepository();
    }

    private Contact add(String first, String last, String phone, String email) {
        return repository.add(first, last, phone, email);
    }

    @Test
    void add_storesContactAndAssignsSequentialIds() {
        var first = add("Anna", "Andersson", "070-123 4567", "anna@example.com");
        var second = add("Bo", "Bengtsson", "070-987 6543", "bo@example.com");

        assertNotNull(first);
        assertNotNull(second);
        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
        assertEquals(2, repository.findAll().size());
        assertEquals("Anna", repository.findById(1).orElseThrow().getFirstName());
    }

    @Test
    void add_rejectsDuplicateEmail() {
        assertNotNull(add("Anna", "Andersson", "070-123 4567", "anna@example.com"));

        var duplicate = add("Anna", "Other", "070-000 0000", "ANNA@example.com");

        assertNull(duplicate);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_returnsContactWhenIdExists() {
        add("Anna", "Andersson", "070-123 4567", "anna@example.com");

        var found = repository.findById(1);

        assertTrue(found.isPresent());
        assertEquals("Andersson", found.get().getLastName());
    }

    @Test
    void findById_returnsEmptyForUnknownId() {
        assertTrue(repository.findById(99).isEmpty());
    }

    @Test
    void findAll_keepsInsertionOrder() {
        add("Anna", "Andersson", "070-1", "anna@example.com");
        add("Bo", "Bengtsson", "070-2", "bo@example.com");

        var all = repository.findAll();

        assertEquals(2, all.size());
        assertEquals("Anna", all.get(0).getFirstName());
        assertEquals("Bo", all.get(1).getFirstName());
    }

    @Test
    void search_matchesFirstNameLastNameAndPhoneIgnoringCase() {
        add("Anna", "Andersson", "070-123 4567", "anna@example.com");
        add("Bo", "Bengtsson", "070-987 6543", "bo@example.com");

        assertEquals(1, repository.search("anna").size());
        assertEquals(1, repository.search("ANDERSSON").size());
        assertEquals(1, repository.search("987").size());
        assertEquals(2, repository.search("").size());
    }

    @Test
    void search_returnsEmptyListWhenNothingMatches() {
        add("Anna", "Andersson", "070-123 4567", "anna@example.com");

        assertTrue(repository.search("zzz").isEmpty());
    }

    @Test
    void update_replacesFieldsAndKeepsId() {
        add("Anna", "Andersson", "070-123 4567", "anna@example.com");

        var updated = repository.update(1, "Anna", "Lindqvist", "070-000 0000", "anna.l@example.com");

        assertTrue(updated.isPresent());
        assertEquals(1, updated.get().getId());
        var stored = repository.findById(1).orElseThrow();
        assertEquals("Lindqvist", stored.getLastName());
        assertEquals("070-000 0000", stored.getPhone());
        assertEquals("anna.l@example.com", stored.getEmail());
    }

    @Test
    void update_returnsEmptyForUnknownId() {
        var updated = repository.update(42, "Ghost", "Rider", "070-000 0000", "ghost@example.com");

        assertTrue(updated.isEmpty());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void update_rejectsEmailAlreadyUsedByAnotherContact() {
        add("Anna", "Andersson", "070-123 4567", "anna@example.com");
        add("Bo", "Bengtsson", "070-987 6543", "bo@example.com");

        var updated = repository.update(2, "Bo", "Bengtsson", "070-987 6543", "anna@example.com");

        assertTrue(updated.isEmpty());
        assertEquals("bo@example.com", repository.findById(2).orElseThrow().getEmail());
    }

    @Test
    void delete_removesContact() {
        add("Anna", "Andersson", "070-123 4567", "anna@example.com");
        add("Bo", "Bengtsson", "070-987 6543", "bo@example.com");

        assertTrue(repository.delete(1));
        assertTrue(repository.findById(1).isEmpty());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void delete_returnsFalseForUnknownIdAndLeavesListUntouched() {
        add("Anna", "Andersson", "070-123 4567", "anna@example.com");

        assertFalse(repository.delete(99));
        assertEquals(1, repository.findAll().size());
    }
}
