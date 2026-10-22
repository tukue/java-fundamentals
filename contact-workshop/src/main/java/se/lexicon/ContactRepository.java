package se.lexicon;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ContactRepository {

    public static final int NO_EXCLUDED_ID = -1;

    private final Map<Integer, Contact> contacts = new LinkedHashMap<>();
    private int nextId = 1;

    public Contact add(String firstName, String lastName, String phone, String email) {
        if (emailExists(email, NO_EXCLUDED_ID)) {
            return null;
        }
        var contact = new Contact(nextId++, firstName, lastName, phone, email);
        contacts.put(contact.getId(), contact);
        return contact;
    }

    public List<Contact> findAll() {
        return List.copyOf(contacts.values());
    }

    public Optional<Contact> findById(int id) {
        return Optional.ofNullable(contacts.get(id));
    }

    public List<Contact> search(String query) {
        var needle = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        var matches = new ArrayList<Contact>();
        for (var contact : contacts.values()) {
            if (contact.getFirstName().toLowerCase(Locale.ROOT).contains(needle)
                    || contact.getLastName().toLowerCase(Locale.ROOT).contains(needle)
                    || contact.getPhone().toLowerCase(Locale.ROOT).contains(needle)) {
                matches.add(contact);
            }
        }
        return List.copyOf(matches);
    }

    public Optional<Contact> update(int id, String firstName, String lastName, String phone, String email) {
        if (!contacts.containsKey(id) || emailExists(email, id)) {
            return Optional.empty();
        }
        var updated = new Contact(id, firstName, lastName, phone, email);
        contacts.put(id, updated);
        return Optional.of(updated);
    }

    public boolean delete(int id) {
        return contacts.remove(id) != null;
    }

    public boolean emailExists(String email, int excludeId) {
        if (email == null) {
            return false;
        }
        return contacts.values().stream()
                .anyMatch(c -> c.getId() != excludeId && c.getEmail().equalsIgnoreCase(email.trim()));
    }
}
