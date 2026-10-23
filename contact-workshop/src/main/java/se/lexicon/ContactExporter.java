package se.lexicon;

import java.util.List;

public interface ContactExporter {

    String extension();

    List<String> lines(List<Contact> contacts);
}
