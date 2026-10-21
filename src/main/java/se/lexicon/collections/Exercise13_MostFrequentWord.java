/*
 * Collection choice: HashMap<String, Integer>
 *
 * - merge(word, 1, Integer::sum) counts occurrences in a single pass.
 * - Finding the most frequent word is then one scan over entrySet() keeping
 *   the running maximum - no sorting needed, so HashMap is enough.
 * - A WordCount record returns both the word and its count cleanly.
 */
package se.lexicon.collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise13_MostFrequentWord {

    public static List<String> words() {
        return List.of("cat", "dog", "cat", "bird", "dog", "cat", "fish", "dog", "dog");
    }

    public static Map<String, Integer> countWords(List<String> words) {
        var counts = new HashMap<String, Integer>();
        for (var word : words) {
            counts.merge(word, 1, Integer::sum);
        }
        return counts;
    }

    public static WordCount mostFrequent(List<String> words) {
        if (words.isEmpty()) {
            throw new IllegalArgumentException("words must not be empty");
        }
        var counts = countWords(words);
        WordCount best = null;
        for (var entry : counts.entrySet()) {
            if (best == null || entry.getValue() > best.count()) {
                best = new WordCount(entry.getKey(), entry.getValue());
            }
        }
        return best;
    }

    public static void main(String[] args) {
        var result = mostFrequent(words());
        System.out.println("Most frequent: " + result.word() + " (" + result.count() + ")");
    }
}
