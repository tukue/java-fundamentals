package se.lexicon.issues;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/** Stores issues as readable UTF-8, pipe-delimited text with escaped field values. */
public class IssueFileStore {
    private static final String NEXT_ID_PREFIX = "#nextId=";
    private final Path path;

    public IssueFileStore(Path path) {
        this.path = path.toAbsolutePath();
    }

    public StoredIssues load() throws IOException {
        if (Files.notExists(path)) {
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.createFile(path);
        }

        var lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        var issues = new ArrayList<Issue>();
        long storedNextId = 1;
        try {
            for (int index = 0; index < lines.size(); index++) {
                String line = lines.get(index);
                if (line.isBlank()) {
                    continue;
                }
                if (line.startsWith(NEXT_ID_PREFIX)) {
                    storedNextId = Long.parseLong(line.substring(NEXT_ID_PREFIX.length()));
                    if (storedNextId <= 0) {
                        throw new IllegalArgumentException("Next issue id must be positive");
                    }
                } else {
                    issues.add(parseLine(line, index + 1));
                }
            }
        } catch (RuntimeException exception) {
            if (exception instanceof InvalidIssueLineException invalidLine) {
                throw new IOException("Invalid issue data in " + path + " at line " + invalidLine.lineNumber,
                        invalidLine.getCause());
            }
            throw new IOException("Invalid issue data in " + path, exception);
        }
        long minimumNextId = issues.stream().mapToInt(Issue::id).max().orElse(0) + 1L;
        return new StoredIssues(List.copyOf(issues), Math.max(storedNextId, minimumNextId));
    }

    public void save(List<Issue> issues, long nextId) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        var lines = new ArrayList<String>();
        lines.add(NEXT_ID_PREFIX + nextId);
        issues.stream().map(IssueFileStore::formatLine).forEach(lines::add);
        Files.write(path, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    private static String formatLine(Issue issue) {
        return String.join("|",
                Integer.toString(issue.id()),
                escape(issue.applicationName()),
                issue.environment().name(),
                escape(issue.title()),
                escape(issue.description()),
                escape(issue.assignee()),
                issue.status().name(),
                issue.priority().name());
    }

    private static Issue parseLine(String line, int lineNumber) {
        try {
            List<String> fields = splitFields(line);
            if (fields.size() != 8) {
                throw new IllegalArgumentException("Expected 8 fields but found " + fields.size());
            }
            return new Issue(
                    Integer.parseInt(fields.get(0)),
                    fields.get(1),
                    DeploymentEnvironment.valueOf(fields.get(2)),
                    fields.get(3),
                    fields.get(4),
                    fields.get(5),
                    IssueStatus.valueOf(fields.get(6)),
                    IssuePriority.valueOf(fields.get(7)));
        } catch (RuntimeException exception) {
            throw new InvalidIssueLineException(lineNumber, exception);
        }
    }

    private static String escape(String value) {
        var escaped = new StringBuilder();
        for (char character : value.toCharArray()) {
            switch (character) {
                case '\\' -> escaped.append("\\\\");
                case '|' -> escaped.append("\\|");
                case '\n' -> escaped.append("\\n");
                case '\r' -> escaped.append("\\r");
                default -> escaped.append(character);
            }
        }
        return escaped.toString();
    }

    private static List<String> splitFields(String line) {
        var fields = new ArrayList<String>();
        var field = new StringBuilder();
        boolean escaped = false;
        for (char character : line.toCharArray()) {
            if (escaped) {
                switch (character) {
                    case 'n' -> field.append('\n');
                    case 'r' -> field.append('\r');
                    case '|', '\\' -> field.append(character);
                    default -> throw new IllegalArgumentException("Unknown escape sequence: \\" + character);
                }
                escaped = false;
            } else if (character == '\\') {
                escaped = true;
            } else if (character == '|') {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(character);
            }
        }
        if (escaped) {
            throw new IllegalArgumentException("Field ends with an incomplete escape");
        }
        fields.add(field.toString());
        return fields;
    }

    public record StoredIssues(List<Issue> issues, long nextId) {
    }

    private static final class InvalidIssueLineException extends RuntimeException {
        private final int lineNumber;

        private InvalidIssueLineException(int lineNumber, RuntimeException cause) {
            super(cause);
            this.lineNumber = lineNumber;
        }
    }
}
