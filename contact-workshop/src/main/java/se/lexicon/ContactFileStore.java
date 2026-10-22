package se.lexicon;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class ContactFileStore {

    public static final String DELIMITER = "\t";

    private final Path file;

    public ContactFileStore() {
        this(Path.of("contacts.txt"));
    }

    public ContactFileStore(Path file) {
        this.file = file;
    }

    public Path getFile() {
        return file;
    }

    public List<Contact> load() {
        if (!Files.exists(file)) {
            return List.of();
        }
        try (var lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return lines
                    .filter(line -> !line.isBlank())
                    .map(this::parse)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            System.out.println("Warning: could not read " + file + " (" + e.getMessage() + ")");
            return List.of();
        }
    }

    public void save(List<Contact> contacts) {
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            var lines = contacts.stream().map(this::format).toList();
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Warning: could not write " + file + " (" + e.getMessage() + ")");
        }
    }

    private Contact parse(String line) {
        var parts = line.split(java.util.regex.Pattern.quote(DELIMITER), -1);
        if (parts.length != 5) {
            System.out.println("Warning: skipping malformed line in " + file);
            return null;
        }
        try {
            return new Contact(
                    Integer.parseInt(parts[0].trim()),
                    parts[1],
                    parts[2],
                    parts[3],
                    parts[4]);
        } catch (NumberFormatException e) {
            System.out.println("Warning: skipping malformed line in " + file);
            return null;
        }
    }

    private String format(Contact contact) {
        var fields = List.of(contact.getFirstName(), contact.getLastName(),
                contact.getPhone(), contact.getEmail());
        for (var field : fields) {
            if (field.contains(DELIMITER) || field.contains("\n") || field.contains("\r")) {
                throw new IllegalArgumentException("Contact fields must not contain tabs or line breaks");
            }
        }
        return String.join(DELIMITER,
                String.valueOf(contact.getId()),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhone(),
                contact.getEmail());
    }
}
