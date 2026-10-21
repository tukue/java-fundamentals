/*
 * Approach: O(n^2) expanding window with a running sum.
 *
 * - Fix a start index, then extend the end index one element at a time,
 *   adding to a running total. Whenever the total matches the target we
 *   record the window (start, end) and keep going: a later window with the
 *   SAME start can still match (e.g. zeros, or negative numbers).
 * - Because the sum is accumulated, each sublist costs O(1) per step - no
 *   re-adding the slice every time (that would be O(n^3)).
 * - Works for negatives and zeros, where a "sliding window" that only
 *   shrinks from the left would break.
 *
 * Checking the sample by hand, [1,2,3,4,2,1,5] with target 6 has exactly
 * three matches: 1+2+3 (0-2), 4+2 (3-4) and 1+5 (5-6). The sublists
 * suggested in the exercise text do NOT sum to 6 (3+4+2+1 = 10 and
 * 2+1+5 = 8), so the tests assert they are not reported.
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.List;

public class Optional05_SublistWithTargetSum {

    public record Sublist(int start, int end, List<Integer> values) {

        public int sum() {
            return values.stream().mapToInt(Integer::intValue).sum();
        }

        @Override
        public String toString() {
            return "Index " + start + "-" + end + ": " + values;
        }
    }

    public static List<Integer> sample() {
        return List.of(1, 2, 3, 4, 2, 1, 5);
    }

    public static int sampleTarget() {
        return 6;
    }

    public static List<Sublist> findSublists(List<Integer> numbers, int target) {
        var matches = new ArrayList<Sublist>();

        for (var start = 0; start < numbers.size(); start++) {
            var runningSum = 0;
            for (var end = start; end < numbers.size(); end++) {
                runningSum += numbers.get(end);
                if (runningSum == target) {
                    matches.add(new Sublist(start, end, List.copyOf(numbers.subList(start, end + 1))));
                }
            }
        }
        return matches;
    }

    public static void printSublists(List<Integer> numbers, int target) {
        System.out.println("List: " + numbers + ", target: " + target);
        var matches = findSublists(numbers, target);
        if (matches.isEmpty()) {
            System.out.println("No contiguous sublist sums to " + target);
            return;
        }
        matches.forEach(match -> System.out.println("  " + match + "  (sum = " + match.sum() + ")"));
    }

    public static void main(String[] args) {
        printSublists(sample(), sampleTarget());
    }
}
