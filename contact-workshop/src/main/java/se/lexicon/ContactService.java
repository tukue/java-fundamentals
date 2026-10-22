package se.lexicon;

import java.util.List;
import java.util.Optional;

public class ContactService {

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public ValidationResult add(String firstName, String lastName, String phone, String email) {
        var saved = repository.add(trim(firstName), trim(lastName), trim(phone), trim(email));
        if (saved == null) {
            return new ValidationResult(false, "Email already in use");
        }
        return new ValidationResult(true, "Contact added with id " + saved.getId());
    }

    public ValidationResult update(int id, String firstName, String lastName, String phone, String email) {
        if (repository.findById(id).isEmpty()) {
            return new ValidationResult(false, "No contact with id " + id);
        }
        var updated = repository.update(id, trim(firstName), trim(lastName), trim(phone), trim(email));
        if (updated.isEmpty()) {
            return new ValidationResult(false, "Email already in use: " + trim(email));
        }
        return new ValidationResult(true, "Contact " + id + " updated");
    }

    public ValidationResult delete(int id) {
        if (repository.findById(id).isEmpty()) {
            return new ValidationResult(false, "No contact with id " + id);
        }
        repository.delete(id);
        return new ValidationResult(true, "Contact " + id + " deleted");
    }

    public Optional<Contact> find(int id) {
        return repository.findById(id);
    }

    public List<Contact> list() {
        return repository.findAll();
    }

    public List<Contact> search(String query) {
        return repository.search(query);
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    public record ValidationResult(boolean ok, String message) {
    }
}
