package se.lexicon;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ContactTransferService {

    private final ContactService service;
    private final ContactExporter exporter;
    private final ContactImporter importer;

    public ContactTransferService(ContactService service) {
        this(service, new CsvContactExporter(), new CsvContactImporter());
    }

    public ContactTransferService(ContactService service, ContactExporter exporter, ContactImporter importer) {
        this.service = service;
        this.exporter = exporter;
        this.importer = importer;
    }

    public boolean exportTo(Path file, List<Contact> contacts) {
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            var lines = exporter.lines(contacts);
            Files.writeString(file, String.join("\r\n", lines) + "\r\n", StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            System.out.println("Warning: could not write " + file + " (" + e.getMessage() + ")");
            return false;
        }
    }

    public ImportOutcome importFrom(Path file) {
        List<String> lines;
        try {
            lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Warning: could not read " + file + " (" + e.getMessage() + ")");
            return new ImportOutcome(List.of(), List.of("Could not read " + file));
        }
        var parsed = importer.parse(lines);
        if (parsed.contacts().isEmpty()) {
            return parsed;
        }
        var merged = service.importContacts(parsed.contacts());
        var warnings = new ArrayList<>(parsed.warnings());
        warnings.addAll(merged.warnings());
        return new ImportOutcome(merged.contacts(), List.copyOf(warnings));
    }
}
