/*
 * Collection choice: TreeMap<String, Integer>
 *
 * - Counts are keyed by word, so a Map is the natural structure.
 * - merge(word, 1, Integer::sum) increments each count in one line.
 * - TreeMap keeps the keys sorted alphabetically, which the exercise requires
 *   when printing (a HashMap would print in unpredictable order).
 */
package se.lexicon.collections;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Exercise06_WordFrequency {

    public static List<String> words() {
        return List.of("apple", "banana", "apple", "orange", "banana", "apple", "grape", "orange");
    }

    public static Map<String, Integer> countWords(List<String> words) {
        var counts = new TreeMap<String, Integer>();
        for (var word : words) {
            counts.merge(word, 1, Integer::sum);
        }
        return counts;
    }

    public static void main(String[] args) {
        var counts = countWords(words());
        counts.forEach((word, count) -> System.out.println(word + " = " + count));
    }
}
