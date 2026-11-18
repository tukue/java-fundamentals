package se.lexicon.exceptionhandling;

public class NameNotFoundException extends Exception {
    public NameNotFoundException(String name) {
        super("Name not found: " + name);
    }
}
