/*
 * Collection choices: HashSet (lookup) + LinkedHashSet (result)
 *
 * - Putting list B in a HashSet makes "is this in both?" an O(1) contains
 *   check, so the whole search is O(n) instead of a nested loop.
 * - LinkedHashSet guarantees each common element appears only once, in the
 *   order it first appears in list A.
 */
package se.lexicon.collections;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Exercise14_CommonElements {

    public static List<Integer> listA() {
        return List.of(1, 3, 5, 7, 9, 11);
    }

    public static List<Integer> listB() {
        return List.of(3, 6, 9, 12, 15);
    }

    public static Set<Integer> commonElements(List<Integer> a, List<Integer> b) {
        var second = new HashSet<>(b);
        var common = new LinkedHashSet<Integer>();
        for (var number : a) {
            if (second.contains(number)) {
                common.add(number);
            }
        }
        return common;
    }

    public static void main(String[] args) {
        var common = commonElements(listA(), listB());
        System.out.println("List A: " + listA());
        System.out.println("List B: " + listB());
        System.out.println("Common elements: " + common);
    }
}
