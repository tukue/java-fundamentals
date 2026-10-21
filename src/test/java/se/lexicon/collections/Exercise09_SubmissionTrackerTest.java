package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class Exercise09_SubmissionTrackerTest {

    @Test
    void submit_firstTime_returnsTrue() {
        var tracker = Exercise09_SubmissionTracker.createTracker();
        assertTrue(Exercise09_SubmissionTracker.submit(tracker, "Emma"));
    }

    @Test
    void submit_duplicate_returnsFalse() {
        var tracker = Exercise09_SubmissionTracker.createTracker();
        Exercise09_SubmissionTracker.submit(tracker, "Emma");
        assertFalse(Exercise09_SubmissionTracker.submit(tracker, "Emma"));
    }

    @Test
    void submit_duplicateDoesNotChangeSize() {
        var tracker = Exercise09_SubmissionTracker.createTracker();
        Exercise09_SubmissionTracker.submit(tracker, "Liam");
        Exercise09_SubmissionTracker.submit(tracker, "Liam");
        assertEquals(1, tracker.size());
    }

    @Test
    void fullRun_producesFiveUniqueStudents() {
        var tracker = new HashSet<String>();
        for (var student : Exercise09_SubmissionTracker.submissionOrder()) {
            Exercise09_SubmissionTracker.submit(tracker, student);
        }
        assertEquals(5, tracker.size());
        assertTrue(tracker.contains("Mia"));
    }
}
