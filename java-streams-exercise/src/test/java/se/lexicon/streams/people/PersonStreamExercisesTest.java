package se.lexicon.streams.people;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonStreamExercisesTest {
    private List<Person> people;

    @BeforeEach
    void setUp() {
        people = List.of(
                new Person("Amina", 22, "Stockholm", true),
                new Person("Smith", 17, "Uppsala", true),
                new Person("Alice", 34, "Stockholm", false),
                new Person("Alex", 29, "Gothenburg", true),
                new Person("Lina", 41, "Malmö", false),
                new Person("Omar", 19, "Stockholm", true));
    }

    @Test
    void filtersActivePeopleAndAdults() {
        assertEquals(List.of("Amina", "Smith", "Alex", "Omar"),
                PersonStreamExercises.activePeople(people).stream().map(Person::getName).toList());
        assertEquals(5, PersonStreamExercises.adults(people).size());
    }

    @Test
    void selectsActiveAdultsInAgeOrder() {
        assertEquals(List.of("Omar", "Amina", "Alex"),
                PersonStreamExercises.activeAdults(people).stream().map(Person::getName).toList());
    }

    @Test
    void findsNamesInCityIgnoringCase() {
        assertEquals(List.of("Alice", "Amina", "Omar"),
                PersonStreamExercises.namesInCity(people, "stockholm"));
    }

    @Test
    void groupsPeopleAndCountsActivePeopleByCity() {
        assertEquals(List.of("Gothenburg", "Malmö", "Stockholm", "Uppsala"),
                PersonStreamExercises.groupByCity(people).keySet().stream().toList());
        assertEquals(Map.of("Gothenburg", 1L, "Malmö", 0L, "Stockholm", 2L, "Uppsala", 1L),
                PersonStreamExercises.countActivePeopleByCity(people));
    }

    @Test
    void computesAverageAgeAndOldestActivePerson() {
        assertEquals(OptionalDouble.of(27.0), PersonStreamExercises.averageAge(people));
        assertEquals("Alex", PersonStreamExercises.oldestActivePerson(people)
                .map(Person::getName).orElseThrow());
    }

    @Test
    void handlesEmptyCollections() {
        assertFalse(PersonStreamExercises.averageAge(List.of()).isPresent());
        assertTrue(PersonStreamExercises.oldestActivePerson(List.of()).isEmpty());
    }

    @Test
    void filtersPeopleWithCustomRulesAndComposedRules() {
        assertEquals(List.of("Amina", "Smith", "Alex", "Omar"),
                PersonStreamExercises.filterPeople(people, PersonRules.IS_ACTIVE)
                        .stream().map(Person::getName).toList());
        assertEquals(List.of("Amina", "Alice", "Omar"),
                PersonStreamExercises.filterPeople(people, PersonRules.LIVES_IN_STOCKHOLM)
                        .stream().map(Person::getName).toList());
        assertEquals(List.of("Amina", "Alice", "Alex", "Lina", "Omar"),
                PersonStreamExercises.filterPeople(people, PersonRules.IS_ADULT)
                        .stream().map(Person::getName).toList());
        assertEquals(List.of("Amina", "Alex", "Omar"),
                PersonStreamExercises.filterPeople(people, PersonRules.ACTIVE_AND_ADULT)
                        .stream().map(Person::getName).toList());
        assertEquals(List.of("Amina", "Alice", "Alex", "Lina", "Omar"),
                PersonStreamExercises.filterPeople(people, PersonRules.ADULT_OR_LIVES_IN_STOCKHOLM)
                        .stream().map(Person::getName).toList());
        assertEquals(List.of("Alice", "Lina"),
                PersonStreamExercises.filterPeople(people, PersonRules.NOT_ACTIVE)
                        .stream().map(Person::getName).toList());
    }

    @Test
    void processorWorksWithStandardPredicateAndConsumerInterfaces() {
        var processor = new PersonProcessor();
        var matchingPeople = processor.findPeople(people, person -> person.getCity().equals("Stockholm"));
        var sentEmails = new ArrayList<String>();

        processor.applyToMatching(people, PersonRules.IS_ACTIVE,
                person -> sentEmails.add("Send email to " + person.getName()));

        assertEquals(List.of("Amina", "Alice", "Omar"),
                matchingPeople.stream().map(Person::getName).toList());
        assertEquals(List.of("Send email to Amina", "Send email to Smith", "Send email to Alex",
                "Send email to Omar"), sentEmails);
    }
}
