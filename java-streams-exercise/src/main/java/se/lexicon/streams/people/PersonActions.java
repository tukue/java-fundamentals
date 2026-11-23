package se.lexicon.streams.people;

public final class PersonActions {
    public static final PersonAction PRINT_NAME = person -> System.out.println(person.getName());
    public static final PersonAction SEND_EMAIL =
            person -> System.out.println("Send email to " + person.getName());

    private PersonActions() {
    }
}
