package se.lexicon.streams.people;

import java.util.List;
import java.util.Random;

final class PersonTestData {
    private static final List<String> NAMES = List.of("Amina", "Smith", "Alice", "Alex", "Lina", "Omar");
    private static final List<String> CITIES = List.of("Stockholm", "Uppsala", "Gothenburg", "Malmö");

    private PersonTestData() {
    }

    static List<Person> randomizedPeople(long seed) {
        var random = new Random(seed);
        return NAMES.stream()
                .map(name -> new Person(name,
                        random.nextInt(18, 91),
                        CITIES.get(random.nextInt(CITIES.size())),
                        random.nextBoolean()))
                .toList();
    }
}
