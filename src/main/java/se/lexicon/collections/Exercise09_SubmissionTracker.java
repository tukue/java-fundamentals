/*
 * Collection choice: HashSet<String>
 *
 * - A student can only submit once, and a Set rejects duplicates automatically.
 * - We only need to know whether a student has submitted (a membership test),
 *   not what they submitted, so no key/value mapping is needed.
 * - The order submissions arrived in does not matter, so HashSet is enough
 *   (no need for LinkedHashSet or TreeSet).
 */
package se.lexicon.collections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Exercise09_SubmissionTracker {

    public static Set<String> createTracker() {
        return new HashSet<>();
    }

    public static boolean submit(Set<String> submitted, String student) {
        return submitted.add(student);
    }

    public static List<String> submissionOrder() {
        return List.of("Carol", "Olivia", "Ava", "Carol", "Mia", "Ethan",
                "Mia", "Ethan", "Mallory", "Trent", "Mallory");
    }

    public static void main(String[] args) {
        var submitted = createTracker();
        for (var student : submissionOrder()) {
            if (submit(submitted, student)) {
                System.out.println("Accepted  : " + student);
            } else {
                System.out.println("Rejected  : " + student + " has already submitted.");
            }
        }
        System.out.println("All submitted students: " + submitted);
    }
}
