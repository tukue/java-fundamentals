/*
 * Collection choice: HashSet<Integer>
 *
 * - The "think about it" hint asks for constant-time value lookup: a HashSet
 *   contains() call is O(1), so we avoid the nested-loop O(n^2) solution.
 * - For each number we check whether its complement (target - number) has
 *   already been seen; if yes, we have our pair; if no, we remember the number.
 * - The whole algorithm runs in a single O(n) pass.
 */
package se.lexicon.collections;

import java.util.HashSet;
import java.util.List;

public class Exercise15_TwoSum {

    public static List<Integer> numbers() {
        return List.of(2, 7, 11, 15, 4, 6);
    }

    public static int target() {
        return 13;
    }

    public static List<Integer> twoSum(List<Integer> numbers, int target) {
        var seen = new HashSet<Integer>();
        for (var number : numbers) {
            var complement = target - number;
            if (seen.contains(complement)) {
                return List.of(complement, number);
            }
            seen.add(number);
        }
        return List.of();
    }

    public static void main(String[] args) {
        var pair = twoSum(numbers(), target());
        System.out.println("Numbers: " + numbers() + ", target: " + target());
        System.out.println(pair.isEmpty() ? "No pair found." : "Pair: " + pair);
    }
}
