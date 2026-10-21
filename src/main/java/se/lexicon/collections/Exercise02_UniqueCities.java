/*
 * Collection choice: LinkedHashSet<String>
 *
 * - A Set rejects duplicates automatically, so the repeated cities
 *   are stored only once - exactly what "automatically handles duplicates" asks for.
 * - LinkedHashSet preserves insertion order, so the unique cities print in the
 *   same order they first appeared (a plain HashSet would print in random order).
 */
package se.lexicon.collections;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Exercise02_UniqueCities {

    public static List<String> cityNames() {
        return List.of("Stockholm", "Gothenburg", "Malmö", "Stockholm",
                "Uppsala", "Gothenburg", "Lund");
    }

    public static Set<String> uniqueCities(List<String> cities) {
        return new LinkedHashSet<>(cities);
    }

    public static void main(String[] args) {
        var unique = uniqueCities(cityNames());
        System.out.println("Unique cities:");
        for (var city : unique) {
            System.out.println("- " + city);
        }
        System.out.println("Total count: " + unique.size());
    }
}
