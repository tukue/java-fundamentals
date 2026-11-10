package se.lexicon.issues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IssueServiceTest {
    private IssueService service;

    @BeforeEach
    void setUp() {
        service = new IssueService(List.of(
                new Issue(1, "Login fails", "Reset flow broken", "Alice Smith",
                        IssueStatus.OPEN, IssuePriority.CRITICAL),
                new Issue(2, "Search is slow", "Optimize search", "Alice Smith",
                        IssueStatus.OPEN, IssuePriority.HIGH),
                new Issue(3, "Update docs", "Fix help page", "Alice Smith",
                        IssueStatus.CLOSED, IssuePriority.LOW)));
    }

    @Test
    void filtersAssigneeWithoutCaseSensitivity() {
        assertEquals(List.of(1, 2, 3), service.findByAssignee("alice smith").stream().map(Issue::id).toList());
    }

    @Test
    void searchesTitleAndDescriptionWithoutCaseSensitivity() {
        assertEquals(List.of(1), service.search("RESET FLOW").stream().map(Issue::id).toList());
    }

    @Test
    void prioritizesCriticalOpenIssuesBeforeHigh() {
        assertEquals(List.of(1, 2), service.findOpenHighPriority().stream().map(Issue::id).toList());
    }

    @Test
    void countsIssuesByStatus() {
        assertEquals(2L, service.countByStatus().get(IssueStatus.OPEN));
        assertEquals(1L, service.countByStatus().get(IssueStatus.CLOSED));
    }

    @Test
    void createsIssueWithNextIdAndOpenStatus() {
        Issue created = service.create("New bug", "Description", "Alice Smith", IssuePriority.MEDIUM);

        assertEquals(4, created.id());
        assertEquals(IssueStatus.OPEN, created.status());
        assertEquals(created, service.findById(4));
    }

    @Test
    void updatesIssueAndPreservesOtherFields() {
        Issue updated = service.update(2, "Search is very slow", "Search times out", IssuePriority.CRITICAL);

        assertEquals("Search is very slow", updated.title());
        assertEquals("Alice Smith", updated.assignee());
        assertEquals(IssueStatus.OPEN, updated.status());
        assertEquals(IssuePriority.CRITICAL, updated.priority());
    }

    @Test
    void canAssignChangeStatusAndDeleteIssue() {
        assertEquals("Alice Smith", service.assign(1, "Alice Smith").assignee());
        assertEquals(IssueStatus.IN_PROGRESS, service.changeStatus(1, IssueStatus.IN_PROGRESS).status());
        assertTrue(service.delete(1));
        assertFalse(service.delete(1));
        assertThrows(IllegalArgumentException.class, () -> service.findById(1));
    }
}
