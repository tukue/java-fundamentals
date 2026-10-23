package se.lexicon;

import java.util.List;

public record ImportOutcome(List<Contact> contacts, List<String> warnings) {
}
