package se.lexicon.streams.tasks;

import java.util.List;
import java.util.Set;

public class TaskStreamsApp {
    public static void main(String[] args) {
        var tasks = List.of(
                new WorkTask(1, "Add login validation", "Alice", TaskStatus.IN_PROGRESS,
                        5, Set.of("backend", "security")),
                new WorkTask(2, "Write API documentation", "Smith", TaskStatus.TODO,
                        3, Set.of("docs", "api")),
                new WorkTask(3, "Fix token refresh", "Alice", TaskStatus.DONE,
                        2, Set.of("backend", "security")),
                new WorkTask(4, "Add health check", "Smith", TaskStatus.IN_PROGRESS,
                        3, Set.of("backend", "monitoring")));

        System.out.println("Tasks grouped by status: " + TaskQueries.groupByStatus(tasks).keySet());
        System.out.println("Task counts by assignee: " + TaskQueries.countByAssignee(tasks));
        System.out.println("All tags: " + TaskQueries.allTags(tasks));
        System.out.println("Average points by assignee: " + TaskQueries.averageEstimateByAssignee(tasks));
    }
}
