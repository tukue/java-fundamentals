package se.lexicon.issues;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class IssueManagementApp {
    private final Scanner scanner;
    private final IssueService service;

    private IssueManagementApp(Scanner scanner, IssueService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public static void main(String[] args) throws IOException {
        var store = new IssueFileStore(Path.of("application.issue.txt"));
        var service = IssueService.fromFile(store);
        System.out.println("Loaded " + service.listAll().size() + " issue(s) from application.issue.txt.");

        try (var scanner = new Scanner(System.in)) {
            new IssueManagementApp(scanner, service).run();
        }
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = prompt("Choose an option: ");
            try {
                switch (choice) {
                    case "1" -> listIssues(service.listAll());
                    case "2" -> createIssue();
                    case "3" -> searchIssues();
                    case "4" -> listByStatus();
                    case "5" -> updateIssue();
                    case "6" -> assignIssue();
                    case "7" -> changeStatus();
                    case "8" -> deleteIssue();
                    case "9" -> listIssues(service.findOpenHighPriority());
                    case "0" -> running = false;
                    case "10" -> listIssues(service.findByApplication(prompt("Application name: ")));
                    case "11" -> listIssues(service.findByEnvironment(
                            readEnum("Environment (DEVELOPMENT, STAGING, PRODUCTION): ",
                                    DeploymentEnvironment.class)));
                    default -> System.out.println("Choose a number from 0 to 11.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Could not complete that action: " + exception.getMessage());
            } catch (java.io.UncheckedIOException exception) {
                System.out.println("Could not save application.issue.txt: " + exception.getCause().getMessage());
            }
        }
        System.out.println("Goodbye. Your issue changes are saved in application.issue.txt.");
    }

    private void printMenu() {
        System.out.println("\n=== Application Support Issue Tracker ===");
        System.out.println("1. List all issues");
        System.out.println("2. Create issue");
        System.out.println("3. Search issues");
        System.out.println("4. List by status");
        System.out.println("5. Update issue details");
        System.out.println("6. Assign issue");
        System.out.println("7. Change issue status");
        System.out.println("8. Delete issue");
        System.out.println("9. List open high-priority issues");
        System.out.println("10. List issues for an application");
        System.out.println("11. List issues by deployment environment");
        System.out.println("0. Exit");
    }

    private void createIssue() {
        var issue = service.create(
                prompt("Deployed application/service: "),
                readEnum("Environment (DEVELOPMENT, STAGING, PRODUCTION): ", DeploymentEnvironment.class),
                prompt("Title: "),
                prompt("Description: "),
                prompt("Assignee: "),
                readEnum("Priority (LOW, MEDIUM, HIGH, CRITICAL): ", IssuePriority.class));
        System.out.println("Created issue #" + issue.id() + ".");
    }

    private void searchIssues() {
        listIssues(service.search(prompt("Search application, title, or description: ")));
    }

    private void listByStatus() {
        var status = readEnum("Status (OPEN, IN_PROGRESS, RESOLVED, CLOSED): ", IssueStatus.class);
        listIssues(service.findByStatus(status));
    }

    private void updateIssue() {
        int id = readId();
        var issue = service.findById(id);
        var updated = service.update(id,
                promptOrDefault("Application/service", issue.applicationName()),
                readEnumOrDefault("Environment (DEVELOPMENT, STAGING, PRODUCTION)",
                        DeploymentEnvironment.class, issue.environment()),
                promptOrDefault("Title", issue.title()),
                promptOrDefault("Description", issue.description()),
                readEnumOrDefault("Priority (LOW, MEDIUM, HIGH, CRITICAL)", IssuePriority.class,
                        issue.priority()));
        System.out.println("Updated issue #" + updated.id() + ".");
    }

    private void assignIssue() {
        var issue = service.assign(readId(), prompt("New assignee: "));
        System.out.println("Assigned issue #" + issue.id() + " to " + issue.assignee() + ".");
    }

    private void changeStatus() {
        var issue = service.changeStatus(readId(),
                readEnum("New status (OPEN, IN_PROGRESS, RESOLVED, CLOSED): ", IssueStatus.class));
        System.out.println("Issue #" + issue.id() + " is now " + issue.status() + ".");
    }

    private void deleteIssue() {
        int id = readId();
        if (service.delete(id)) {
            System.out.println("Deleted issue #" + id + ".");
        } else {
            System.out.println("No issue with id " + id + ".");
        }
    }

    private int readId() {
        try {
            return Integer.parseInt(prompt("Issue id: "));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Issue id must be a number.", exception);
        }
    }

    private <T extends Enum<T>> T readEnum(String message, Class<T> enumType) {
        String value = prompt(message).trim().toUpperCase(Locale.ROOT);
        try {
            return Enum.valueOf(enumType, value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown value: " + value, exception);
        }
    }

    private <T extends Enum<T>> T readEnumOrDefault(String message, Class<T> enumType, T defaultValue) {
        String value = prompt(message + " [" + defaultValue + "]: ").trim();
        if (value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Enum.valueOf(enumType, value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown value: " + value, exception);
        }
    }

    private String promptOrDefault(String label, String defaultValue) {
        String value = prompt(label + " [" + defaultValue + "]: ");
        return value.isBlank() ? defaultValue : value;
    }

    private String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static void listIssues(List<Issue> issues) {
        if (issues.isEmpty()) {
            System.out.println("No issues found.");
            return;
        }
        issues.stream().map(issue -> "#%d | %s @ %s | %s | %s | %s | %s".formatted(
                        issue.id(), issue.applicationName(), issue.environment(), issue.status(),
                        issue.priority(), issue.assignee(), issue.title()))
                .forEach(System.out::println);
    }
}
