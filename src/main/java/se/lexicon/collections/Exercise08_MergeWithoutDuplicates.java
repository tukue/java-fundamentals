/*
 * Collection choice: LinkedHashSet
 *
 * - Merging two lists "with no duplicates" is a set union.
 * - addAll automatically ignores elements already present, and size() gives
 *   the total count the exercise asks for.
 * - LinkedHashSet preserves the order of first appearance for a predictable printout.
 */
package se.lexicon.collections;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Exercise08_MergeWithoutDuplicates {

    public static List<Integer> listA() {
        return List.of(1, 2, 3, 4, 5);
    }

    public static List<Integer> listB() {
        return List.of(3, 4, 5, 6, 7);
    }

    public static Set<Integer> merge(List<Integer> a, List<Integer> b) {
        var merged = new LinkedHashSet<>(a);
        merged.addAll(b);
        return merged;
    }

    public static void main(String[] args) {
        var merged = merge(listA(), listB());
        System.out.println("List A: " + listA());
        System.out.println("List B: " + listB());
        System.out.println("Merged: " + merged);
        System.out.println("Total count: " + merged.size());
    }
}
