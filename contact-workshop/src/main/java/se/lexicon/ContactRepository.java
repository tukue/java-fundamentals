package se.lexicon;

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
        return add(0, firstName, lastName, phone, email);
    }

    /**
     * Keeps the preferred id when it is a free positive number,
     * otherwise a new id is generated. Ids are never reused.
     */
    public Contact add(int preferredId, String firstName, String lastName, String phone, String email) {
        if (emailExists(email, NO_EXCLUDED_ID)) {
            return null;
        }
        var id = preferredId > 0 && !contacts.containsKey(preferredId) ? preferredId : nextId++;
        if (id >= nextId) {
            nextId = id + 1;
        }
        var contact = new Contact(id, firstName, lastName, phone, email);
        contacts.put(contact.getId(), contact);
        return contact;
    }

    public List<Contact> findAll() {
        return List.copyOf(contacts.values());
    }

    public void loadAll(List<Contact> loaded) {
        contacts.clear();
        var maxId = 0;
        for (var contact : loaded) {
            contacts.putIfAbsent(contact.getId(), contact);
            maxId = Math.max(maxId, contact.getId());
        }
        nextId = maxId + 1;
    }

    public Optional<Contact> findById(int id) {
        return Optional.ofNullable(contacts.get(id));
    }

    public List<Contact> search(String query) {
        var needle = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        return contacts.values().stream()
                .filter(contact -> contact.getFirstName().toLowerCase(Locale.ROOT).contains(needle)
                        || contact.getLastName().toLowerCase(Locale.ROOT).contains(needle)
                        || contact.getPhone().contains(needle))
                .toList();
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
