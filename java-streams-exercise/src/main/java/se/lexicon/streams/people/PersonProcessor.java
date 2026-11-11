package se.lexicon.streams.people;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/** Reusable processor using Java's standard Predicate and Consumer functional interfaces. */
public class PersonProcessor {
    public List<Person> findPeople(List<Person> people, Predicate<Person> rule) {
        return people.stream().filter(rule).toList();
    }

    public void applyToMatching(List<Person> people, Predicate<Person> rule, Consumer<Person> action) {
        people.stream().filter(rule).forEach(action);
    }
}
