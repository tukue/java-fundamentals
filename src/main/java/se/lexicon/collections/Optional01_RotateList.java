/*
 * Collection choice: ArrayList + subList
 *
 * - Rotation by k to the right only depends on k % size, so a k larger than
 *   the list (e.g. k = 7 on 5 items) behaves exactly like k = 2.
 * - The tail (last k items) moves to the front, the head keeps its order.
 * - Built from subList views copied into a new ArrayList, so no element is
 *   ever mutated in place: the input list stays untouched.
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.List;

public class Optional01_RotateList {

    public static List<Integer> sample() {
        return new ArrayList<>(List.of(1, 2, 3, 4, 5));
    }

    public static int sampleK() {
        return 2;
    }

    public static List<Integer> rotateRight(List<Integer> list, int k) {
        var result = new ArrayList<>(list);
        var size = result.size();
        if (size < 2) {
            return result;
        }

        var shift = ((k % size) + size) % size;
        if (shift == 0) {
            return result;
        }

        var tail = new ArrayList<>(result.subList(size - shift, size));
        var head = new ArrayList<>(result.subList(0, size - shift));
        tail.addAll(head);
        return tail;
    }

    public static void main(String[] args) {
        var list = sample();
        System.out.println("Input:  " + list + ", k = " + sampleK());
        System.out.println("Output: " + rotateRight(list, sampleK()));

        System.out.println("k = 7 (larger than size): " + rotateRight(list, 7));
        System.out.println("k = -1 (negative):        " + rotateRight(list, -1));
        System.out.println("Original unchanged:       " + list);
    }
}
