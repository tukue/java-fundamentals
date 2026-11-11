package se.lexicon.streams.people;

import java.util.function.Consumer;

@FunctionalInterface
public interface PersonAction extends Consumer<Person> {
    void perform(Person person);

    @Override
    default void accept(Person person) {
        perform(person);
    }
}
