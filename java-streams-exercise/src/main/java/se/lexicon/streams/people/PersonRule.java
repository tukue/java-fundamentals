package se.lexicon.streams.people;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface PersonRule extends Predicate<Person> {
    @Override
    boolean test(Person person);

    default PersonRule and(PersonRule other) {
        Objects.requireNonNull(other, "other");
        return person -> test(person) && other.test(person);
    }

    default PersonRule or(PersonRule other) {
        Objects.requireNonNull(other, "other");
        return person -> test(person) || other.test(person);
    }

    @Override
    default PersonRule negate() {
        return person -> !test(person);
    }
}
