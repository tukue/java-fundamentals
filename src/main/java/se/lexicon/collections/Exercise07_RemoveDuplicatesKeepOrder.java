/*
 * Collection choice: LinkedHashSet (wrapped back into an ArrayList)
 *
 * - The "think about it" hint asks what tracks what we have already seen:
 *   a Set does exactly that - adding a duplicate is a no-op.
 * - LinkedHashSet keeps first-occurrence order, which preserves the original
 *   list order as required.
 * - The result is converted to an ArrayList so it behaves like a normal list.
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Exercise07_RemoveDuplicatesKeepOrder {

    public static List<Integer> numbers() {
        return List.of(4, 7, 2, 7, 9, 4, 1, 9, 3);
    }

    public static List<Integer> removeDuplicates(List<Integer> numbers) {
        return new ArrayList<>(new LinkedHashSet<>(numbers));
    }

    public static void main(String[] args) {
        var original = numbers();
        System.out.println("Original: " + original);
        System.out.println("Without duplicates: " + removeDuplicates(original));
    }
}
