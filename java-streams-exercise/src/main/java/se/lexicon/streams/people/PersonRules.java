package se.lexicon.streams.people;

public final class PersonRules {
    public static final PersonRule IS_ACTIVE = Person::isActive;
    public static final PersonRule IS_ADULT = person -> person.getAge() >= 18;
    public static final PersonRule LIVES_IN_STOCKHOLM =
            person -> person.getCity().equalsIgnoreCase("Stockholm");

    public static final PersonRule ACTIVE_AND_ADULT = IS_ACTIVE.and(IS_ADULT);
    public static final PersonRule ADULT_OR_LIVES_IN_STOCKHOLM = IS_ADULT.or(LIVES_IN_STOCKHOLM);
    public static final PersonRule NOT_ACTIVE = IS_ACTIVE.negate();

    private PersonRules() {
    }
}
