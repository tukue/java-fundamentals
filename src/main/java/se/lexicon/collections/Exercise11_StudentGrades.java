/*
 * Storage: HashMap<String, Integer>
 * - Each student has exactly one grade, so name -> grade is a natural key/value pair.
 * - HashMap gives O(1) lookup by name, which is what "find quickly by name" asks for.
 *
 * Sorting: HashMap has no order of its own, so I copy its entries into a List and
 * sort that list with a comparator that compares grades from highest to lowest.
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Exercise11_StudentGrades {

    public static Map<String, Integer> createGrades() {
        var grades = new LinkedHashMap<String, Integer>();
        grades.put("Alice", 92);
        grades.put("Bob", 71);
        grades.put("Charlie", 85);
        grades.put("Diana", 99);
        grades.put("Erik", 64);
        grades.put("Fatima", 78);
        return grades;
    }

    public static Integer gradeOf(Map<String, Integer> grades, String student) {
        return grades.get(student);
    }

    public static List<Map.Entry<String, Integer>> sortedByGrade(Map<String, Integer> grades) {
        var entries = new ArrayList<>(grades.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return entries;
    }

    public static void main(String[] args) {
        var grades = new HashMap<>(createGrades());

        System.out.print("Enter a student name (e.g. Alice): ");
        var scanner = new java.util.Scanner(System.in);
        var name = scanner.nextLine().trim();
        var grade = gradeOf(grades, name);
        System.out.println(grade != null ? name + ": " + grade : "Student not found.");

        System.out.println("All students by grade (high to low):");
        for (var entry : sortedByGrade(grades)) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
