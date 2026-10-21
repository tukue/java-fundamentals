/*
 * Collection choice: List<Integer> (given)
 *
 * - The numbers arrive as a list, and finding the largest only needs one pass
 *   over the elements - no lookup, no sorting, no key/value mapping.
 * - A simple loop keeps it O(n) and respects the "no Collections.max()" rule.
 */
package se.lexicon.collections;

import java.util.List;

public class Exercise04_FindLargest {

    public static int findLargest(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("list must not be empty");
        }
        var largest = numbers.get(0);
        for (var number : numbers) {
            if (number > largest) {
                largest = number;
            }
        }
        return largest;
    }

    public static void main(String[] args) {
        var numbers = List.of(14, 3, 78, 45, 22, 91, 7, 56);
        System.out.println("Numbers: " + numbers);
        System.out.println("Largest: " + findLargest(numbers));
    }
}
