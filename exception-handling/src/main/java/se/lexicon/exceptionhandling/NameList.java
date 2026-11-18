package se.lexicon.exceptionhandling;

import java.util.ArrayList;
import java.util.List;

public class NameList {
    private final List<String> names = new ArrayList<>();

    public void addName(String name) throws DuplicateNameException {
        String normalizedName = normalize(name);
        if (names.stream().anyMatch(existing -> existing.equalsIgnoreCase(normalizedName))) {
            throw new DuplicateNameException(normalizedName);
        }
        names.add(normalizedName);
    }

    public String findName(String name) throws NameNotFoundException {
        String normalizedName = normalize(name);
        return names.stream()
                .filter(existing -> existing.equalsIgnoreCase(normalizedName))
                .findFirst()
                .orElseThrow(() -> new NameNotFoundException(normalizedName));
    }

    public List<String> getNames() {
        return List.copyOf(names);
    }

    private static String normalize(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        return name.trim();
    }
}
