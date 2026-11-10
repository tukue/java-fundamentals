package se.lexicon.issues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/** In-memory issue management operations and stream-based queries. */
public class IssueService {
    private final List<Issue> issues;

    public IssueService(Collection<Issue> issues) {
        this.issues = new ArrayList<>(Objects.requireNonNull(issues, "issues"));
        long distinctIds = this.issues.stream().map(Issue::id).distinct().count();
        if (distinctIds != this.issues.size()) {
            throw new IllegalArgumentException("Issue ids must be unique");
        }
    }

    public Issue create(String title, String description, String assignee, IssuePriority priority) {
        int nextId = issues.stream().mapToInt(Issue::id).max().orElse(0) + 1;
        Issue created = new Issue(nextId, title, description, assignee, IssueStatus.OPEN, priority);
        issues.add(created);
        return created;
    }

    public Issue update(int id, String title, String description, IssuePriority priority) {
        Issue existing = findById(id);
        Issue updated = new Issue(id, title, description, existing.assignee(), existing.status(), priority);
        replace(id, updated);
        return updated;
    }

    public Issue assign(int id, String assignee) {
        Issue existing = findById(id);
        Issue updated = new Issue(id, existing.title(), existing.description(), assignee,
                existing.status(), existing.priority());
        replace(id, updated);
        return updated;
    }

    public Issue changeStatus(int id, IssueStatus status) {
        Objects.requireNonNull(status, "status");
        Issue existing = findById(id);
        Issue updated = new Issue(id, existing.title(), existing.description(), existing.assignee(),
                status, existing.priority());
        replace(id, updated);
        return updated;
    }

    public boolean delete(int id) {
        boolean exists = issues.stream().anyMatch(issue -> issue.id() == id);
        if (exists) {
            issues.removeIf(issue -> issue.id() == id);
        }
        return exists;
    }

    public Issue findById(int id) {
        return issues.stream().filter(issue -> issue.id() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No issue with id " + id));
    }

    private void replace(int id, Issue replacement) {
        issues.replaceAll(issue -> issue.id() == id ? replacement : issue);
    }

    public List<Issue> listAll() {
        return issues.stream().sorted(Comparator.comparingInt(Issue::id)).toList();
    }

    public List<Issue> findByStatus(IssueStatus status) {
        Objects.requireNonNull(status, "status");
        return issues.stream().filter(issue -> issue.status() == status)
                .sorted(Comparator.comparingInt(Issue::id)).toList();
    }

    public List<Issue> findByAssignee(String assignee) {
        String normalizedAssignee = requireText(assignee, "assignee");
        return issues.stream().filter(issue -> issue.assignee().equalsIgnoreCase(normalizedAssignee))
                .sorted(Comparator.comparingInt(Issue::id)).toList();
    }

    public List<Issue> search(String query) {
        String normalizedQuery = requireText(query, "query").toLowerCase(Locale.ROOT);
        return issues.stream()
                .filter(issue -> issue.title().toLowerCase(Locale.ROOT).contains(normalizedQuery)
                        || issue.description().toLowerCase(Locale.ROOT).contains(normalizedQuery))
                .sorted(Comparator.comparingInt(Issue::id)).toList();
    }

    public List<Issue> findOpenHighPriority() {
        return issues.stream()
                .filter(issue -> issue.status() == IssueStatus.OPEN)
                .filter(issue -> issue.priority() == IssuePriority.HIGH
                        || issue.priority() == IssuePriority.CRITICAL)
                .sorted(Comparator.comparing(Issue::priority).reversed()
                        .thenComparingInt(Issue::id))
                .toList();
    }

    public Map<IssueStatus, Long> countByStatus() {
        return issues.stream().collect(Collectors.groupingBy(
                Issue::status, () -> new EnumMap<>(IssueStatus.class), Collectors.counting()));
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
