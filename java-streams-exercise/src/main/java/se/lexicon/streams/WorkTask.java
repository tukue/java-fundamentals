package se.lexicon.streams.tasks;

import java.util.Objects;
import java.util.Set;

public record WorkTask(
        int id,
        String title,
        String assignee,
        TaskStatus status,
        int estimatePoints,
        Set<String> tags) {

    public WorkTask {
        if (id <= 0) {
            throw new IllegalArgumentException("Task id must be positive");
        }
        title = requireText(title, "title");
        assignee = requireText(assignee, "assignee");
        status = Objects.requireNonNull(status, "status");
        if (estimatePoints < 0) {
            throw new IllegalArgumentException("Estimate points must not be negative");
        }
        tags = Set.copyOf(Objects.requireNonNull(tags, "tags"));
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
