package se.lexicon.streams.people;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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

    public static List<String> names(Collection<Person> people) {
        return people.stream().map(Person::getName).toList();
    }

    public static long countAdults(Collection<Person> people) {
        return people.stream().filter(person -> person.getAge() >= 18).count();
    }

    public static List<Person> sortByAge(Collection<Person> people) {
        return people.stream().sorted(Comparator.comparingInt(Person::getAge)).toList();
    }

    public static Optional<Person> firstActivePersonInStockholm(Collection<Person> people) {
        return people.stream()
                .filter(Person::isActive)
                .filter(person -> person.getCity().equalsIgnoreCase("Stockholm"))
                .findFirst();
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

    public static List<Person> activePeopleInStockholm(Collection<Person> people) {
        return filterPeople(people, PersonRules.IS_ACTIVE.and(PersonRules.LIVES_IN_STOCKHOLM));
    }

    public static List<Person> inactivePeopleOlderThan30(Collection<Person> people) {
        return people.stream()
                .filter(PersonRules.NOT_ACTIVE)
                .filter(person -> person.getAge() > 30)
                .toList();
    }

    public static List<String> uniqueCities(Collection<Person> people) {
        return people.stream().map(Person::getCity).distinct().sorted(String.CASE_INSENSITIVE_ORDER).toList();
    }

    public static List<String> uniqueFirstLetters(Collection<Person> people) {
        return people.stream()
                .map(Person::getName)
                .filter(name -> !name.isEmpty())
                .map(name -> name.substring(0, 1).toUpperCase(Locale.ROOT))
                .distinct()
                .sorted()
                .toList();
    }

    public static List<String> formatPeople(Collection<Person> people) {
        return people.stream()
                .map(person -> "%s (%d) - %s".formatted(person.getName(), person.getAge(), person.getCity()))
                .toList();
    }

    public static List<String> emails(Collection<Person> people) {
        return people.stream()
                .map(person -> person.getName().toLowerCase(Locale.ROOT) + "@example.com")
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
