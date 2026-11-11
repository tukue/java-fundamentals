package se.lexicon.issues;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IssueFileStoreTest {
    @TempDir
    Path tempDir;

    @Test
    void savesAndReloadsIssuesAndDoesNotReuseDeletedIds() throws Exception {
        var store = new IssueFileStore(tempDir.resolve("application.issue.txt"));
        var service = IssueService.fromFile(store);

        Issue kept = service.create("Customer API", DeploymentEnvironment.PRODUCTION,
                "Crash | café", "Line one\nLine two", "Alice Smith", IssuePriority.HIGH);
        Issue deleted = service.create("Temporary issue", "Will be deleted", "Alice Smith", IssuePriority.LOW);
        assertTrue(service.delete(deleted.id()));

        var reloadedService = IssueService.fromFile(store);
        Issue afterRestart = reloadedService.create("After restart", "Still gets a fresh id",
                "Alice Smith", IssuePriority.MEDIUM);

        assertEquals(kept, reloadedService.findById(kept.id()));
        assertEquals(3, afterRestart.id());
        assertTrue(Files.readString(tempDir.resolve("application.issue.txt"))
                .contains("Crash \\| café"));
    }
}
