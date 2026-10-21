package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Exercise05_CountByLetterTest {

    @Test
    void countStartingWith_uppercaseA_returns4() {
        assertEquals(4, Exercise05_CountByLetter.countStartingWith(Exercise05_CountByLetter.names(), "A"));
    }

    @Test
    void countStartingWith_lowercaseA_returnsSameCount() {
        assertEquals(4, Exercise05_CountByLetter.countStartingWith(Exercise05_CountByLetter.names(), "a"));
    }

    @Test
    void countStartingWith_letterB_returns2() {
        assertEquals(2, Exercise05_CountByLetter.countStartingWith(Exercise05_CountByLetter.names(), "b"));
    }

    @Test
    void countStartingWith_letterWithoutMatches_returns0() {
        assertEquals(0, Exercise05_CountByLetter.countStartingWith(Exercise05_CountByLetter.names(), "Z"));
    }

    @Test
    void countStartingWith_emptyLetter_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Exercise05_CountByLetter.countStartingWith(Exercise05_CountByLetter.names(), ""));
    }
}
