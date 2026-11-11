package se.lexicon.streams.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskQueriesTest {
    private List<WorkTask> tasks;

    @BeforeEach
    void setUp() {
        tasks = List.of(
                new WorkTask(1, "Add login validation", "Alice", TaskStatus.IN_PROGRESS,
                        5, Set.of("backend", "security")),
                new WorkTask(2, "Write API documentation", "Smith", TaskStatus.TODO,
                        3, Set.of("docs", "api")),
                new WorkTask(3, "Fix token refresh", "Alice", TaskStatus.DONE,
                        2, Set.of("backend", "security")),
                new WorkTask(4, "Add health check", "Smith", TaskStatus.IN_PROGRESS,
                        3, Set.of("backend", "monitoring")));
    }

    @Test
    void filtersTasksByStatus() {
        assertEquals(List.of(1, 4), TaskQueries.byStatus(tasks, TaskStatus.IN_PROGRESS)
                .stream().map(WorkTask::id).toList());
    }

    @Test
    void groupsTasksByStatus() {
        assertEquals(2, TaskQueries.groupByStatus(tasks).get(TaskStatus.IN_PROGRESS).size());
    }

    @Test
    void countsTasksByAssignee() {
        assertEquals(2L, TaskQueries.countByAssignee(tasks).get("Alice"));
        assertEquals(2L, TaskQueries.countByAssignee(tasks).get("Smith"));
    }

    @Test
    void flattensTagsAndRemovesDuplicates() {
        assertEquals(Set.of("api", "backend", "docs", "monitoring", "security"),
                TaskQueries.allTags(tasks));
    }

    @Test
    void calculatesAverageEstimateByAssignee() {
        assertEquals(3.5, TaskQueries.averageEstimateByAssignee(tasks).get("Alice"));
        assertEquals(3.0, TaskQueries.averageEstimateByAssignee(tasks).get("Smith"));
    }
}
