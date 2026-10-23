package se.lexicon;

import java.util.ArrayList;
import java.util.List;

public class CsvContactExporter implements ContactExporter {

    public static final List<String> COLUMNS =
            List.of("id", "firstName", "lastName", "phone", "email");

    @Override
    public String extension() {
        return "csv";
    }

    @Override
    public List<String> lines(List<Contact> contacts) {
        var lines = new ArrayList<String>();
        lines.add(String.join(",", COLUMNS));
        for (var contact : contacts) {
            lines.add(String.join(",",
                    escape(String.valueOf(contact.getId())),
                    escape(contact.getFirstName()),
                    escape(contact.getLastName()),
                    escape(contact.getPhone()),
                    escape(contact.getEmail())));
        }
        return List.copyOf(lines);
    }

    static String escape(String field) {
        var needsQuotes = field.contains(",") || field.contains("\"")
                || field.contains("\n") || field.contains("\r");
        return needsQuotes ? "\"" + field.replace("\"", "\"\"") + "\"" : field;
    }
}
