package se.lexicon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactServiceTest {

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

    private ContactService service;

    @BeforeEach
    void setUp() {
        service = new ContactService(new ContactRepository());
    }

    @Test
    void add_returnsOkAndContactBecomesListed() {
        var result = service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL);

        assertTrue(result.ok());
        assertTrue(result.message().contains("1"));
        assertEquals(1, service.list().size());
        assertEquals(1, service.search("alex").size());
    }

    @Test
    void add_rejectsDuplicateEmail() {
        service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL);

        var result = service.add(OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, EMAIL.toUpperCase());

        assertFalse(result.ok());
        assertTrue(result.message().toLowerCase().contains("already in use"));
        assertEquals(1, service.list().size());
    }

    @Test
    void add_acceptsAnyInputWithoutFormatValidation() {
        var result = service.add("", "", "", "not-an-email");

        assertTrue(result.ok());
        assertEquals(1, service.list().size());
    }

    @Test
    void update_returnsOkAndAppliesChanges() {
        service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL);

        var result = service.update(1, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);

        assertTrue(result.ok());

        var stored = service.find(1).orElseThrow();
        assertEquals(1, stored.getId());
        assertEquals(OTHER_FIRST_NAME, stored.getFirstName());
        assertEquals(OTHER_LAST_NAME, stored.getLastName());
        assertEquals(OTHER_PHONE, stored.getPhone());
        assertEquals(OTHER_EMAIL, stored.getEmail());
    }

    @Test
    void update_failsForUnknownId() {
        var result = service.update(42, FIRST_NAME, LAST_NAME, PHONE, EMAIL);

        assertFalse(result.ok());
        assertTrue(result.message().contains("No contact with id 42"));
    }

    @Test
    void update_failsWhenEmailBelongsToAnotherContact() {
        service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL);
        service.add(OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);

        var result = service.update(2, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, EMAIL);

        assertFalse(result.ok());
        assertTrue(result.message().toLowerCase().contains("already in use"));
        assertEquals(OTHER_EMAIL, service.find(2).orElseThrow().getEmail());
    }

    @Test
    void delete_removesContactAndReturnsOk() {
        service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL);

        var result = service.delete(1);

        assertTrue(result.ok());
        assertTrue(service.find(1).isEmpty());
        assertTrue(service.list().isEmpty());
    }

    @Test
    void delete_failsForUnknownId() {
        var result = service.delete(99);

        assertFalse(result.ok());
        assertTrue(result.message().contains("No contact with id 99"));
    }

    @Test
    void list_isEmptyWhenNothingAdded() {
        assertTrue(service.list().isEmpty());
    }

    @Test
    void search_returnsEmptyListWhenNothingMatches() {
        service.add(FIRST_NAME, LAST_NAME, PHONE, EMAIL);

        assertTrue(service.search("zzz").isEmpty());
    }
}
