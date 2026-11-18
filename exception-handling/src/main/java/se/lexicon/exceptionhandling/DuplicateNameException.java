package se.lexicon.exceptionhandling;

public class DuplicateNameException extends Exception {
    public DuplicateNameException(String name) {
        super("Name already exists: " + name);
    }
}
