package se.lexicon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactRepositoryTest {

    private static final String FIRST_NAME = "Alex";
    private static final String LAST_NAME = "Smith";
    private static final String PHONE = "1234567890";
    private static final String EMAIL_DOMAIN = "example.com";
    private static final String EMAIL = emailFor(FIRST_NAME, LAST_NAME);

    private static final String OTHER_FIRST_NAME = "Sam";
    private static final String OTHER_LAST_NAME = "Taylor";
    private static final String OTHER_PHONE = "0987654321";
    private static final String OTHER_EMAIL = emailFor(OTHER_FIRST_NAME, OTHER_LAST_NAME);

    private static String emailFor(String firstName, String lastName) {
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + EMAIL_DOMAIN;
    }

    private ContactRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ContactRepository();
    }

    private Contact add(String first, String last, String phone, String email) {
        return repository.add(first, last, phone, email);
    }

    private Contact addAlex() {
        return add(FIRST_NAME, LAST_NAME, PHONE, EMAIL);
    }

    private Contact addSam() {
        return add(OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);
    }

    @Test
    void add_storesContactAndAssignsSequentialIds() {
        var first = addAlex();
        var second = addSam();

        assertNotNull(first);
        assertNotNull(second);
        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
        assertEquals(2, repository.findAll().size());

        var stored = repository.findById(1).orElseThrow();
        assertEquals(FIRST_NAME, stored.getFirstName());
        assertEquals(LAST_NAME, stored.getLastName());
        assertEquals(PHONE, stored.getPhone());
        assertEquals(EMAIL, stored.getEmail());
    }

    @Test
    void add_rejectsDuplicateEmail() {
        assertNotNull(addAlex());

        var duplicate = add(OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, EMAIL.toUpperCase());

        assertNull(duplicate);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_returnsContactWhenIdExists() {
        addAlex();

        var found = repository.findById(1);

        assertTrue(found.isPresent());
        assertEquals(FIRST_NAME, found.get().getFirstName());
    }

    @Test
    void findById_returnsEmptyForUnknownId() {
        assertTrue(repository.findById(99).isEmpty());
    }

    @Test
    void findAll_keepsInsertionOrder() {
        addAlex();
        addSam();

        var all = repository.findAll();

        assertEquals(2, all.size());
        assertEquals(FIRST_NAME, all.get(0).getFirstName());
        assertEquals(OTHER_FIRST_NAME, all.get(1).getFirstName());
    }

    @Test
    void search_matchesFirstNameLastNameAndPhoneIgnoringCase() {
        addAlex();
        addSam();

        assertEquals(1, repository.search("alex").size());
        assertEquals(1, repository.search("TAYLOR").size());
        assertEquals(1, repository.search("12345").size());
        assertEquals(2, repository.search("").size());
    }

    @Test
    void search_returnsEmptyListWhenNothingMatches() {
        addAlex();

        assertTrue(repository.search("zzz").isEmpty());
    }

    @Test
    void update_replacesFieldsAndKeepsId() {
        addAlex();

        var updated = repository.update(1, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);

        assertTrue(updated.isPresent());
        assertEquals(1, updated.get().getId());

        var stored = repository.findById(1).orElseThrow();
        assertEquals(OTHER_FIRST_NAME, stored.getFirstName());
        assertEquals(OTHER_LAST_NAME, stored.getLastName());
        assertEquals(OTHER_PHONE, stored.getPhone());
        assertEquals(OTHER_EMAIL, stored.getEmail());
    }

    @Test
    void update_returnsEmptyForUnknownId() {
        var updated = repository.update(42, FIRST_NAME, LAST_NAME, PHONE, EMAIL);

        assertTrue(updated.isEmpty());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void update_rejectsEmailAlreadyUsedByAnotherContact() {
        addAlex();
        addSam();

        var updated = repository.update(2, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, EMAIL);

        assertTrue(updated.isEmpty());
        assertEquals(OTHER_EMAIL, repository.findById(2).orElseThrow().getEmail());
    }

    @Test
    void delete_removesContact() {
        addAlex();
        addSam();

        assertTrue(repository.delete(1));
        assertTrue(repository.findById(1).isEmpty());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void delete_returnsFalseForUnknownIdAndLeavesListUntouched() {
        addAlex();

        assertFalse(repository.delete(99));
        assertEquals(1, repository.findAll().size());
    }
}
