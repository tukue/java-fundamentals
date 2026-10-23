package se.lexicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvContactImporter implements ContactImporter {

    @Override
    public ImportOutcome parse(List<String> lines) {
        var warnings = new ArrayList<String>();
        if (lines == null || lines.isEmpty()) {
            warnings.add("File had no rows");
            return new ImportOutcome(List.of(), warnings);
        }
        var header = parseRow(lines.get(0), 1, warnings);
        if (header == null) {
            return new ImportOutcome(List.of(), warnings);
        }
        var columns = mapColumns(header, warnings);
        if (columns == null) {
            return new ImportOutcome(List.of(), warnings);
        }
        var contacts = new ArrayList<Contact>();
        for (var i = 1; i < lines.size(); i++) {
            var line = lines.get(i);
            if (line.isBlank()) {
                continue;
            }
            var row = i + 1;
            var fields = parseRow(line, row, warnings);
            if (fields == null) {
                continue;
            }
            var contact = toContact(fields, columns, row, warnings);
            if (contact != null) {
                contacts.add(contact);
            }
        }
        return new ImportOutcome(List.copyOf(contacts), warnings);
    }

    private static Map<String, Integer> mapColumns(List<String> header, List<String> warnings) {
        var columns = new HashMap<String, Integer>();
        for (var i = 0; i < header.size(); i++) {
            columns.putIfAbsent(header.get(i).trim(), i);
        }
        for (var column : CsvContactExporter.COLUMNS) {
            if (!columns.containsKey(column)) {
                warnings.add("Missing column: " + column);
                return null;
            }
        }
        return columns;
    }

    private static Contact toContact(List<String> fields, Map<String, Integer> columns,
                                     int row, List<String> warnings) {
        var needed = columns.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        if (fields.size() <= needed) {
            warnings.add("Row " + row + ": wrong number of fields");
            return null;
        }
        var first = fields.get(columns.get("firstName"));
        var last = fields.get(columns.get("lastName"));
        var phone = fields.get(columns.get("phone"));
        var email = fields.get(columns.get("email"));
        var error = ContactService.validate(first, last, phone, email);
        if (error != null) {
            warnings.add("Row " + row + ": " + lowercaseFirst(error));
            return null;
        }
        return new Contact(preferredId(fields.get(columns.get("id"))),
                first.trim(), last.trim(), phone.trim(), email.trim());
    }

    private static int preferredId(String value) {
        try {
            var id = Integer.parseInt(value.trim());
            return id > 0 ? id : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String lowercaseFirst(String message) {
        return message.isEmpty()
                ? message
                : Character.toLowerCase(message.charAt(0)) + message.substring(1);
    }

    static List<String> parseRow(String line, int row, List<String> warnings) {
        var fields = new ArrayList<String>();
        var current = new StringBuilder();
        var inQuotes = false;
        var i = 0;
        while (i < line.length()) {
            var c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        current.append('"');
                        i += 2;
                        continue;
                    }
                    inQuotes = false;
                    i++;
                    continue;
                }
                current.append(c);
                i++;
                continue;
            }
            if (c == '"') {
                if (current.length() > 0) {
                    warnings.add("Row " + row + ": malformed quoting");
                    return null;
                }
                inQuotes = true;
                i++;
                continue;
            }
            if (c == ',') {
                fields.add(current.toString());
                current.setLength(0);
                i++;
                continue;
            }
            current.append(c);
            i++;
        }
        if (inQuotes) {
            warnings.add("Row " + row + ": malformed quoting");
            return null;
        }
        fields.add(current.toString());
        return fields;
    }
}
