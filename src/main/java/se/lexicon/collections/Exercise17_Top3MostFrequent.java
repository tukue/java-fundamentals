/*
 * Collection choices: HashMap (counting) + sorted List<WordCount> (ranking)
 *
 * - HashMap counts each word with merge(word, 1, Integer::sum).
 * - To pick the top 3 we copy the counts into a List of WordCount records and
 *   sort by count descending; the alphabetical tiebreak keeps the order
 *   deterministic when counts are equal.
 * - subList(0, 3) then extracts just the top entries.
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Exercise17_Top3MostFrequent {

    public static List<String> words() {
        return List.of("java", "python", "java", "c++", "python", "java",
                "ruby", "c++", "python", "ruby", "java");
    }

    public static List<WordCount> countAll(List<String> words) {
        var counts = new HashMap<String, Integer>();
        for (var word : words) {
            counts.merge(word, 1, Integer::sum);
        }
        var result = new ArrayList<WordCount>();
        counts.forEach((word, count) -> result.add(new WordCount(word, count)));
        result.sort(Comparator.comparingInt(WordCount::count).reversed()
                .thenComparing(WordCount::word));
        return result;
    }

    public static List<WordCount> topN(List<String> words, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative");
        }
        var all = countAll(words);
        return new ArrayList<>(all.subList(0, Math.min(n, all.size())));
    }

    public static void main(String[] args) {
        System.out.println("Top 3 most frequent words:");
        var top = topN(words(), 3);
        for (var i = 0; i < top.size(); i++) {
            var entry = top.get(i);
            System.out.println((i + 1) + ". " + entry.word() + " = " + entry.count());
        }
    }
}
