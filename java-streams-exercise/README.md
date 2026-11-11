# Java Streams Exercise — People

A Maven practice project focused on functional programming and the Stream API using a `Person` collection.

## Run

From this directory:

```sh
mvn test
mvn exec:java -Dexec.mainClass=se.lexicon.streams.people.PersonStreamExercisesApp
```

## Functional programming and Person exercises

`Person` and generated sample data are in the `se.lexicon.streams.people` package. The demo randomizes age (18–90), city, and activity status. Tests use seeded randomized data so cases are varied but repeatable. `PersonStreamExercises` demonstrates:

- Filtering active people, adults, and active adults with predicates and method references.
- Mapping people to sorted names in a city.
- Grouping people by city and counting active people per city.
- Computing average age and finding the oldest active person with `Optional` results.
- Part 2 Stream API tasks: filtering active people, mapping names, counting adults, sorting by age, and finding the first active Stockholm resident.
- Optional Part 2 tasks: combined filters, unique sorted cities and initials, and formatting names as descriptions and email addresses.

### Functional rules and actions

- `PersonRule` is a composable `Predicate<Person>` with `and`, `or`, and `negate` rules.
- `PersonRules` defines active, adult, Stockholm resident, and the combined example rules.
- `PersonAction` is a `Consumer<Person>`; `PersonActions` includes name-printing and email-message actions.
- `PersonStreamExercises.filterPeople` demonstrates a custom rule, while `PersonProcessor` uses the standard `Predicate<Person>` and `Consumer<Person>` APIs.
