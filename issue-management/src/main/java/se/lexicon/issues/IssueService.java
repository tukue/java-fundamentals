package se.lexicon.issues;

import java.io.IOException;
import java.io.UncheckedIOException;
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
    private final IssueFileStore fileStore;
    private long nextId;

    public IssueService(Collection<Issue> issues) {
        this(issues, null, 1);
    }

    private IssueService(Collection<Issue> issues, IssueFileStore fileStore, long storedNextId) {
        this.issues = new ArrayList<>(Objects.requireNonNull(issues, "issues"));
        this.fileStore = fileStore;
        long distinctIds = this.issues.stream().map(Issue::id).distinct().count();
        if (distinctIds != this.issues.size()) {
            throw new IllegalArgumentException("Issue ids must be unique");
        }
        long firstAvailableId = this.issues.stream().mapToInt(Issue::id).max().orElse(0) + 1L;
        this.nextId = Math.max(firstAvailableId, storedNextId);
    }

    public static IssueService fromFile(IssueFileStore fileStore) throws IOException {
        Objects.requireNonNull(fileStore, "fileStore");
        var storedIssues = fileStore.load();
        return new IssueService(storedIssues.issues(), fileStore, storedIssues.nextId());
    }

    public Issue create(String title, String description, String assignee, IssuePriority priority) {
        return create("Sample Application", DeploymentEnvironment.PRODUCTION,
                title, description, assignee, priority);
    }

    public Issue create(String applicationName, DeploymentEnvironment environment, String title,
                        String description, String assignee, IssuePriority priority) {
        if (nextId > Integer.MAX_VALUE) {
            throw new IllegalStateException("No issue ids remain");
        }
        Issue created = new Issue((int) nextId, applicationName, environment, title, description,
                assignee, IssueStatus.OPEN, priority);
        var updatedIssues = new ArrayList<>(issues);
        updatedIssues.add(created);
        long followingId = nextId + 1;
        saveAndReplace(updatedIssues, followingId);
        nextId = followingId;
        return created;
    }

    public Issue update(int id, String title, String description, IssuePriority priority) {
        Issue existing = findById(id);
        return update(id, existing.applicationName(), existing.environment(), title, description, priority);
    }

    public Issue update(int id, String applicationName, DeploymentEnvironment environment,
                        String title, String description, IssuePriority priority) {
        Issue existing = findById(id);
        Issue updated = new Issue(id, applicationName, environment, title, description,
                existing.assignee(), existing.status(), priority);
        replace(id, updated);
        return updated;
    }

    public Issue updateDeployment(int id, String applicationName, DeploymentEnvironment environment) {
        Issue existing = findById(id);
        Issue updated = new Issue(id, applicationName, environment, existing.title(), existing.description(),
                existing.assignee(), existing.status(), existing.priority());
        replace(id, updated);
        return updated;
    }

    public Issue assign(int id, String assignee) {
        Issue existing = findById(id);
        Issue updated = new Issue(id, existing.applicationName(), existing.environment(), existing.title(),
                existing.description(), assignee, existing.status(), existing.priority());
        replace(id, updated);
        return updated;
    }

    public Issue changeStatus(int id, IssueStatus status) {
        Objects.requireNonNull(status, "status");
        Issue existing = findById(id);
        Issue updated = new Issue(id, existing.applicationName(), existing.environment(), existing.title(),
                existing.description(), existing.assignee(), status, existing.priority());
        replace(id, updated);
        return updated;
    }

    public boolean delete(int id) {
        boolean exists = issues.stream().anyMatch(issue -> issue.id() == id);
        if (exists) {
            var updatedIssues = issues.stream().filter(issue -> issue.id() != id).toList();
            saveAndReplace(updatedIssues, nextId);
        }
        return exists;
    }

    public Issue findById(int id) {
        return issues.stream().filter(issue -> issue.id() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No issue with id " + id));
    }

    private void replace(int id, Issue replacement) {
        var updatedIssues = issues.stream()
                .map(issue -> issue.id() == id ? replacement : issue)
                .toList();
        saveAndReplace(updatedIssues, nextId);
    }

    private void saveAndReplace(List<Issue> updatedIssues, long updatedNextId) {
        if (fileStore != null) {
            try {
                fileStore.save(updatedIssues, updatedNextId);
            } catch (IOException exception) {
                throw new UncheckedIOException("Could not save issues", exception);
            }
        }
        issues.clear();
        issues.addAll(updatedIssues);
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

    public List<Issue> findByApplication(String applicationName) {
        String normalizedName = requireText(applicationName, "applicationName");
        return issues.stream()
                .filter(issue -> issue.applicationName().equalsIgnoreCase(normalizedName))
                .sorted(Comparator.comparingInt(Issue::id)).toList();
    }

    public List<Issue> findByEnvironment(DeploymentEnvironment environment) {
        Objects.requireNonNull(environment, "environment");
        return issues.stream().filter(issue -> issue.environment() == environment)
                .sorted(Comparator.comparingInt(Issue::id)).toList();
    }

    public List<Issue> search(String query) {
        String normalizedQuery = requireText(query, "query").toLowerCase(Locale.ROOT);
        return issues.stream()
                .filter(issue -> issue.applicationName().toLowerCase(Locale.ROOT).contains(normalizedQuery)
                        || issue.title().toLowerCase(Locale.ROOT).contains(normalizedQuery)
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
