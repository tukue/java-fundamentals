/*
 * Collection choice: HashMap<String, String>
 *
 * - A phone book maps a name (key) to a phone number (value).
 * - HashMap gives O(1) average lookup, so finding a number by name is fast
 *   even if the book grows.
 * - Names are unique, which matches the Map contract (one value per key).
 */
package se.lexicon.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Exercise03_PhoneBook {

    public static Map<String, String> createPhoneBook() {
        var phoneBook = new HashMap<String, String>();
        phoneBook.put("Anna", "070-1112233");
        phoneBook.put("Bob", "070-2223077");
        phoneBook.put("Clara", "070-3334455");
        phoneBook.put("David", "070-4445566");
        return phoneBook;
    }

    public static String lookup(Map<String, String> phoneBook, String name) {
        return phoneBook.get(name);
    }

    public static void main(String[] args) {
        var phoneBook = createPhoneBook();
        try (var scanner = new Scanner(System.in)) {
            System.out.print("Enter a name: ");
            var name = scanner.nextLine().trim();
            var number = lookup(phoneBook, name);
            System.out.println(number != null ? number : "Contact not found.");
        }
    }
}
