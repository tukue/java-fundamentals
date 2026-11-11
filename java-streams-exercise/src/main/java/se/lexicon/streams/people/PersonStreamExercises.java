package se.lexicon.streams.people;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** Stream-based solutions for the Person collection exercises. */
public final class PersonStreamExercises {
    private PersonStreamExercises() {
    }

    public static List<Person> filterPeople(Collection<Person> people, PersonRule rule) {
        return people.stream().filter(rule::test).toList();
    }

    public static List<Person> activePeople(Collection<Person> people) {
        return people.stream().filter(Person::isActive).toList();
    }

    public static List<Person> adults(Collection<Person> people) {
        return people.stream().filter(person -> person.getAge() >= 18).toList();
    }

    public static List<Person> activeAdults(Collection<Person> people) {
        return people.stream()
                .filter(Person::isActive)
                .filter(person -> person.getAge() >= 18)
                .sorted(Comparator.comparingInt(Person::getAge))
                .toList();
    }

    public static List<String> namesInCity(Collection<Person> people, String city) {
        return people.stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }

    public static Map<String, List<Person>> groupByCity(Collection<Person> people) {
        return people.stream().collect(Collectors.groupingBy(
                Person::getCity, TreeMap::new, Collectors.toList()));
    }

    public static Map<String, Long> countActivePeopleByCity(Collection<Person> people) {
        return people.stream().collect(Collectors.groupingBy(
                Person::getCity,
                TreeMap::new,
                Collectors.filtering(Person::isActive, Collectors.counting())));
    }

    public static OptionalDouble averageAge(Collection<Person> people) {
        return people.stream().mapToInt(Person::getAge).average();
    }

    public static Optional<Person> oldestActivePerson(Collection<Person> people) {
        return people.stream()
                .filter(Person::isActive)
                .max(Comparator.comparingInt(Person::getAge));
    }
}
