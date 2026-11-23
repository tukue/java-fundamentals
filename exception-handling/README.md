# Exception Handling Exercises

This is a standalone Maven project for the exception-handling exercises. Open this folder or its `pom.xml` in IntelliJ IDEA to import it as a Maven project.

## Requirements

- JDK 25 (the Maven compiler is configured for Java 25)
- Maven

## Run the tests

From this directory:

```bash
mvn test
```

To run an individual test class:

```bash
mvn -Dtest=Exercise1_BasicExceptionHandlingTest test
```

Replace the test class name to run another test. The tests are under `src/test/java/se/lexicon/exceptionhandling/`.

## Run an exercise

In IntelliJ, open the desired class under `src/main/java/se/lexicon/exceptionhandling/` and run its `main` method. From a terminal, first compile the project and then run a class from this directory:

```bash
mvn compile
java -cp target/classes se.lexicon.exceptionhandling.Exercise1_BasicExceptionHandling
```

Change the class name to run another exercise. Exercises 1–8 are provided; Exercise 3 uses `BankAccount`, Exercise 7 uses `NameList`, and their helper exception classes are in the same package.
