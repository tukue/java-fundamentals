package se.lexicon.issues;

import java.util.List;

public class IssueManagementApp {
    public static void main(String[] args) {
        var service = new IssueService(List.of(
                new Issue(1, "Login fails", "Users cannot log in after reset", "Alice Smith",
                        IssueStatus.OPEN, IssuePriority.CRITICAL),
                new Issue(2, "Update dependencies", "Upgrade the logging library", "Alice Smith",
                        IssueStatus.IN_PROGRESS, IssuePriority.LOW),
                new Issue(3, "Search is slow", "Optimize issue search results", "Alice Smith",
                        IssueStatus.OPEN, IssuePriority.HIGH),
                new Issue(4, "Fix typo", "Correct the help page heading", "Alice Smith",
                        IssueStatus.CLOSED, IssuePriority.LOW)));

        Issue created = service.create("Export fails", "CSV export returns an empty file", "Alice Smith",
                IssuePriority.HIGH);
        service.assign(created.id(), "Alice Smith");

        System.out.println("Open issues assigned to Alice Smith:");
        service.findByAssignee("alice smith").stream()
                .filter(issue -> issue.status() == IssueStatus.OPEN)
                .forEach(issue -> System.out.println("- [" + issue.priority() + "] " + issue.title()));
        System.out.println("\nIssue counts by status: " + service.countByStatus());
        System.out.println("\nMoving issue #" + created.id() + " to in progress.");
        service.changeStatus(created.id(), IssueStatus.IN_PROGRESS);
        service.findByStatus(IssueStatus.IN_PROGRESS).stream()
                .map(issue -> "#" + issue.id() + " " + issue.title() + " (" + issue.assignee() + ")")
                .forEach(System.out::println);
    }
}
