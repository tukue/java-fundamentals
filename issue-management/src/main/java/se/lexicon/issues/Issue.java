package se.lexicon.issues;

import java.util.Objects;

public record Issue(
        int id,
        String title,
        String description,
        String assignee,
        IssueStatus status,
        IssuePriority priority) {

    public Issue {
        if (id <= 0) {
            throw new IllegalArgumentException("Issue id must be positive");
        }
        title = requireText(title, "title");
        description = Objects.requireNonNull(description, "description").trim();
        assignee = requireText(assignee, "assignee");
        status = Objects.requireNonNull(status, "status");
        priority = Objects.requireNonNull(priority, "priority");
    }

    private static String requireText(String value, String field) {
        Objects.requireNonNull(value, field);
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return trimmed;
    }
}
