package se.lexicon;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactTest {

    private static final String FIRST_NAME = "Alex";
    private static final String LAST_NAME = "Smith";
    private static final String PHONE = "1234567890";
    private static final String EMAIL_DOMAIN = "example.com";
    private static final String EMAIL = emailFor(FIRST_NAME, LAST_NAME);

    private static final String OTHER_FIRST_NAME = "Alice";
    private static final String OTHER_LAST_NAME = "Bob";
    private static final String OTHER_PHONE = "0987654321";
    private static final String OTHER_EMAIL = emailFor(OTHER_FIRST_NAME, OTHER_LAST_NAME);

    private static String emailFor(String firstName, String lastName) {
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + EMAIL_DOMAIN;
    }

    private static Contact contact(int id) {
        return new Contact(id, FIRST_NAME, LAST_NAME, PHONE, EMAIL);
    }

    private static Contact otherContact(int id) {
        return new Contact(id, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);
    }

    @Test
    void equals_isReflexive_forSameInstance() {
        var alex = contact(1);

        assertTrue(alex.equals(alex));
    }

    @Test
    void equals_isTrue_whenIdMatches_evenIfOtherFieldsDiffer() {
        var alex = new Contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL);
        var alice = new Contact(1, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);

        assertTrue(alex.equals(alice));
        assertTrue(alice.equals(alex));
    }

    @Test
    void equals_isFalse_whenIdDiffers() {
        var first = contact(1);
        var second = contact(2);

        assertFalse(first.equals(second));
        assertFalse(second.equals(first));
    }

    @Test
    void equals_isFalse_againstNull() {
        var alex = contact(1);

        assertFalse(alex.equals(null));
    }

    @Test
    void equals_isFalse_againstDifferentType() {
        var alex = contact(1);

        assertFalse(alex.equals(EMAIL));
        assertFalse(alex.equals(new Object()));
    }

    @Test
    void equals_isSymmetric_andTransitive_forMatchingIds() {
        var alex = new Contact(7, FIRST_NAME, LAST_NAME, PHONE, EMAIL);
        var alice = new Contact(7, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);
        var alexAgain = contact(7);

        assertEquals(alex, alice);
        assertEquals(alice, alex);
        assertEquals(alice, alexAgain);
        assertEquals(alex, alexAgain);
    }

    @Test
    void hashCode_isEqual_forContactsWithSameId() {
        var alex = new Contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL);
        var alice = new Contact(1, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);

        assertEquals(alex.hashCode(), alice.hashCode());
    }

    @Test
    void hashCode_isConsistent_acrossRepeatedCalls() {
        var alex = contact(42);

        assertEquals(alex.hashCode(), alex.hashCode());
    }

    @Test
    void hashCode_isNotEqual_forDifferentIds() {
        assertNotEquals(contact(1).hashCode(), contact(2).hashCode());
    }

    @Test
    void equalsAndHashCode_obeyContract_equalObjectsShareHashCode() {
        var pairs = new Contact[][]{
                {new Contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL),
                        new Contact(1, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL)},
                {new Contact(2, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL),
                        new Contact(2, FIRST_NAME, LAST_NAME, PHONE, EMAIL)}
        };

        for (var pair : pairs) {
            assertTrue(pair[0].equals(pair[1]),
                    "expected equal contacts for id " + pair[0].getId());
            assertEquals(pair[0].hashCode(), pair[1].hashCode(),
                    "equal contacts must share hashCode for id " + pair[0].getId());
        }
    }

    @Test
    void hashSet_deduplicatesContactsById() {
        var set = new HashSet<Contact>();
        var alex = new Contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL);
        var duplicate = new Contact(1, OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_PHONE, OTHER_EMAIL);
        var other = otherContact(2);

        assertTrue(set.add(alex));
        assertFalse(set.add(duplicate), "same id must be treated as duplicate");
        assertTrue(set.add(other));

        assertEquals(2, set.size());
        assertTrue(set.contains(new Contact(1, FIRST_NAME, LAST_NAME, PHONE, EMAIL)));
    }

    @Test
    void equals_handlesBoundaryIds_withNullSafeContract() {
        for (var id : new int[]{0, 1, Integer.MAX_VALUE}) {
            var alex = contact(id);
            assertTrue(Objects.equals(alex, alex));
            assertNotEquals(alex, null);
        }
    }
}
