/*
 * Collection choice: List<String> (given)
 *
 * - Counting names that start with a letter is a single pass with a counter,
 *   so a List is all we need - no mapping or lookup structure involved.
 * - Case-insensitivity is handled by lowercasing the first character of both
 *   the name and the searched letter.
 */
package se.lexicon.collections;

import java.util.List;
import java.util.Scanner;

public class Exercise05_CountByLetter {

    public static List<String> names() {
        return List.of("Anna", "Bob", "Alice", "Charlie", "Amanda", "David", "Amy", "Brian");
    }

    public static long countStartingWith(List<String> names, String letter) {
        if (letter == null || letter.isEmpty()) {
            throw new IllegalArgumentException("a letter must be provided");
        }
        var target = Character.toLowerCase(letter.charAt(0));
        var count = 0L;
        for (var name : names) {
            if (!name.isEmpty() && Character.toLowerCase(name.charAt(0)) == target) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter a letter: ");
        var letter = scanner.nextLine().trim();
        var count = countStartingWith(names(), letter);
        System.out.println("Names starting with \"" + letter + "\": " + count);
    }
}
