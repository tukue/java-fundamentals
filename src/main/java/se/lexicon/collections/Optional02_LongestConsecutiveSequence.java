/*
 * Collection choice: HashSet<Integer>
 *
 * - Sorting would solve it in O(n log n); the "think about it" hint asks for O(n).
 * - Every number goes into a HashSet first (O(1) lookups), so duplicates cost
 *   nothing and membership tests are constant time.
 * - We only start counting at a number that has no predecessor in the set
 *   (num - 1 absent). That way each streak is walked exactly once and the
 *   whole pass stays O(n).
 */
package se.lexicon.collections;

import java.util.HashSet;
import java.util.List;

public class Optional02_LongestConsecutiveSequence {

    public static List<Integer> sample() {
        return List.of(100, 4, 200, 1, 3, 2);
    }

    public static int longestConsecutive(List<Integer> numbers) {
        var seen = new HashSet<>(numbers);
        var best = 0;

        for (var number : seen) {
            if (seen.contains(number - 1)) {
                continue;
            }
            var current = number;
            var length = 1;
            while (seen.contains(current + 1)) {
                current++;
                length++;
            }
            best = Math.max(best, length);
        }
        return best;
    }

    public static void main(String[] args) {
        var numbers = sample();
        var longest = longestConsecutive(numbers);
        System.out.println("Input:  " + numbers);
        System.out.println("Longest consecutive sequence length: " + longest);
    }
}
