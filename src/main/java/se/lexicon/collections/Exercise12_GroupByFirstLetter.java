/*
 * Collection choice: TreeMap<Character, List<String>>
 *
 * - Grouping means one bucket (List) per first letter, so a Map of
 *   letter -> names is the natural structure.
 * - computeIfAbsent creates a new list the first time a letter is seen,
 *   avoiding manual null checks.
 * - TreeMap prints the groups in alphabetical order (A, B, C...).
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Exercise12_GroupByFirstLetter {

    public static List<String> names() {
        return List.of("Alice", "Bob", "Anna", "Joe", "Brian", "Amanda", "Carl");
    }

    public static Map<Character, List<String>> groupByFirstLetter(List<String> names) {
        var groups = new TreeMap<Character, List<String>>();
        for (var name : names) {
            if (name.isEmpty()) {
                continue;
            }
            var firstLetter = Character.toUpperCase(name.charAt(0));
            groups.computeIfAbsent(firstLetter, key -> new ArrayList<>()).add(name);
        }
        return groups;
    }

    public static void main(String[] args) {
        var groups = groupByFirstLetter(names());
        groups.forEach((letter, group) -> System.out.println(letter + ": " + group));
    }
}
