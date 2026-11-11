package se.lexicon.streams.people;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.TreeMap;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonStreamExercisesTest {
    private List<Person> people;

    @BeforeEach
    void setUp() {
        people = PersonTestData.randomizedPeople(3481L);
    }

    @Test
    void testDataRandomizesAgeCityAndActiveStatusWithinExpectedRanges() {
        assertTrue(people.stream().allMatch(person -> person.getAge() >= 18 && person.getAge() <= 90));
        assertTrue(people.stream().map(Person::getCity).distinct().count() > 1);
        assertTrue(people.stream().map(Person::isActive).distinct().count() > 1);
    }

    @Test
    void filtersActivePeopleAndAdults() {
        assertEquals(expectedNames(Person::isActive), names(PersonStreamExercises.activePeople(people)));
        assertEquals(expectedNames(person -> person.getAge() >= 18),
                names(PersonStreamExercises.adults(people)));
    }

    @Test
    void selectsActiveAdultsInAgeOrder() {
        var expected = new ArrayList<Person>();
        for (Person person : people) {
            if (person.isActive() && person.getAge() >= 18) {
                expected.add(person);
            }
        }
        expected.sort(Comparator.comparingInt(Person::getAge));

        assertEquals(names(expected), names(PersonStreamExercises.activeAdults(people)));
    }

    @Test
    void findsNamesInCityIgnoringCase() {
        var expected = new ArrayList<String>();
        for (Person person : people) {
            if (person.getCity().equalsIgnoreCase("stockholm")) {
                expected.add(person.getName());
            }
        }
        expected.sort(String.CASE_INSENSITIVE_ORDER);

        assertEquals(expected, PersonStreamExercises.namesInCity(people, "stockholm"));
    }

    @Test
    void groupsPeopleAndCountsActivePeopleByCity() {
        var expectedGroups = new TreeMap<String, List<String>>();
        var expectedActiveCounts = new TreeMap<String, Long>();
        for (Person person : people) {
            expectedGroups.computeIfAbsent(person.getCity(), ignored -> new ArrayList<>())
                    .add(person.getName());
            expectedActiveCounts.putIfAbsent(person.getCity(), 0L);
            if (person.isActive()) {
                expectedActiveCounts.merge(person.getCity(), 1L, Long::sum);
            }
        }

        var actualGroups = new TreeMap<String, List<String>>();
        PersonStreamExercises.groupByCity(people)
                .forEach((city, residents) -> actualGroups.put(city, names(residents)));
        assertEquals(expectedGroups, actualGroups);
        assertEquals(expectedActiveCounts, PersonStreamExercises.countActivePeopleByCity(people));
    }

    @Test
    void computesAverageAgeAndOldestActivePerson() {
        int totalAge = 0;
        Person expectedOldestActive = null;
        for (Person person : people) {
            totalAge += person.getAge();
            if (person.isActive()
                    && (expectedOldestActive == null || person.getAge() > expectedOldestActive.getAge())) {
                expectedOldestActive = person;
            }
        }

        assertEquals((double) totalAge / people.size(),
                PersonStreamExercises.averageAge(people).orElseThrow(), 0.0001);
        assertEquals(expectedOldestActive,
                PersonStreamExercises.oldestActivePerson(people).orElse(null));
    }

    @Test
    void handlesEmptyCollections() {
        assertFalse(PersonStreamExercises.averageAge(List.of()).isPresent());
        assertTrue(PersonStreamExercises.oldestActivePerson(List.of()).isEmpty());
    }

    @Test
    void filtersPeopleWithCustomAndComposedRules() {
        assertEquals(expectedNames(Person::isActive),
                names(PersonStreamExercises.filterPeople(people, PersonRules.IS_ACTIVE)));
        assertEquals(expectedNames(person -> person.getAge() >= 18),
                names(PersonStreamExercises.filterPeople(people, PersonRules.IS_ADULT)));
        assertEquals(expectedNames(person -> person.getCity().equalsIgnoreCase("Stockholm")),
                names(PersonStreamExercises.filterPeople(people, PersonRules.LIVES_IN_STOCKHOLM)));
        assertEquals(expectedNames(person -> person.isActive() && person.getAge() >= 18),
                names(PersonStreamExercises.filterPeople(people, PersonRules.ACTIVE_AND_ADULT)));
        assertEquals(expectedNames(person -> person.getAge() >= 18
                        || person.getCity().equalsIgnoreCase("Stockholm")),
                names(PersonStreamExercises.filterPeople(people, PersonRules.ADULT_OR_LIVES_IN_STOCKHOLM)));
        assertEquals(expectedNames(person -> !person.isActive()),
                names(PersonStreamExercises.filterPeople(people, PersonRules.NOT_ACTIVE)));
    }

    @Test
    void processorWorksWithStandardPredicateAndConsumerInterfaces() {
        var processor = new PersonProcessor();
        var matchingPeople = processor.findPeople(people,
                person -> person.getCity().equalsIgnoreCase("Stockholm"));
        var expectedEmails = new ArrayList<String>();
        for (Person person : people) {
            if (person.isActive()) {
                expectedEmails.add("Send email to " + person.getName());
            }
        }
        var sentEmails = new ArrayList<String>();

        processor.applyToMatching(people, PersonRules.IS_ACTIVE,
                person -> sentEmails.add("Send email to " + person.getName()));

        assertEquals(expectedNames(person -> person.getCity().equalsIgnoreCase("Stockholm")),
                names(matchingPeople));
        assertEquals(expectedEmails, sentEmails);
    }

    private List<String> expectedNames(Predicate<Person> predicate) {
        var result = new ArrayList<String>();
        for (Person person : people) {
            if (predicate.test(person)) {
                result.add(person.getName());
            }
        }
        return result;
    }

    private static List<String> names(List<Person> people) {
        return people.stream().map(Person::getName).toList();
    }
}
