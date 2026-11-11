package se.lexicon.streams.tasks;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class TaskQueries {
    private TaskQueries() {
    }

    public static List<WorkTask> byStatus(Collection<WorkTask> tasks, TaskStatus status) {
        Objects.requireNonNull(status, "status");
        return tasks.stream()
                .filter(task -> task.status() == status)
                .sorted(Comparator.comparingInt(WorkTask::id))
                .toList();
    }

    public static Map<TaskStatus, List<WorkTask>> groupByStatus(Collection<WorkTask> tasks) {
        return tasks.stream().collect(Collectors.groupingBy(
                WorkTask::status,
                () -> new EnumMap<>(TaskStatus.class),
                Collectors.toList()));
    }

    public static Map<String, Long> countByAssignee(Collection<WorkTask> tasks) {
        return tasks.stream().collect(Collectors.groupingBy(
                WorkTask::assignee, TreeMap::new, Collectors.counting()));
    }

    public static Set<String> allTags(Collection<WorkTask> tasks) {
        return tasks.stream()
                .flatMap(task -> task.tags().stream())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public static Map<String, Double> averageEstimateByAssignee(Collection<WorkTask> tasks) {
        return tasks.stream().collect(Collectors.groupingBy(
                WorkTask::assignee,
                TreeMap::new,
                Collectors.averagingInt(WorkTask::estimatePoints)));
    }
}
