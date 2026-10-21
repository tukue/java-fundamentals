/*
 * Collection choice: none - just two variables (Integer largest, Integer second)
 *
 * - The exercise forbids sorting, and sorting would be O(n log n) anyway.
 * - A single pass tracking the largest and second-largest *unique* values is
 *   enough: duplicates of the largest are ignored, everything else updates
 *   the second place.
 */
package se.lexicon.collections;

import java.util.List;

public class Exercise16_SecondLargest {

    public static List<Integer> numbers() {
        return List.of(34, 78, 23, 78, 91, 56, 91, 12);
    }

    public static int secondLargest(List<Integer> numbers) {
        if (numbers.size() < 2) {
            throw new IllegalArgumentException("need at least two numbers");
        }
        Integer largest = null;
        Integer second = null;
        for (var number : numbers) {
            if (largest == null || number > largest) {
                second = largest;
                largest = number;
            } else if (!number.equals(largest) && (second == null || number > second)) {
                second = number;
            }
        }
        if (second == null) {
            throw new IllegalArgumentException("no second largest unique value");
        }
        return second;
    }

    public static void main(String[] args) {
        var numbers = numbers();
        System.out.println("Numbers: " + numbers);
        System.out.println("Second largest: " + secondLargest(numbers));
    }
}
