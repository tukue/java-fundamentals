package se.lexicon.streams.people;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PersonStreamExercisesApp {
    public static void main(String[] args) {
        var people = generateSamplePeople();
        System.out.println("Randomized sample data:");
        people.forEach(System.out::println);

        System.out.println("\nActive adults: "
                + PersonStreamExercises.filterPeople(people, PersonRules.ACTIVE_AND_ADULT)
                        .stream().map(Person::getName).toList());
        System.out.println("Adults or Stockholm residents: "
                + PersonStreamExercises.filterPeople(people, PersonRules.ADULT_OR_LIVES_IN_STOCKHOLM)
                        .stream().map(Person::getName).toList());
        System.out.println("Inactive people:");
        new PersonProcessor().applyToMatching(people, PersonRules.NOT_ACTIVE, PersonActions.PRINT_NAME);

        System.out.println("People by city: " + PersonStreamExercises.groupByCity(people).keySet());
        System.out.println("Active people by city: "
                + PersonStreamExercises.countActivePeopleByCity(people));
        System.out.println("Average age: " + PersonStreamExercises.averageAge(people).orElse(0));
        System.out.println("Oldest active person: "
                + PersonStreamExercises.oldestActivePerson(people).map(Person::getName).orElse("None"));
        System.out.println("Emailing active people:");
        new PersonProcessor().applyToMatching(people, PersonRules.IS_ACTIVE, PersonActions.SEND_EMAIL);
    }

    private static List<Person> generateSamplePeople() {
        var random = ThreadLocalRandom.current();
        var cities = List.of("Stockholm", "Uppsala", "Gothenburg", "Malmö");
        return List.of("Amina", "Smith", "Alice", "Alex", "Lina", "Omar").stream()
                .map(name -> new Person(name,
                        random.nextInt(18, 91),
                        cities.get(random.nextInt(cities.size())),
                        random.nextBoolean()))
                .toList();
    }
}
