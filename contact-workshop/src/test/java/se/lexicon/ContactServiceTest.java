package se.lexicon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactServiceTest {

    private ContactService service;

    @BeforeEach
    void setUp() {
        service = new ContactService(new ContactRepository());
    }

    @Test
    void add_returnsOkAndContactBecomesListed() {
        var result = service.add("Anna", "Andersson", "070-123 4567", "anna@example.com");

        assertTrue(result.ok());
        assertTrue(result.message().contains("1"));
        assertEquals(1, service.list().size());
        assertTrue(service.search("anna").size() == 1);
    }

    @Test
    void add_rejectsDuplicateEmail() {
        service.add("Anna", "Andersson", "070-123 4567", "anna@example.com");

        var result = service.add("Bo", "Bengtsson", "070-987 6543", "ANNA@example.com");

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
        service.add("Anna", "Andersson", "070-123 4567", "anna@example.com");

        var result = service.update(1, "Anna", "Lindqvist", "070-000 0000", "anna.l@example.com");

        assertTrue(result.ok());
        var stored = service.find(1).orElseThrow();
        assertEquals("Lindqvist", stored.getLastName());
        assertEquals(1, stored.getId());
    }

    @Test
    void update_failsForUnknownId() {
        var result = service.update(42, "Ghost", "Rider", "070-000 0000", "ghost@example.com");

        assertFalse(result.ok());
        assertTrue(result.message().contains("No contact with id 42"));
    }

    @Test
    void update_failsWhenEmailBelongsToAnotherContact() {
        service.add("Anna", "Andersson", "070-123 4567", "anna@example.com");
        service.add("Bo", "Bengtsson", "070-987 6543", "bo@example.com");

        var result = service.update(2, "Bo", "Bengtsson", "070-987 6543", "anna@example.com");

        assertFalse(result.ok());
        assertTrue(result.message().toLowerCase().contains("already in use"));
        assertEquals("bo@example.com", service.find(2).orElseThrow().getEmail());
    }

    @Test
    void delete_removesContactAndReturnsOk() {
        service.add("Anna", "Andersson", "070-123 4567", "anna@example.com");

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
        service.add("Anna", "Andersson", "070-123 4567", "anna@example.com");

        assertTrue(service.search("zzz").isEmpty());
    }
}
